package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.ReadWriteExcelFile;
import com.openerp.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.YearMonth;
import java.util.Date;
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

        if (page.equalsIgnoreCase(Constants.ROUTE.WORKING_HOUR_RECORD)){
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier"));
            model.addAttribute(Constants.BRANCHES, organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch"));
            if(!model.containsAttribute(Constants.YEAR_MONTH)){
                model.addAttribute(Constants.YEAR_MONTH, DateUtility.getYearMonth(new Date()));
            }
            if(!model.containsAttribute(Constants.BRANCH_ID)){
                model.addAttribute(Constants.BRANCH_ID, getSessionUser().getEmployee().getOrganization().getId());
            }
            model.addAttribute(Constants.LIST, employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(Integer.parseInt(model.asMap().get(Constants.BRANCH_ID).toString())));

            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new WorkingHourRecord());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.PAYROLL_CONFIGURATION)){
            model.addAttribute(Constants.FORMULA_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("formula-type"));
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

    @PostMapping(value = "/working-hour-record/search")
    public String postWorkingHourRecordFilter(@RequestParam(name = "branch", required = true) int branchId, @RequestParam(name = "monthYear", required = true) String monthYear, RedirectAttributes redirectAttributes) throws Exception {
        if(monthYear.length()>6 && monthYear.contains("-")){
            int year = Integer.parseInt(monthYear.split("-")[0]);
            int month = Integer.parseInt(monthYear.split("-")[1]);
            YearMonth yearMonthObject = YearMonth.of(year, month);
            int daysInMonth = yearMonthObject.lengthOfMonth();
            redirectAttributes.addFlashAttribute(Constants.DAYS_IN_MONTH, daysInMonth);
            redirectAttributes.addFlashAttribute(Constants.YEAR_MONTH, monthYear);
            redirectAttributes.addFlashAttribute(Constants.BRANCH_ID, branchId);
        }
        return mapPost(redirectAttributes, "/payroll/working-hour-record");
    }
}
