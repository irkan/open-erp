package com.openerp.controller;

import com.openerp.domain.*;
import com.openerp.entity.*;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class ApiController extends SkeletonController {

    @ResponseBody
    @GetMapping(value = "/info/{username}/{password}/{sale_code}")
    public WSResponse getPayment(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("sale_code") Integer saleId){
        WSResponse response = new WSResponse("404", "Xəta baş verdi!");
        try {
            WebServiceAuthenticator webServiceAuthenticator = webServiceAuthenticatorRepository.getWebServiceAuthenticatorByUsernameAndPasswordAndActiveTrue(username, password);
            if(webServiceAuthenticator!=null){
                Sales sale = salesRepository.getSalesByIdAndActiveTrue(saleId);
                response = new WSResponse("400", "Məlumat tapılmadı!");
                if(sale!=null && sale.getPayment()!=null){
                    double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                    List<Schedule> schedules = Util.getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule(), sale.getPayment().getPeriod(), sale.getPayment().getLastPrice(), sale.getPayment().getDown(), Util.parseInt(sale.getPayment().getGracePeriod()));
                    double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                    schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                    sale.getPayment().setLatency(Util.calculateLatency(schedules, sumOfInvoices, sale));
                    sale.getPayment().setSumOfInvoice(sumOfInvoices);
                    sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                    response = new WSResponse("200", "OK", new WSInfo(sale.getId(),
                            sale.getCustomer().getPerson().getFullName(),
                            sale.getPayment().getLastPrice(),
                            sale.getPayment().getSumOfInvoice(),
                            Double.parseDouble(Util.format(sale.getPayment().getUnpaid())),
                            sale.getService()?"Servis":"Satış",
                            sale.getPayment().getCash()?"Birdəfəlik ödəniş":"Aylıq ödəniş",
                            sale.getTaxConfiguration()!=null?sale.getTaxConfiguration().getId():null,
                            sale.getTaxConfiguration()!=null?sale.getTaxConfiguration().getVoen():null
                    ));
                }
            }
        } catch (Exception e){
            log(null, "error", "", "", null, "", e.getMessage());
            log.error(e.getMessage(), e);
        }
        log(response, "sales", "find", saleId, response.toString(), username);
        return response;
    }


    @ResponseBody
    @GetMapping(value = "/pay/{username}/{password}/{sale_code}/{amount}/{channel_reference_code}")
    public WSResponse pay(@PathVariable("username") String username, @PathVariable("password") String password,  @PathVariable("sale_code") Integer saleId, @PathVariable("amount") Double amount, @PathVariable("channel_reference_code") String channelReferenceCode){
        WSResponse response = new WSResponse("404", "Xəta baş verdi!");
        try {
            WebServiceAuthenticator webServiceAuthenticator = webServiceAuthenticatorRepository.getWebServiceAuthenticatorByUsernameAndPasswordAndActiveTrue(username, password);
            if(webServiceAuthenticator!=null){
                Sales sale = salesRepository.getSalesByIdAndActiveTrue(saleId);
                response = new WSResponse("400", "Ödəniş həyata keçmədi!");
                if(sale!=null && sale.getPayment()!=null){
                    boolean status = true;
                    if(channelReferenceCode!=null && channelReferenceCode.trim().length()>0){
                        List<Invoice> invoices = invoiceRepository.getInvoicesByChannelReferenceCode(channelReferenceCode.trim());
                        status = invoices.size()>0?false:true;
                    }
                    if(status){
                        Invoice invc = new Invoice();
                        invc.setApproveDate(new Date());
                        invc.setChannelReferenceCode(channelReferenceCode);
                        invc.setOrganization(sale.getOrganization());
                        invc.setSales(sale);
                        invc.setPrice(amount);
                        invc.setDescription("Ödəniş " + invc.getPrice() + " AZN "+webServiceAuthenticator.getDescription() + ":TERMINAL ilə ödənilib");
                        invc.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("million", "payment-channel"));
                        invoiceRepository.save(invc);

                        log(response,"invoice", "create/edit", invc.getId(), response.toString(), invc.getDescription(), webServiceAuthenticator.getUsername());

                        try{
                            Transaction transaction = new Transaction();
                            transaction.setApprove(false);
                            transaction.setDebt(invc.getPrice()>0?true:false);
                            transaction.setInventory(invc.getSales().getSalesInventories().get(0).getInventory());
                            transaction.setOrganization(invc.getOrganization());
                            transaction.setPrice(Math.abs(invc.getPrice()));
                            transaction.setCurrency("AZN");
                            transaction.setRate(Util.getRate(currencyRateRepository.getCurrencyRateByCode(transaction.getCurrency().toUpperCase())));
                            double sumPrice = Util.amountChecker(transaction.getAmount()) * transaction.getPrice() * transaction.getRate();
                            transaction.setSumPrice(sumPrice);
                            transaction.setAction(invc.getSales().getSalesInventories().get(0).getInventory().getActions().get(0).getAction()); //burda duzelis edilmelidir
                            transaction.setDescription("TERMINAL Satış|Servis, Kod: "+invc.getSales().getId() + " -> "
                                    + invc.getSales().getCustomer().getPerson().getFullName() + " -> "
                                    + " barkod: " + invc.getSales().getSalesInventories().get(0).getInventory().getName()
                                    + " " + invc.getSales().getSalesInventories().get(0).getInventory().getBarcode() + " " + invc.getDescription()
                            );
                            transactionRepository.save(transaction);
                            log(transaction, "transaction", "create/edit", transaction.getId(), transaction.toString(), transaction.getDescription(), webServiceAuthenticator.getUsername());

                        } catch (Exception e){
                            log.error(e.getMessage(), e);
                        }

                        saleBonusAdvance(invc);

                        try{
                            Sales sales1 = salesRepository.getSalesById(invc.getSales().getId());
                            if(sales1!=null && sales1.getPayment()!=null && Util.calculateInvoice(sales1.getInvoices())>=sales1.getPayment().getLastPrice()){
                                sales1.setSaled(true);
                                salesRepository.save(sales1);
                                log(sales1, "sales", "create/edit", sales1.getId(), sales1.toString(), "Satıldı statusu yeniləndi!");
                            }
                        } catch (Exception e){
                            log.error(e.getMessage(), e);
                        }

                        response = new WSResponse("200", "OK", channelReferenceCode);
                    }
                }
            }
        } catch (Exception e){
            log(null, "error", "", "", null, "", e.getMessage());
            log.error(e.getMessage(), e);
        }
        return response;
    }

    @ResponseBody
    @GetMapping(value = "/get-voens/{username}/{password}")
    public WSResponse getVoens(@PathVariable("username") String username, @PathVariable("password") String password){
        WSResponse response = new WSResponse("404", "Xəta baş verdi!");
        try {
            WebServiceAuthenticator webServiceAuthenticator = webServiceAuthenticatorRepository.getWebServiceAuthenticatorByUsernameAndPasswordAndActiveTrue(username, password);
            if(webServiceAuthenticator!=null){
                List<TaxConfiguration> taxConfigurations = taxConfigurationRepository.getTaxConfigurationsByActiveTrue();
                response = new WSResponse("400", "Məlumat tapılmadı!");
                List<WSGetVoen> getVoens = new ArrayList<>();
                for(TaxConfiguration tc: taxConfigurations){
                    getVoens.add(new WSGetVoen(tc.getOrganization().getName(), tc.getId(), tc.getVoen()));
                }
                response = new WSResponse("200", "OK", getVoens);
            }
        } catch (Exception e){
            log(null, "error", "", "", null, "", e.getMessage());
            log.error(e.getMessage(), e);
        }
        return response;
    }
}
