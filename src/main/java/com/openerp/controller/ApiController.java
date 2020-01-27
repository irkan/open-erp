package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.domain.Schedule;
import com.openerp.domain.WSResponse;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ApiController extends SkeletonController {

    @ResponseBody
    @GetMapping(value = "/payment/{username}/{password}/{sale_no}")
    public WSResponse getPayment(@PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("sale_no") Integer saleId){
        WSResponse response = new WSResponse("404", "Xəta baş verdi!");
        try {
            User user = userRepository.findByUsernameAndPasswordAndActiveTrue(username, DigestUtils.md5DigestAsHex(password.getBytes()));
            if(user!=null){
                Sales sale = salesRepository.getSalesByIdAndActiveTrue(saleId);
                response = new WSResponse("400", "Ödəniş tapılmadı!");
                if(sale!=null && sale.getPayment()!=null){
                    double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                    List<Schedule> schedules = getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule().getId(), sale.getPayment().getPeriod().getId(), sale.getPayment().getLastPrice(), sale.getPayment().getDown());
                    double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                    schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                    sale.getPayment().setLatency(Util.calculateLatency(schedules, sumOfInvoices, sale));
                    sale.getPayment().setSumOfInvoice(sumOfInvoices);
                    sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                    response = new WSResponse("200", "OK", sale.getPayment());
                }
            }
        } catch (Exception e){
            log.error(e);
        }
        return response;
    }


    @ResponseBody
    @GetMapping(value = "/pay/{username}/{password}/{sale_no}/{amount}/{channel_reference_code}")
    public WSResponse pay(@PathVariable("username") String username, @PathVariable("password") String password,  @PathVariable("sale_no") Integer saleId, @PathVariable("amount") Double amount, @PathVariable("channel_reference_code") String channelReferenceCode){
        WSResponse response = new WSResponse("404", "Xəta baş verdi!");
        try {
            User user = userRepository.findByUsernameAndPasswordAndActiveTrue(username, DigestUtils.md5DigestAsHex(password.getBytes()));
            if(user!=null){
                Sales sale = salesRepository.getSalesByIdAndActiveTrue(saleId);
                response = new WSResponse("400", "Ödəniş tapılmadı!");
                if(sale!=null && sale.getPayment()!=null){
                    Invoice invoice = new Invoice();
                    invoice.setApproveDate(new Date());
                    invoice.setChannelReferenceCode(channelReferenceCode);
                    invoice.setOrganization(sale.getOrganization());
                    invoice.setSales(sale);
                    invoice.setPrice(amount);
                    invoice.setDescription("Satışdan əldə edilən ödəniş " + invoice.getPrice() + " AZN");
                    invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("million", "payment-channel"));
                    invoiceRepository.save(invoice);
                    response = new WSResponse("200", "OK", invoice);
                }
            }
        } catch (Exception e){
            log.error(e);
        }
        return response;
    }
}
