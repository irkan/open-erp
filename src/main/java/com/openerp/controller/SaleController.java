package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/sale")
public class SaleController extends SkeletonController {

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

        if (page.equalsIgnoreCase(Constants.ROUTE.SALE_GROUP)){
            List<Employee> employees;
            if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId());
            } else {
                employees = employeeRepository.getEmployeesByContractEndDateIsNull();
            }
            model.addAttribute(Constants.EMPLOYEES, employees);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new SaleGroup());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/sale-group")
    public String postInventory(@ModelAttribute(Constants.FORM) @Validated SaleGroup saleGroup, @RequestParam(name = "saleGroupEmployees", defaultValue = "0") int[] employeeIds, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            List<SaleGroupEmployee> saleGroupEmployees = new ArrayList<>();
            for(int id: employeeIds){
                if(id!=0){
                    saleGroupEmployees.add(new SaleGroupEmployee(saleGroup, employeeRepository.getEmployeeById(id)));
                }
            }
            saleGroup.setSaleGroupEmployees(saleGroupEmployees);
            saleGroupRepository.save(saleGroup);
        }
        return mapPost(saleGroup, binding, redirectAttributes);
    }
}
