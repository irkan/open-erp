package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.springframework.context.annotation.Description;
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
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier"));
            model.addAttribute(Constants.LIST, advanceRepository.getAdvancesByActiveTrue());
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.getEmployeesByContractEndDateIsNull());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Advance());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SALARY)){
            model.addAttribute(Constants.BRANCHES, organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch"));
            if(!model.containsAttribute(Constants.BRANCH_ID)){
                model.addAttribute(Constants.BRANCH_ID, getSessionUser().getEmployee().getOrganization().getId());
            }
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Salary(new WorkingHourRecord(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()))));
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

    @PostMapping(value = "/working-hour-record/approve")
    public String postWorkingHourRecordApprove(@RequestParam(name = "id", defaultValue = "0") int id, RedirectAttributes redirectAttributes) throws Exception {
        WorkingHourRecord whr = workingHourRecordRepository.getWorkingHourRecordById(id);
        whr.setApprove(!whr.getApprove());
        if(whr.getApprove()){
            whr.setApproveDate(new Date());
            whr.setApprovedUser(getSessionUser());
        }
        workingHourRecordRepository.save(whr);
        return mapPost(redirectAttributes, "/payroll/working-hour-record");
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

    @PostMapping(value = "/salary/calculate")
    public String postSalaryCalculate(@ModelAttribute(Constants.FORM) @Validated Salary salary, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Salary slry = null;
        if(!binding.hasErrors()){
            if(salary.getWorkingHourRecord().getMonthYear().length()>6 && salary.getWorkingHourRecord().getMonthYear().contains("-")){
                int year = Integer.parseInt(salary.getWorkingHourRecord().getMonthYear().split("-")[0]);
                int month = Integer.parseInt(salary.getWorkingHourRecord().getMonthYear().split("-")[1]);
                YearMonth yearMonthObject = YearMonth.of(year, month);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                salary.getWorkingHourRecord().setYear(year);
                salary.getWorkingHourRecord().setMonth(month);
                redirectAttributes.addFlashAttribute(Constants.DAYS_IN_MONTH, daysInMonth);
                WorkingHourRecord whr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndBranch_Id(salary.getWorkingHourRecord().getMonth(), salary.getWorkingHourRecord().getYear(), salary.getWorkingHourRecord().getBranch().getId());
                String message = "";
                if(whr==null){
                    message = "İş vaxtının uçotu cədvəli tapılmadı!";
                }
                if(whr!=null && !whr.getApprove()){
                    message = "İş vaxtının uçotu cədvəli təsdiq edilməyib!";
                }
                redirectAttributes.addFlashAttribute(Constants.ERROR, message);

                if(whr!=null && whr.getApprove()){
                    slry = salaryRepository.getSalaryByActiveTrueAndWorkingHourRecord(whr);
                    if(slry==null){
                        slry = new Salary(whr);
                        List<SalaryEmployee> salaryEmployees = new ArrayList<>();
                        for(WorkingHourRecordEmployee whre: workingHourRecordEmployeeRepository.getWorkingHourRecordEmployeesByWorkingHourRecord_Id(whr.getId())){
                            salaryEmployees.add(new SalaryEmployee(slry, whre));
                        }
                        slry.setSalaryEmployees(salaryEmployees);
                        salaryRepository.save(slry);

                        List<PayrollConfiguration> payrollConfigurations = payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById();
                        List<Dictionary> employeeAdditionalFields = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-additional-field");
                        ScriptEngineManager mgr = new ScriptEngineManager();
                        ScriptEngine engine = mgr.getEngineByName("JavaScript");
                        for(SalaryEmployee se: slry.getSalaryEmployees()){
                            List<EmployeeDetail> employeeDetails = se.getWorkingHourRecordEmployee().getEmployee().getEmployeeDetails();
                            List<SalaryEmployeeDetail> salaryEmployeeDetails = new ArrayList<>();
                            String salary1 = Util.findEmployeeDetail(employeeDetails, "{salary}");
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findEmployeeDetailDescription(employeeAdditionalFields, "{salary}"),
                                            "{salary}",
                                            String.valueOf(engine.eval(salary1)),
                                            salary1,
                                            salary1
                                    )
                            );
                            String percent = "*0.01";
                            String gross_salary = Util.findEmployeeDetail(employeeDetails, "{gross_salary}");
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findEmployeeDetailDescription(employeeAdditionalFields, "{gross_salary}"),
                                            "{gross_salary}",
                                            String.valueOf(engine.eval(gross_salary)),
                                            gross_salary,
                                            gross_salary
                                    )
                            );
                            String allowance = Util.findEmployeeDetail(employeeDetails, "{allowance}");
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findEmployeeDetailDescription(employeeAdditionalFields, "{allowance}"),
                                            "{allowance}",
                                            String.valueOf(engine.eval(allowance)),
                                            allowance,
                                            allowance
                                    )
                            );
                            String membership_fee_for_trade_union_fee = Util.findEmployeeDetail(employeeDetails, "{membership_fee_for_trade_union_fee}");
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findEmployeeDetailDescription(employeeAdditionalFields, "{membership_fee_for_trade_union_fee}"),
                                            "{membership_fee_for_trade_union_fee}",
                                            String.valueOf(engine.eval(membership_fee_for_trade_union_fee)),
                                            membership_fee_for_trade_union_fee,
                                            membership_fee_for_trade_union_fee
                                    )
                            );
                            String minimal_salary = Util.findPayrollConfiguration(payrollConfigurations,"{minimal_salary}");
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{minimal_salary}"),
                                            "{minimal_salary}",
                                            String.valueOf(engine.eval(minimal_salary)),
                                            minimal_salary,
                                            minimal_salary
                                    )
                            );
                            String tax_amount_involved = Util.findPayrollConfiguration(payrollConfigurations,"{tax_amount_involved}")
                                    .replaceAll(Pattern.quote("{gross_salary}"), gross_salary)
                                    .replaceAll(Pattern.quote("{allowance}"), allowance)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{tax_amount_involved}"),
                                            "{tax_amount_involved}",
                                            String.valueOf(engine.eval(tax_amount_involved)),
                                            tax_amount_involved,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{tax_amount_involved}")
                                    )
                            );
                            String tax_income = Util.findPayrollConfiguration(payrollConfigurations,"{tax_income}")
                                    .replaceAll(Pattern.quote("{tax_amount_involved}"), String.valueOf(engine.eval(tax_amount_involved)))
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{tax_income}"),
                                            "{tax_income}",
                                            String.valueOf(engine.eval(tax_income)),
                                            tax_income,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{tax_income}")
                                    )
                            );
                            String dsmf_deduction = Util.findPayrollConfiguration(payrollConfigurations,"{dsmf_deduction}")
                                    .replaceAll(Pattern.quote("{gross_salary}"), gross_salary)
                                    .replaceAll(Pattern.quote("{minimal_salary}"), minimal_salary)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{dsmf_deduction}"),
                                            "{dsmf_deduction}",
                                            String.valueOf(engine.eval(dsmf_deduction)),
                                            dsmf_deduction,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{dsmf_deduction}")
                                    )
                            );
                            String membership_fee_for_trade_union = Util.findPayrollConfiguration(payrollConfigurations,"{membership_fee_for_trade_union}")
                                    .replaceAll(Pattern.quote("{gross_salary}"), gross_salary)
                                    .replaceAll(Pattern.quote("{membership_fee_for_trade_union_fee}"), membership_fee_for_trade_union_fee)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{membership_fee_for_trade_union}"),
                                            "{membership_fee_for_trade_union}",
                                            String.valueOf(engine.eval(membership_fee_for_trade_union)),
                                            membership_fee_for_trade_union,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{membership_fee_for_trade_union}")
                                    )
                            );
                            String compulsory_health_insurance = Util.findPayrollConfiguration(payrollConfigurations,"{compulsory_health_insurance}")
                                    .replaceAll(Pattern.quote("{gross_salary}"), gross_salary)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{compulsory_health_insurance}"),
                                            "{compulsory_health_insurance}",
                                            String.valueOf(engine.eval(compulsory_health_insurance)),
                                            compulsory_health_insurance,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{compulsory_health_insurance}")
                                    )
                            );
                            String unemployment_insurance = Util.findPayrollConfiguration(payrollConfigurations,"{unemployment_insurance}")
                                    .replaceAll(Pattern.quote("{gross_salary}"), gross_salary)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{unemployment_insurance}"),
                                            "{unemployment_insurance}",
                                            String.valueOf(engine.eval(unemployment_insurance)),
                                            unemployment_insurance,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{unemployment_insurance}")
                                    )
                            );
                            String total_amount_payable_official = Util.findPayrollConfiguration(payrollConfigurations,"{total_amount_payable_official}")
                                    .replaceAll(Pattern.quote("{gross_salary}"), gross_salary)
                                    .replaceAll(Pattern.quote("{tax_income}"), String.valueOf(engine.eval(tax_income)))
                                    .replaceAll(Pattern.quote("{dsmf_deduction}"), String.valueOf(engine.eval(dsmf_deduction)))
                                    .replaceAll(Pattern.quote("{unemployment_insurance}"), String.valueOf(engine.eval(unemployment_insurance)))
                                    .replaceAll(Pattern.quote("{compulsory_health_insurance}"), String.valueOf(engine.eval(compulsory_health_insurance)))
                                    .replaceAll(Pattern.quote("{membership_fee_for_trade_union}"), String.valueOf(engine.eval(membership_fee_for_trade_union)));
                            if(Double.parseDouble(String.valueOf(engine.eval(total_amount_payable_official)))<0){
                                total_amount_payable_official = "0";
                            }
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{total_amount_payable_official}"),
                                            "{total_amount_payable_official}",
                                            String.valueOf(engine.eval(total_amount_payable_official)),
                                            total_amount_payable_official,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{total_amount_payable_official}")
                                    )
                            );
                            String work_experience = Util.calculateWorkExperience(se.getWorkingHourRecordEmployee().getEmployee().getContractStartDate());
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            "İş stajı",
                                            "{work_experience}",
                                            String.valueOf(engine.eval(work_experience)),
                                            work_experience,
                                            work_experience
                                    )
                            );
                            String work_experience_salary = Util.findPayrollConfiguration(payrollConfigurations,"{work_experience_salary}")
                                    .replaceAll(Pattern.quote("{work_experience}"), work_experience)
                                    .replaceAll(Pattern.quote("{salary}"), salary1)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{work_experience_salary}"),
                                            "{work_experience_salary}",
                                            String.valueOf(engine.eval(work_experience_salary)),
                                            work_experience_salary,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{work_experience_salary}")
                                    )
                            );
                            String total_salary = Util.findPayrollConfiguration(payrollConfigurations,"{total_salary}")
                                    .replaceAll(Pattern.quote("{work_experience_salary}"), String.valueOf(engine.eval(work_experience_salary)))
                                    .replaceAll(Pattern.quote("{salary}"), salary1);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{total_salary}"),
                                            "{total_salary}",
                                            String.valueOf(engine.eval(total_salary)),
                                            total_salary,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{total_salary}")
                                    )
                            );
                            String total_amount_payable_non_official = Util.findPayrollConfiguration(payrollConfigurations,"{total_amount_payable_non_official}")
                                    .replaceAll(Pattern.quote("{total_salary}"), String.valueOf(engine.eval(total_salary)))
                                    .replaceAll(Pattern.quote("{total_amount_payable_official}"), String.valueOf(engine.eval(total_amount_payable_official)));
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{total_amount_payable_non_official}"),
                                            "{total_amount_payable_non_official}",
                                            String.valueOf(engine.eval(total_amount_payable_non_official)),
                                            total_amount_payable_non_official,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{total_amount_payable_non_official}")
                                    )
                            );

                            salaryEmployeeDetailRepository.saveAll(salaryEmployeeDetails);
                        }
                    }
                    redirectAttributes.addFlashAttribute(Constants.SALARY_EMPLOYEES, salaryEmployeeRepository.getSalaryEmployeesBySalary_Id(slry.getId()));
                }
            }
        }
        //WorkingHourRecord whr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndBranch_Id(salary.getWorkingHourRecord().getMonth(), salary.getWorkingHourRecord().getYear(), salary.getWorkingHourRecord().getBranch().getId());
        //slry = salaryRepository.getSalaryByActiveTrueAndWorkingHourRecord()
        return mapPost2(slry, binding, redirectAttributes, "/payroll/salary");
    }
}
