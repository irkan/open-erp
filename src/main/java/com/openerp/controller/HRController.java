package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            model.addAttribute(Constants.ORGANIZATION_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("organization-type"));
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.LIST, organizationRepository.findOrganizationsByOrganizationIsNullAndActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Organization());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.EMPLOYEE)){
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.POSITIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position"));
            model.addAttribute(Constants.NATIONALITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality"));
            model.addAttribute(Constants.GENDERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender"));
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.LIST, employeeRepository.findAll());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Employee());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/organization")
    public String postOrganization(@ModelAttribute(Constants.FORM) @Validated Organization organization, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            organizationRepository.save(organization);
        }
        return mapPost(organization, binding, redirectAttributes);
    }

    @PostMapping(value = "/employee")
    public String postEmployee(@ModelAttribute(Constants.FORM) @Validated Employee employee, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if (!binding.hasErrors()) {
            Person person = employee.getPerson();
            personRepository.save(person);
            employee.setPerson(person);
            employeeRepository.save(employee);
        }
        return mapPost(employee, binding, redirectAttributes);
    }
}
