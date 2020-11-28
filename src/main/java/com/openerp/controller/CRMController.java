package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.ImageResizer;
import com.openerp.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
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
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Customer(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Customer((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null));
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Customer){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<Customer> customers = customerService.findAll((Customer) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, customers);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(customers, redirectAttributes, page);
            }
        }
        return "layout";
    }

    @PostMapping(value = "/customer")
    public String postCustomer(@RequestParam(name = "file1", required = false) MultipartFile file1, @RequestParam(name = "file2", required = false) MultipartFile file2, @ModelAttribute(Constants.FORM) @Validated Customer customer, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            customerRepository.save(customer);
            log(customer, "crm_customer", "create/edit", customer.getId(), customer.toString());

            Person person = customer.getPerson();
            Dictionary documentType = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("id card", "document-type");
            List<PersonDocument> personDocuments = personDocumentRepository.getPersonDocumentsByPerson(person);
            if(personDocuments.size()>0 && (file1.getOriginalFilename().trim().length()>0 || file2.getOriginalFilename().trim().length()>0)){
                personDocumentRepository.deleteInBatch(personDocumentRepository.getPersonDocumentsByPerson(person));
            }
            if(file1!=null && file1.getOriginalFilename().trim().length()>0){
                PersonDocument document1 = new PersonDocument(person, documentType, ImageResizer.compress(file1.getInputStream(), file1.getOriginalFilename()), null, file1.getOriginalFilename());
                personDocumentRepository.save(document1);
                log(customer, "person_document", "create/edit", document1.getId(), document1.toString());
            }
            if(file2!=null && file2.getOriginalFilename().trim().length()>0){
                PersonDocument document2 = new PersonDocument(person, documentType, ImageResizer.compress(file2.getInputStream(), file2.getOriginalFilename()), null, file2.getOriginalFilename());
                personDocumentRepository.save(document2);
                log(customer, "person_document", "create/edit", document2.getId(), document2.toString());
            }
        }
        return mapPost(customer, binding, redirectAttributes);
    }

    @PostMapping(value = "/customer/filter")
    public String postCustomerFilter(@ModelAttribute(Constants.FILTER) @Validated Customer customer, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(customer, binding, redirectAttributes, "/crm/customer");
    }

    @ResponseBody
    @GetMapping(value = "/api/customer/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer findCustomer(@PathVariable("id") String id){
        try {
            return customerRepository.getCustomerByIdAndActiveTrueAndOrganization(Integer.parseInt(id), getSessionOrganization());
        } catch (Exception e){
            log(null, "error", "crm_customer", "", null, "", "CRM API CUSTOMER Xəta baş verdi! " + e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
