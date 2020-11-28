package com.openerp.controller;

import com.openerp.domain.Schedule;
import com.openerp.domain.ServiceDomain;
import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/collect")
public class CollectController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {

        if(page.equalsIgnoreCase(Constants.ROUTE.PAYMENT_LATENCY) ||
                page.equalsIgnoreCase(Constants.ROUTE.TROUBLED_CUSTOMER)){
            model.addAttribute(Constants.CONFIGURATION_TROUBLED_CUSTOMER, configurationRepository.getGlobalConfigurationByKeyAndActiveTrue("troubled_customer").getAttribute());
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            Sales salesObject = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null);
            salesObject.setService(null);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Invoice(getSessionOrganization(), salesObject, calculateInvoiceDate(model)));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                salesObject.setApprove(false);
                Payment paymentObject = new Payment();
                paymentObject.setPeriod(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(Util.getPeriodDay(), "payment-period"));
                salesObject.setPayment(paymentObject);
                salesObject.setSaleDate(null);
                salesObject.setSaled(false);
                salesObject.setReturned(false);
                model.addAttribute(Constants.FILTER, salesObject);
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Sales){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<Sales> sales = salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize()*1000, Sort.by("id").descending()));
            List<Sales> salesList2 = new ArrayList<>(sales.getContent());
            if(salesObject.getPayment()!=null && salesObject.getPayment().getPeriod()!=null && salesObject.getPayment().getId()!=null){
                salesObject.getPayment().setPeriod(dictionaryRepository.getDictionaryById(salesObject.getPayment().getPeriod().getId()));
                Date today = new Date();
                Date nextContractDate = DateUtility.generate(Util.parseInt(salesObject.getPayment().getPeriod().getAttr1()), today.getMonth()+1, today.getYear()+1900);
                List<ContactHistory> contactHistories = contactHistoryRepository.getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveAndOrganizationOrderByIdDesc(nextContractDate, true, getSessionOrganization());
                if(canViewAll()){
                    contactHistories = contactHistoryRepository.getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveOrderByIdDesc(nextContractDate, true);
                }
                for(ContactHistory ch: contactHistories){
                    salesList2.add(ch.getSales());
                }
            }


            List<Sales> salesList = new ArrayList<>();
            for(Sales sale: salesList2){
                double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                List<Schedule> schedules = new ArrayList<>();
                if(sale.getPayment()!=null && !sale.getPayment().getCash()){
                    schedules = Util.getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule(), sale.getPayment().getPeriod(), sale.getPayment().getLastPrice(), sale.getPayment().getDown(), Util.parseInt(sale.getPayment().getGracePeriod()));
                }
                double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                if(sale.getPayment().getLastPrice()>sumOfInvoices && sumOfInvoices<plannedPayment){
                    schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                    int latency = Util.calculateLatency(schedules, sumOfInvoices, sale);
                    sale.getPayment().setLatency(latency);
                    sale.getPayment().setSumOfInvoice(sumOfInvoices);
                    sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                    sale.getPayment().setLastPaid(Util.getLastPaid(sale.getInvoices()));
                    if(latency>0){
                        sale.setSalesInventories(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(sale.getId()));
                        salesList.add(sale);
                    }
                }
            }
            Page<Sales> salesPage = new PageImpl<Sales>(salesList);
            model.addAttribute(Constants.LIST, salesPage);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(salesPage, redirectAttributes, page);
            }
        } if(page.equalsIgnoreCase(Constants.ROUTE.CONTACT_HISTORY)){
            model.addAttribute(Constants.CONTACT_CHANNELS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("contact-channel"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new ContactHistory(new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null), getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new ContactHistory(new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null), !canViewAll()?getSessionOrganization():null));
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof ContactHistory){
                ContactHistory contactHistory = (ContactHistory) session.getAttribute(Constants.SESSION_FILTER);
                if(!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                    contactHistory.getSales().setId(Util.parseInt(data.get()));
                }
                model.addAttribute(Constants.FILTER, contactHistory);
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
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Invoice){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            model.addAttribute(Constants.LIST, invoiceService.findAll((Invoice) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending())));
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SERVICE_EMPLOYEE)){
            if(!model.containsAttribute(Constants.FILTER)){
                Sales sales = new Sales();
                sales.setApprove(true);
                sales.setService(true);
                if(!canViewAll()){
                    sales.setOrganization(getSessionOrganization());
                }
                if(canViewAll()){
                    sales.setServicer(getSessionUser().getEmployee());
                }
                model.addAttribute(Constants.FILTER, sales);
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Sales){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            model.addAttribute(Constants.LIST, salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending())));
        }
        return "layout";
    }

    @PostMapping(value = "/contact-history")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated ContactHistory contactHistory, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            contactHistory.setUser(getSessionUser());
            contactHistory.setChildSales(null);
            contactHistoryRepository.save(contactHistory);
            log(contactHistory, "collect_contact_history", "create/edit", contactHistory.getId(), contactHistory.toString());
        }
        return mapPost(contactHistory, binding, redirectAttributes);
    }

    @PostMapping(value = {"/payment-latency", "/troubled-c8ustomer"})
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
        invoice.setDescription("Ödəniş " + invoice.getPrice() + " AZN");
       invoiceRepository.save(invoice);
        log(invoice, "invoice", "create/edit", invoice.getId(), invoice.toString());
        String desc = "Hesab faktura yaradıldı: " + invoice.getId();
        if(sales!=null){
            ContactHistory contactHistory = new ContactHistory();
            contactHistory.setDescription(desc);
            contactHistory.setSales(sales);
            contactHistory.setUser(getSessionUser());
            contactHistoryRepository.save(contactHistory);
            log(contactHistory, "collect_payment_regulator_note", "create/edit", contactHistory.getId(), contactHistory.toString());
        }
        return mapPost(redirectAttributes, "/collect/payment-regulator");
    }

    @PostMapping(value = "/troubled-customer/transfer")
    public String postTroubledCustomerTransfer(Model model, @ModelAttribute(Constants.FORM) @Validated Invoice invoice,
                                               BindingResult binding,
                                               RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(null, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            invoice(invoice);
        }
        return mapPost(redirectAttributes, "/collect/troubled-customer");
    }

    @PostMapping(value = "/payment-latency/transfer")
    public String postPaymentLatencyTransfer(@ModelAttribute(Constants.FORM) @Validated Invoice invoice,
                                               BindingResult binding,
                                               RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(null, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            invoice(invoice);
        }
        return mapPost(redirectAttributes, "/collect/payment-latency");
    }

    @PostMapping(value = "/contact-history/filter")
    public String postContactHistoryFilter(@ModelAttribute(Constants.FILTER) @Validated ContactHistory contactHistory, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(contactHistory, binding, redirectAttributes, "/collect/contact-history");
    }

    @PostMapping(value = "/payment-latency/filter")
    public String postPaymentLatencyFilter(@ModelAttribute(Constants.FILTER) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(sales, binding, redirectAttributes, "/collect/payment-latency");
    }

    @PostMapping(value = "/troubled-customer/filter")
    public String postTroubledCustomerFilter(@ModelAttribute(Constants.FILTER) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(sales, binding, redirectAttributes, "/collect/troubled-customer");
    }


    @ResponseBody
    @GetMapping(value = "/api/contact-history/{dataId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ContactHistory getContactHistory(@PathVariable("dataId") Integer dataId){
        return contactHistoryRepository.getContactHistoryById(dataId);
    }

    private Date calculateInvoiceDate(Model model){
        Date today = new Date();
        try{
            if(model.containsAttribute(Constants.FILTER) && model.asMap().get(Constants.FILTER) instanceof Sales){
                Sales sales = (Sales) model.asMap().get(Constants.FILTER);
                if(sales!=null &&
                        sales.getPayment()!=null &&
                        sales.getPayment().getPeriod()!=null
                ){
                    Dictionary period = dictionaryRepository.getDictionaryById(sales.getPayment().getPeriod().getId());
                    return DateUtility.generate(
                            Util.parseInt(period.getAttr1()),
                            today.getMonth()+1,
                            today.getYear()+1900);
                }
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return DateUtility.addDay(1);
    }
}