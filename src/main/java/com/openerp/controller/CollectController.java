package com.openerp.controller;

import com.openerp.domain.Schedule;
import com.openerp.domain.ServiceDomain;
import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
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
            Sales salesObject = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null, new Payment((Double) null));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Invoice(salesObject, getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                salesObject.setSaleDateFrom(DateUtility.addYear(Integer.parseInt(configurationRepository.getConfigurationByKey("by_year").getAttribute())));
                salesObject.setService(null);
                salesObject.setApprove(true);
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
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SERVICE_EMPLOYEE)){
            if(!model.containsAttribute(Constants.FILTER)){
                Sales sales = new Sales();
                sales.setService(true);
                if(!canViewAll()){
                    sales.setServicer(getSessionUser().getEmployee());
                }
                model.addAttribute(Constants.FILTER, new Invoice(null, false, sales));
            }
            model.addAttribute(Constants.LIST, invoiceService.findAll((Invoice) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending())));
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SERVICE_REGULATOR)){
            model.addAttribute(Constants.SERVICE_NOTIFICATIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("service-notification"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new ServiceTask(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new ServiceTask(!canViewAll()?getSessionOrganization():null));
            }
            model.addAttribute(Constants.LIST, serviceTaskService.findAll((ServiceTask) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending())));
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

    @PostMapping(value = "/service-regulator/transfer")
    public String postServiceRegulatorTransfer(@ModelAttribute(Constants.FORM) @Validated ServiceTask serviceTask, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(null, Constants.TEXT.SUCCESS));

        if(!binding.hasErrors()){
            Configuration configuration = configurationRepository.getConfigurationByKey("service");
            String defaultValue = configuration!=null?configuration.getAttribute():"6";
            ServiceTask serviceTaskOld = serviceTaskRepository.getServiceTaskById(serviceTask.getId());

            for(ServiceDomain serviceDomain: calculateServiceDomain(serviceTaskOld.getServiceRegulatorTasks(), serviceTask.getServiceRegulatorTasks(), defaultValue)){
                if(serviceDomain.getDictionary()!=null &&
                        serviceDomain.getDictionary().getId()!=null &&
                        serviceDomain.getNextServiceDate()!=null){
                    List<ServiceRegulator> serviceRegulators = serviceRegulatorRepository.getServiceRegulatorsBySalesAndServiceNotification_Id(serviceTask.getSales(), serviceDomain.getDictionary().getId());
                    for(ServiceRegulator serviceRegulator: serviceRegulators){
                        serviceRegulator.setServicedDate(serviceDomain.getNextServiceDate());
                        serviceRegulatorRepository.save(serviceRegulator);
                    }
                }
            }

            //muqahise ucun evvel olan service regulyatorar sales obyektinden yeniden goturulecekdir;
            //log("collect_contact_history", "create/edit", contactHistory.getId(), contactHistory.toString());
        }
        return mapPost(serviceTask, binding, redirectAttributes, "/collect/service-regulator");
    }

    private static List<ServiceDomain> calculateServiceDomain(List<ServiceRegulatorTask> serviceRegulatorOldTasks, List<ServiceRegulatorTask> serviceRegulatorTasks, String defaultValue){
        Date today = new Date();
        List<ServiceDomain> serviceDomains = new ArrayList<>();
        for(ServiceRegulatorTask old: serviceRegulatorOldTasks) {
            if (old.getServiceRegulator() != null &&
                    old.getServiceRegulator().getServiceNotification() != null &&
                    old.getServiceRegulator().getServiceNotification().getId() != null) {
                serviceDomains.add(new ServiceDomain(old.getServiceRegulator().getServiceNotification(), DateUtility.addMonth(today, Util.parseInt(defaultValue, defaultValue))));
            }
        }
        List<ServiceDomain> serviceDomainList = new ArrayList<>();
        for(ServiceRegulatorTask current: serviceRegulatorTasks){
            if(current.getServiceRegulator()!=null &&
                    current.getServiceRegulator().getServiceNotification()!=null &&
                    current.getServiceRegulator().getServiceNotification().getId()!=null){
                for(ServiceDomain serviceDomain: serviceDomains){
                    if(serviceDomain.getDictionary()!=null &&
                            serviceDomain.getDictionary().getId()!=null){
                        if(current.getServiceRegulator().getServiceNotification().getId().intValue()==serviceDomain.getDictionary().getId().intValue()){
                            serviceDomain.setNextServiceDate(DateUtility.addMonth(today, Util.parseInt(current.getServiceRegulator().getServiceNotification().getAttr2(), defaultValue)));
                        } else {
                            serviceDomainList.add(new ServiceDomain(current.getServiceRegulator().getServiceNotification(), DateUtility.addMonth(today, Util.parseInt(current.getServiceRegulator().getServiceNotification().getAttr2(), defaultValue))));
                        }
                    }
                }
            }
        }
        return serviceDomains;
    }
}
