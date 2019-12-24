package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/crm")
public class CRMController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        if (page.equalsIgnoreCase(Constants.ROUTE.CUSTOMER)){
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.NATIONALITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality"));
            model.addAttribute(Constants.GENDERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender"));
            model.addAttribute(Constants.MARITAL_STATUSES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("marital-status"));
            List<Customer> customers;
            if(canViewAll()){
                customers = customerRepository.getCustomersByActiveTrue();
            } else {
                customers = customerRepository.getCustomersByActiveTrueAndOrganization(getSessionOrganization());
            }
            model.addAttribute(Constants.LIST, customers);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Customer());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/customer")
    public String postAdvanceApprove(@ModelAttribute(Constants.FORM) @Validated Customer customer, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            customerRepository.save(customer);
            log("crm_customer", "create/edit", customer.getId(), customer.toString());
        }
        return mapPost(customer, binding, redirectAttributes);
    }

    @ResponseBody
    @GetMapping(value = "/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer findCustomer(@PathVariable("id") String id){
        try {
            return customerRepository.getCustomerByIdAndActiveTrue(Integer.parseInt(id));
        } catch (Exception e){
            log.error(e);
        }
        return null;
    }
}
