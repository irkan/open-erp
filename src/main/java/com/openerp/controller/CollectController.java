package com.openerp.controller;

import com.openerp.domain.Schedule;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/collect")
public class CollectController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {

        if(page.equalsIgnoreCase(Constants.ROUTE.PAYMENT_LATENCY) ||
                page.equalsIgnoreCase(Constants.ROUTE.TROUBLED_CUSTOMER)){
            model.addAttribute(Constants.CONFIGURATION_TROUBLED_CUSTOMER, configurationRepository.getConfigurationByKey("troubled_customer").getAttribute());
            Sales salesObject = new Sales(!data.equals(Optional.empty())?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Invoice(salesObject, getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                salesObject.setSaleDateFrom(DateUtility.minusYear(Integer.parseInt(configurationRepository.getConfigurationByKey("by_year").getAttribute())));
                salesObject.setService(null);
                model.addAttribute(Constants.FILTER, salesObject);
            }
            Page<Sales> sales = salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize()*100, Sort.by("id").descending()));

            List<Sales> salesList = new ArrayList<>();
            for(Sales sale: sales){
                double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                List<Schedule> schedules = new ArrayList<>();
                if(sale.getPayment()!=null && !sale.getPayment().getCash()){
                    schedules = getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule().getId(), sale.getPayment().getPeriod().getId(), sale.getPayment().getLastPrice(), sale.getPayment().getDown());
                }
                double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                if(sale.getPayment().getLastPrice()>sumOfInvoices && sumOfInvoices<plannedPayment){
                    schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                    int latency = Util.calculateLatency(schedules, sumOfInvoices, sale);
                    sale.getPayment().setLatency(latency);
                    sale.getPayment().setSumOfInvoice(sumOfInvoices);
                    sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                    if(latency>0){
                        salesList.add(sale);
                    }
                }
            }
            Page<Sales> salesPage = new PageImpl<Sales>(salesList);
            model.addAttribute(Constants.LIST, salesPage);
        } if(page.equalsIgnoreCase(Constants.ROUTE.CONTACT_HISTORY)){
            model.addAttribute(Constants.CONTACT_CHANNELS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("contact-channel"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new ContactHistory(new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null), getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new ContactHistory(new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null), !canViewAll()?getSessionOrganization():null));
            }
            Page<ContactHistory> contactHistories = contactHistoryService.findAll((ContactHistory) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, contactHistories);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(contactHistories, redirectAttributes, page);
            }
        } else if(page.equalsIgnoreCase(Constants.ROUTE.COLLECTOR)){
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Invoice(getSessionUser().getEmployee(), null, false));
            }
            model.addAttribute(Constants.LIST, invoiceService.findAll((Invoice) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending())));
        }
        return "layout";
    }

    @PostMapping(value = "/contact-history")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated ContactHistory contactHistory, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            contactHistoryRepository.save(contactHistory);
            log("collect_contact_history", "create/edit", contactHistory.getId(), contactHistory.toString());
        }
        return mapPost(contactHistory, binding, redirectAttributes);
    }

    @PostMapping(value = {"/payment-latency", "/troubled-customer"})
    public String postPaymentRegulatorTransfer(@RequestParam(value = "sale", defaultValue = "0") String sale,
                                               @RequestParam(value = "price", defaultValue = "0") String price,
                                               @RequestParam(value = "description") String description,
                                               RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(null, Constants.TEXT.SUCCESS));
        Sales sales = salesRepository.getSalesByIdAndActiveTrue(Integer.parseInt(sale));
        Invoice invoice = new Invoice();
        invoice.setSales(sales);
        invoice.setApprove(false);
        invoice.setPrice(Double.parseDouble(price));
        invoice.setDescription(description);
        invoice.setOrganization(getUserOrganization());
        invoice.setDescription("Satışdan əldə edilən ödəniş " + invoice.getPrice() + " AZN");
        invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
        invoiceRepository.save(invoice);
        log("sale_invoice", "transfer", invoice.getId(), invoice.toString());
        invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
        invoiceRepository.save(invoice);
        log("sale_invoice", "create/edit", invoice.getId(), invoice.toString());
        String desc = "Hesab faktura yaradıldı: " + invoice.getId();
        if(sales!=null){
            ContactHistory contactHistory = new ContactHistory();
            contactHistory.setDescription(desc);
            contactHistory.setSales(sales);
            contactHistoryRepository.save(contactHistory);
            log("collect_payment_regulator_note", "create/edit", contactHistory.getId(), contactHistory.toString());
        }
        return mapPost(redirectAttributes, "/collect/payment-regulator");
    }

    @PostMapping(value = "/troubled-customer/transfer")
    public String postTroubledCustomerTransfer(@RequestParam(value = "sale", defaultValue = "0") String sale,
                                               @RequestParam(value = "price", defaultValue = "0") String price,
                                               @RequestParam(value = "description") String description,
                                               RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(null, Constants.TEXT.SUCCESS));
        Sales sales = salesRepository.getSalesByIdAndActiveTrue(Integer.parseInt(sale));
        Invoice invoice = new Invoice();
        invoice.setSales(sales);
        invoice.setApprove(false);
        invoice.setPrice(Double.parseDouble(price));
        invoice.setDescription(description);
        invoice.setOrganization(getUserOrganization());
        invoice.setDescription("Satışdan əldə edilən ödəniş " + invoice.getPrice() + " AZN");
        invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
        invoiceRepository.save(invoice);
        log("sale_invoice", "transfer", invoice.getId(), invoice.toString());
        String desc = "Hesab faktura yaradıldı: " + invoice.getId();
        if(sales!=null){
            ContactHistory contactHistory = new ContactHistory();
            contactHistory.setDescription(desc);
            contactHistoryRepository.save(contactHistory);
            log("collect_payment_regulator_note", "create/edit", contactHistory.getId(), contactHistory.toString());
        }
        return mapPost(redirectAttributes, "/collect/troubled-customer");
    }
}
