package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.ReadWriteExcelFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/payroll")
public class PayrollController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
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

        if (page.equalsIgnoreCase(Constants.ROUTE.SALARY)){
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Salary());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.BUSINESS_TRIP)){
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new BusinessTrip());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.VACATION)){
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Vacation());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.PAYROLL_CONFIGURATION)){
            model.addAttribute(Constants.FORMULA_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.LIST, payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new PayrollConfiguration());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/payroll-configuration")
    public String postInventory(@ModelAttribute(Constants.FORM) @Validated PayrollConfiguration payrollConfiguration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            payrollConfigurationRepository.save(payrollConfiguration);
        }
        return mapPost(payrollConfiguration, binding, redirectAttributes);
    }
}
