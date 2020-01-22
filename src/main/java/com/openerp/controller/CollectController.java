package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import net.emaze.dysfunctional.Groups;
import net.emaze.dysfunctional.dispatching.delegates.Pluck;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.MediaType;
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
            List<Schedule> schedules;
            if(canViewAll()){
                schedules = scheduleRepository.getSchedules(new Date());
            } else {
                schedules = scheduleRepository.getSchedulesByOrganization(new Date(), getSessionOrganization());
            }
            model.addAttribute(Constants.LIST, Util.convertPaymentSchedule(schedules));
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
