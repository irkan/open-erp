package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hr")
public class HRController extends SkeletonController {

    @GetMapping(value = "/{page}")
    public String route(Model model, @PathVariable("page") String page) throws Exception {
        session.setAttribute(Constants.PAGE, page);
        String description = "";
        List<Module> moduleList = (List<Module>) session.getAttribute(Constants.MODULES);
        for(Module m: moduleList){
            if(m.getPath().equalsIgnoreCase(page)){
                description = m.getDescription();
                break;
            }
        }
        session.setAttribute(Constants.MODULE_DESCRIPTION, description);

        if (page.equalsIgnoreCase(Constants.ROUTE.ORGANIZATION)){
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.findAll());
            model.addAttribute(Constants.LIST, organizationRepository.findOrganizationsByOrganizationIsNull());
            model.addAttribute(Constants.FORM, new Organization());
        } else if (page.equalsIgnoreCase(Constants.ROUTE.EMPLOYEE)){
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.POSITIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position"));
            model.addAttribute(Constants.NATIONALITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality"));
            model.addAttribute(Constants.GENDERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender"));
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.findAll());
            model.addAttribute(Constants.LIST, employeeRepository.findAll());
            model.addAttribute(Constants.FORM, new Employee());
        }
        return "layout";
    }

    @PostMapping(value = "/organization")
    public String postUser(Model model, @ModelAttribute(Constants.FORM) @Validated Organization organization, BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            organizationRepository.save(organization);
            model.addAttribute(Constants.FORM, new Organization());
        } else {
            model.addAttribute(Constants.FORM, organization);
        }
        model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
        model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.findAll());
        model.addAttribute(Constants.LIST, organizationRepository.findOrganizationsByOrganizationIsNull());
        return "layout";
    }

    @PostMapping(value = "/employee")
    public String postEmployee(Model model, @ModelAttribute(Constants.FORM) @Validated Employee employee, BindingResult binding) throws Exception {
        if(!binding.hasErrors()){
            Person person = employee.getPerson();
            personRepository.save(person);
            employee.setPerson(person);
            employeeRepository.save(employee);
            model.addAttribute(Constants.FORM, new Employee());
        } else {
            model.addAttribute(Constants.FORM, employee);
        }
        model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
        model.addAttribute(Constants.POSITIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position"));
        model.addAttribute(Constants.NATIONALITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality"));
        model.addAttribute(Constants.GENDERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender"));
        model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.findAll());
        model.addAttribute(Constants.LIST, employeeRepository.findAll());
        return "layout";
    }
}
