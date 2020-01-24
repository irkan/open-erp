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

        if(page.equalsIgnoreCase(Constants.ROUTE.PAYMENT_REGULATOR) ||
                page.equalsIgnoreCase(Constants.ROUTE.TROUBLED_CUSTOMER)){
            model.addAttribute(Constants.CONFIGURATION_TROUBLED_CUSTOMER, configurationRepository.getConfigurationByKey("troubled_customer").getAttribute());
            if(!model.containsAttribute(Constants.FILTER)){
                Sales sales = new Sales(!data.equals(Optional.empty())?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null);
                sales.setSaleDateFrom(DateUtility.minusYear(Integer.parseInt(configurationRepository.getConfigurationByKey("by_year").getAttribute())));
                model.addAttribute(Constants.FILTER, sales);
            }
            Page<Sales> sales = salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize()*100, Sort.by("id").descending()));

            List<Sales> salesList = new ArrayList<>();
            for(Sales sale: sales){
                double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                List<Schedule> schedules = getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule().getId(), sale.getPayment().getPeriod().getId(), sale.getPayment().getLastPrice(), sale.getPayment().getDown());
                double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                if(sale.getPayment().getLastPrice()>sumOfInvoices && sumOfInvoices<plannedPayment){
                    salesList.add(sale);
                }
            }
            Page<Sales> salesPage = new PageImpl<Sales>(salesList);
            model.addAttribute(Constants.LIST, salesPage);

            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Invoice(getSessionOrganization()));
            }
        } if(page.equalsIgnoreCase(Constants.ROUTE.PAYMENT_REGULATOR_NOTE)){
            List<PaymentRegulatorNote> paymentRegulatorNotes;
            int paymentId = 0;
            if(data.equals(Optional.empty())){
                paymentRegulatorNotes = paymentRegulatorNoteRepository.getPaymentRegulatorNotesByActiveTrue();
            } else {
                paymentRegulatorNotes = paymentRegulatorNoteRepository.getPaymentRegulatorNotesByActiveTrueAndPayment_Id(Integer.parseInt(data.get()));
                paymentId = Integer.parseInt(data.get());
            }
            model.addAttribute(Constants.LIST, paymentRegulatorNotes);
            model.addAttribute(Constants.PAYMENT_ID, paymentId);
            model.addAttribute(Constants.CONTACT_CHANNELS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("contact-channel"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new PaymentRegulatorNote());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/payment-regulator-note")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated PaymentRegulatorNote paymentRegulatorNote, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            paymentRegulatorNoteRepository.save(paymentRegulatorNote);
            log("collect_payment_regulator_note", "create/edit", paymentRegulatorNote.getId(), paymentRegulatorNote.toString());
        }
        return mapPost(paymentRegulatorNote, binding, redirectAttributes);
    }

    @PostMapping(value = "/payment-regulator/transfer")
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
            PaymentRegulatorNote paymentRegulatorNote = new PaymentRegulatorNote();
            paymentRegulatorNote.setDescription(desc);
            paymentRegulatorNote.setPayment(sales.getPayment());
            paymentRegulatorNoteRepository.save(paymentRegulatorNote);
            log("collect_payment_regulator_note", "create/edit", paymentRegulatorNote.getId(), paymentRegulatorNote.toString());
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
            PaymentRegulatorNote paymentRegulatorNote = new PaymentRegulatorNote();
            paymentRegulatorNote.setDescription(desc);
            paymentRegulatorNoteRepository.save(paymentRegulatorNote);
            log("collect_payment_regulator_note", "create/edit", paymentRegulatorNote.getId(), paymentRegulatorNote.toString());
        }
        return mapPost(redirectAttributes, "/collect/troubled-customer");
    }
}
