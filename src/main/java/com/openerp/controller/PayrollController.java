package com.openerp.controller;

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

import java.time.YearMonth;
import java.util.ArrayList;
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
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new WorkingHourRecord(Util.getUserBranch(getSessionUser().getEmployee().getOrganization())));
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.PAYROLL_CONFIGURATION)){
            model.addAttribute(Constants.FORMULA_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("formula-type"));
            model.addAttribute(Constants.LIST, payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new PayrollConfiguration());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ADVANCE)){
            model.addAttribute(Constants.LIST, advanceRepository.getAdvancesByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Advance());
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

    @PostMapping(value = "/working-hour-record")
    public String postWorkingHourRecordFilter(@ModelAttribute(Constants.FORM) @Validated WorkingHourRecord workingHourRecord, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        WorkingHourRecord whr = null;
        if(!binding.hasErrors()){
            if(workingHourRecord.getMonthYear().length()>6 && workingHourRecord.getMonthYear().contains("-")){
                int year = Integer.parseInt(workingHourRecord.getMonthYear().split("-")[0]);
                int month = Integer.parseInt(workingHourRecord.getMonthYear().split("-")[1]);
                YearMonth yearMonthObject = YearMonth.of(year, month);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                workingHourRecord.setYear(year);
                workingHourRecord.setMonth(month);
                redirectAttributes.addFlashAttribute(Constants.DAYS_IN_MONTH, daysInMonth);
                whr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndBranch_Id(workingHourRecord.getMonth(), workingHourRecord.getYear(), workingHourRecord.getBranch().getId());
                if(whr ==null){
                    workingHourRecordRepository.save(workingHourRecord);
                    List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(workingHourRecord.getBranch().getId());
                    List<WorkingHourRecordEmployee> workingHourRecordEmployees = new ArrayList<>();
                    for(Employee employee: employees){
                        WorkingHourRecordEmployee workingHourRecordEmployee = new WorkingHourRecordEmployee(workingHourRecord, employee, employee.getPerson().getFullName(), employee.getPosition().getName(), employee.getOrganization().getName());
                        workingHourRecordEmployeeRepository.save(workingHourRecordEmployee);
                        List<WorkingHourRecordEmployeeIdentifier> workingHourRecordEmployeeIdentifiers = new ArrayList<>();
                        for(int i=1; i<=daysInMonth; i++){
                            Date date = DateUtility.generate(i, workingHourRecord.getMonth(), workingHourRecord.getYear());
                            WorkingHourRecordEmployeeIdentifier workingHourRecordEmployeeIdentifier = new WorkingHourRecordEmployeeIdentifier(workingHourRecordEmployee, SkeletonController.identify(employee, date), i, date.getDay());
                            workingHourRecordEmployeeIdentifiers.add(workingHourRecordEmployeeIdentifier);
                        }
                        workingHourRecordEmployee.setWorkingHourRecordEmployeeIdentifiers(workingHourRecordEmployeeIdentifiers);
                        workingHourRecordEmployeeIdentifierRepository.saveAll(workingHourRecordEmployeeIdentifiers);
                    }
                    workingHourRecord.setWorkingHourRecordEmployees(workingHourRecordEmployees);
                }
                whr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndBranch_Id(workingHourRecord.getMonth(), workingHourRecord.getYear(), workingHourRecord.getBranch().getId());
                whr.setWorkingHourRecordEmployees(workingHourRecordEmployeeRepository.getWorkingHourRecordEmployeesByWorkingHourRecord_Id(whr.getId()));
            }
        }
        return mapPost2(whr, binding, redirectAttributes);
    }

    @PostMapping(value = "/working-hour-record/save")
    public String postWorkingHourRecordSave(@ModelAttribute(Constants.FORM) @Validated WorkingHourRecord workingHourRecord, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            for(WorkingHourRecordEmployee whre: workingHourRecord.getWorkingHourRecordEmployees()){
                for(WorkingHourRecordEmployeeIdentifier whrei: whre.getWorkingHourRecordEmployeeIdentifiers()){
                    workingHourRecordEmployeeIdentifierRepository.save(whrei);
                }
            }
        }
        return mapPost(workingHourRecord, binding, redirectAttributes, "/payroll/working-hour-record");
    }

    @PostMapping(value = "/advance")
    public String postAdvance(@ModelAttribute(Constants.FORM) @Validated Advance advance, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            advanceRepository.save(advance);
        }
        return mapPost(advance, binding, redirectAttributes);
    }

    @PostMapping(value = "/advance/approve")
    public String postAdvanceApprove(@ModelAttribute(Constants.FORM) @Validated Advance advance, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            Advance adv = advanceRepository.getAdvanceById(advance.getId());
            adv.setDescription(advance.getDescription());
            adv.setPayed(advance.getPayed());
            adv.setApprove(true);
            adv.setApproveDate(new Date());
            advanceRepository.save(adv);
        }
        return mapPost(advance, binding, redirectAttributes, "/payroll/advance");
    }
}
