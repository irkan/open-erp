package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        if (page.equalsIgnoreCase(Constants.ROUTE.WORKING_HOUR_RECORD)){
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier"));
            if(!model.containsAttribute(Constants.YEAR_MONTH)){
                model.addAttribute(Constants.YEAR_MONTH, DateUtility.getYearMonth(new Date()));
            }
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new WorkingHourRecord(getSessionOrganization()));
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.PAYROLL_CONFIGURATION)){
            model.addAttribute(Constants.FORMULA_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("formula-type"));
            model.addAttribute(Constants.LIST, payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new PayrollConfiguration());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ADVANCE)){
            model.addAttribute(Constants.ADVANCES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("advance"));
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization()));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Advance());
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Advance(!canViewAll()?getSessionOrganization():null));
            }
            model.addAttribute(Constants.LIST, advanceService.findAll((Advance) model.asMap().get(Constants.FILTER), PageRequest.of(0, 100, Sort.by("id").descending())));
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SALARY)){
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Salary(new WorkingHourRecord(getSessionOrganization())));
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SALARY_EMPLOYEE)){
            List<SalaryEmployee> salaryEmployees = salaryEmployeeRepository.getSalaryEmployeesBySalary_ActiveAndEmployee_IdOrderByEmployeeDesc(true, Integer.parseInt(data.get()));
            model.addAttribute(Constants.LIST, salaryEmployees);
        }
        return "layout";
    }

    @PostMapping(value = "/payroll-configuration")
    public String postInventory(@ModelAttribute(Constants.FORM) @Validated PayrollConfiguration payrollConfiguration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            payrollConfigurationRepository.save(payrollConfiguration);
            log("payroll_configuration", "create/edit", payrollConfiguration.getId(), payrollConfiguration.toString());
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
                whr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndOrganization(workingHourRecord.getMonth(), workingHourRecord.getYear(), workingHourRecord.getOrganization());
                if(whr ==null){
                    WorkingHourRecord owhr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndOrganization(workingHourRecord.getMonth()-1, workingHourRecord.getYear(), workingHourRecord.getOrganization());
                    workingHourRecord.setId(null);
                    workingHourRecordRepository.save(workingHourRecord);
                    log("payroll_working_hour_record", "create/edit", workingHourRecord.getId(), workingHourRecord.toString());
                    List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(workingHourRecord.getOrganization());
                    List<WorkingHourRecordEmployee> workingHourRecordEmployees = new ArrayList<>();
                    List<Dictionary> identifiers = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier");
                    for(Employee employee: employees){
                        List<WorkingHourRecordEmployeeDayCalculation> owhedcs = workingHourRecordEmployeeDayCalculationRepository.getWorkingHourRecordEmployeeDayCalculationsByKeyAndWorkingHourRecordEmployee_EmployeeOrderByWorkingHourRecordEmployeeDesc("HMQ", employee);
                        double balanceVacationDays = owhedcs.size()>0?owhedcs.get(0).getValue():0;

                        WorkingHourRecordEmployee workingHourRecordEmployee = new WorkingHourRecordEmployee(workingHourRecord, employee, employee.getPerson().getFullName(), employee.getPosition().getName(), employee.getOrganization().getName());
                        workingHourRecordEmployeeRepository.save(workingHourRecordEmployee);
                        log("payroll_working_hour_record_employee", "create/edit", workingHourRecordEmployee.getId(), workingHourRecordEmployee.toString());
                        List<WorkingHourRecordEmployeeIdentifier> workingHourRecordEmployeeIdentifiers = new ArrayList<>();
                        for(int i=1; i<=daysInMonth; i++){
                            Date date = DateUtility.generate(i, workingHourRecord.getMonth(), workingHourRecord.getYear());
                            WorkingHourRecordEmployeeIdentifier workingHourRecordEmployeeIdentifier = new WorkingHourRecordEmployeeIdentifier(workingHourRecordEmployee, SkeletonController.identify(employee, date), i, date.getDay());
                            workingHourRecordEmployeeIdentifiers.add(workingHourRecordEmployeeIdentifier);
                        }
                        workingHourRecordEmployee.setWorkingHourRecordEmployeeIdentifiers(workingHourRecordEmployeeIdentifiers);
                        workingHourRecordEmployeeIdentifierRepository.saveAll(workingHourRecordEmployeeIdentifiers);
                        // yuxarida workingHourRecordEmployeeIdentifierRepository-da Id de seflik olur
                        //log("payroll_working_hour_record_employee", "create/edit", workingHourRecordEmployeeIdentifiers.getId(), workingHourRecordEmployeeIdentifiers.toString());
                        List<WorkingHourRecordEmployeeDayCalculation> workingHourRecordEmployeeDayCalculations = new ArrayList<>();
                        for(WorkingHourRecordEmployeeDayCalculation whredc: Util.calculateWorkingHourRecordEmployeeDay(workingHourRecordEmployee, identifiers, balanceVacationDays)){
                            workingHourRecordEmployeeDayCalculations.add(whredc);
                        }
                        workingHourRecordEmployeeDayCalculationRepository.saveAll(workingHourRecordEmployeeDayCalculations);
                       // Id de sef olur
                        // log("payroll_working_hour_record_employee", "create/edit", workingHourRecordEmployeeDayCalculations.getId(), workingHourRecordEmployeeDayCalculations.toString());
                    }
                    workingHourRecord.setWorkingHourRecordEmployees(workingHourRecordEmployees);
                }
                whr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndOrganization(workingHourRecord.getMonth(), workingHourRecord.getYear(), workingHourRecord.getOrganization());
                whr.setWorkingHourRecordEmployees(workingHourRecordEmployeeRepository.getWorkingHourRecordEmployeesByWorkingHourRecord_Id(whr.getId()));
                for(WorkingHourRecordEmployee whre: whr.getWorkingHourRecordEmployees()){
                    whre.setWorkingHourRecordEmployeeDayCalculations(workingHourRecordEmployeeDayCalculationRepository.getWorkingHourRecordEmployeeDayCalculationsByWorkingHourRecordEmployee(whre));
                }
            }
        }
        return mapPost2(whr, binding, redirectAttributes);
    }

    @PostMapping(value = "/working-hour-record/approve")
    public String postWorkingHourRecordApprove(@ModelAttribute(Constants.FORM) @Validated WorkingHourRecord workingHourRecord2, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        WorkingHourRecord workingHourRecord = workingHourRecordRepository.getWorkingHourRecordById(workingHourRecord2.getId());
        if(!binding.hasErrors()) {
            workingHourRecord.setApprove(!workingHourRecord.getApprove());
            if (workingHourRecord.getApprove()) {
                workingHourRecord.setApproveDate(new Date());
                workingHourRecord.setApprovedUser(getSessionUser());
            }
            workingHourRecordRepository.save(workingHourRecord);
            log("payroll_working_hour_record", "create/edit", workingHourRecord.getId(), workingHourRecord.toString());
            YearMonth yearMonthObject = YearMonth.of(workingHourRecord.getYear(), workingHourRecord.getMonth());
            int daysInMonth = yearMonthObject.lengthOfMonth();
            redirectAttributes.addFlashAttribute(Constants.DAYS_IN_MONTH, daysInMonth);
        }
        workingHourRecord.setWorkingHourRecordEmployees(workingHourRecordEmployeeRepository.getWorkingHourRecordEmployeesByWorkingHourRecord_Id(workingHourRecord.getId()));
        for(WorkingHourRecordEmployee whre: workingHourRecord.getWorkingHourRecordEmployees()){
            whre.setWorkingHourRecordEmployeeDayCalculations(workingHourRecordEmployeeDayCalculationRepository.getWorkingHourRecordEmployeeDayCalculationsByWorkingHourRecordEmployee(whre));
        }
        return mapPost2(workingHourRecord, binding, redirectAttributes, "/payroll/working-hour-record");
    }

    @PostMapping(value = "/working-hour-record/save")
    public String postWorkingHourRecordSave(@ModelAttribute(Constants.FORM) @Validated WorkingHourRecord workingHourRecord, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            YearMonth yearMonthObject = YearMonth.of(workingHourRecord.getYear(), workingHourRecord.getMonth());
            int daysInMonth = yearMonthObject.lengthOfMonth();
            redirectAttributes.addFlashAttribute(Constants.DAYS_IN_MONTH, daysInMonth);
            List<Dictionary> identifiers = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier");
            for(WorkingHourRecordEmployee whre: workingHourRecord.getWorkingHourRecordEmployees()){
                List<WorkingHourRecordEmployeeDayCalculation> owhedcs = workingHourRecordEmployeeDayCalculationRepository.getWorkingHourRecordEmployeeDayCalculationsByKeyAndWorkingHourRecordEmployee_EmployeeOrderByWorkingHourRecordEmployeeDesc("HMQ", whre.getEmployee());
                double balanceVacationDays = owhedcs.size()>0?owhedcs.get(0).getValue():0;
                for(WorkingHourRecordEmployeeIdentifier whrei: whre.getWorkingHourRecordEmployeeIdentifiers()){
                    workingHourRecordEmployeeIdentifierRepository.save(whrei);
                    log("payroll_working_hour_record_employee_identifier", "create/edit", whrei.getId(), whrei.toString());
                }
                List<WorkingHourRecordEmployeeDayCalculation> workingHourRecordEmployeeDayCalculations = workingHourRecordEmployeeDayCalculationRepository.getWorkingHourRecordEmployeeDayCalculationsByWorkingHourRecordEmployee(whre);
                whre.setWorkingHourRecordEmployeeDayCalculations(workingHourRecordEmployeeDayCalculations);
                for(WorkingHourRecordEmployeeDayCalculation whredc: Util.calculateWorkingHourRecordEmployeeDay(whre, identifiers, balanceVacationDays)){
                    workingHourRecordEmployeeDayCalculationRepository.save(whredc);
                    log("payroll_working_hour_record_employee_day_calculation", "create/edit", whredc.getId(), whredc.toString());
                }
            }
        }
        WorkingHourRecord whr = workingHourRecordRepository.getWorkingHourRecordById(workingHourRecord.getId());
        whr.setWorkingHourRecordEmployees(workingHourRecordEmployeeRepository.getWorkingHourRecordEmployeesByWorkingHourRecord_Id(whr.getId()));
        for(WorkingHourRecordEmployee whre: whr.getWorkingHourRecordEmployees()){
            whre.setWorkingHourRecordEmployeeDayCalculations(workingHourRecordEmployeeDayCalculationRepository.getWorkingHourRecordEmployeeDayCalculationsByWorkingHourRecordEmployee(whre));
        }
        return mapPost2(whr, binding, redirectAttributes, "/payroll/working-hour-record");
    }

    @PostMapping(value = "/working-hour-record/reload")
    public String postWorkingHourRecordReload(@ModelAttribute(Constants.FORM) @Validated WorkingHourRecord workingHourRecord2, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        WorkingHourRecord workingHourRecord = workingHourRecordRepository.getWorkingHourRecordById(workingHourRecord2.getId());
        if(!binding.hasErrors()){
            YearMonth yearMonthObject = YearMonth.of(workingHourRecord.getYear(), workingHourRecord.getMonth());
            int daysInMonth = yearMonthObject.lengthOfMonth();
            redirectAttributes.addFlashAttribute(Constants.DAYS_IN_MONTH, daysInMonth);
            List<Dictionary> identifiers = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier");
            for(WorkingHourRecordEmployee whre: workingHourRecord.getWorkingHourRecordEmployees()){
                List<WorkingHourRecordEmployeeIdentifier> whreis = new ArrayList<>();
                List<WorkingHourRecordEmployeeDayCalculation> owhedcs = workingHourRecordEmployeeDayCalculationRepository.getWorkingHourRecordEmployeeDayCalculationsByKeyAndWorkingHourRecordEmployee_EmployeeOrderByWorkingHourRecordEmployeeDesc("HMQ", whre.getEmployee());
                double balanceVacationDays = owhedcs.size()>0?owhedcs.get(0).getValue():0;
                for(WorkingHourRecordEmployeeIdentifier whrei: whre.getWorkingHourRecordEmployeeIdentifiers()){
                    String date = whrei.getMonthDay()>9?String.valueOf(whrei.getMonthDay()):("0"+whrei.getMonthDay());
                            date += ".";
                            date += workingHourRecord.getMonth()>9?String.valueOf(workingHourRecord.getMonth()):("0"+workingHourRecord.getMonth());
                            date += "." + String.valueOf(workingHourRecord.getYear());
                    String value = SkeletonController.identify(whre.getEmployee(), DateUtility.getUtilDate(date));
                    if(!value.equalsIgnoreCase("İG")){
                        whrei.setIdentifier(value);
                    }
                    whreis.add(whrei);
                }
                whre.setWorkingHourRecordEmployeeIdentifiers(whreis);

                List<WorkingHourRecordEmployeeDayCalculation> workingHourRecordEmployeeDayCalculations = new ArrayList<>();
                for(WorkingHourRecordEmployeeDayCalculation whredc: Util.calculateWorkingHourRecordEmployeeDay(whre, identifiers, balanceVacationDays)){
                    workingHourRecordEmployeeDayCalculations.add(whredc);
                }
                whre.setWorkingHourRecordEmployeeDayCalculations(workingHourRecordEmployeeDayCalculations);
            }
        }
        return mapPost2(workingHourRecord, binding, redirectAttributes, "/payroll/working-hour-record");
    }

    @PostMapping(value = "/advance")
    public String postAdvance(@ModelAttribute(Constants.FORM) @Validated Advance advance, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            advance.setOrganization(getSessionOrganization());
            advanceRepository.save(advance);
            log("payroll_advance", "create/edit", advance.getId(), advance.toString());
        }
        return mapPost(advance, binding, redirectAttributes);
    }

    @PostMapping(value = "/advance/filter")
    public String postAdvanceFilter(@ModelAttribute(Constants.FILTER) @Validated Advance advance, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(advance, binding, redirectAttributes, "/payroll/advance");
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
            log("payroll_advance", "create/edit", adv.getId(), adv.toString());
        }
        return mapPost(advance, binding, redirectAttributes, "/payroll/advance");
    }

    @PostMapping(value = "/salary/calculate")
    public String postSalaryCalculate(@ModelAttribute(Constants.FORM) @Validated Salary salary, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Salary slry = new Salary();
        WorkingHourRecord whr = null;
        if(!binding.hasErrors()){
            if(salary.getWorkingHourRecord().getMonthYear().length()>6 && salary.getWorkingHourRecord().getMonthYear().contains("-")){
                int year = Integer.parseInt(salary.getWorkingHourRecord().getMonthYear().split("-")[0]);
                int month = Integer.parseInt(salary.getWorkingHourRecord().getMonthYear().split("-")[1]);
                YearMonth yearMonthObject = YearMonth.of(year, month);
                int daysInMonth = yearMonthObject.lengthOfMonth();
                salary.getWorkingHourRecord().setYear(year);
                salary.getWorkingHourRecord().setMonth(month);
                redirectAttributes.addFlashAttribute(Constants.DAYS_IN_MONTH, daysInMonth);
                whr = workingHourRecordRepository.getWorkingHourRecordByActiveTrueAndMonthAndYearAndOrganization(salary.getWorkingHourRecord().getMonth(), salary.getWorkingHourRecord().getYear(), salary.getWorkingHourRecord().getOrganization());
                List<String> messages = new ArrayList<>();
                if(whr==null){
                    messages.add("İş vaxtının uçotu cədvəli tapılmadı!");
                }
                if(whr!=null && !whr.getApprove()){
                    messages.add("İş vaxtının uçotu cədvəli təsdiq edilməyib!");
                }
                if(messages.size()>0){
                    redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, new Response(Constants.STATUS.ERROR, messages));
                }
                slry.setWorkingHourRecord(whr);
                if(whr!=null && whr.getApprove()){
                    slry = salaryRepository.getSalaryByActiveTrueAndWorkingHourRecord(whr);
                    if(slry==null){
                        slry = new Salary(whr);
                        List<SalaryEmployee> salaryEmployees = new ArrayList<>();
                        for(WorkingHourRecordEmployee whre: workingHourRecordEmployeeRepository.getWorkingHourRecordEmployeesByWorkingHourRecord_Id(whr.getId())){
                            salaryEmployees.add(new SalaryEmployee(slry, whre.getEmployee(), whre));
                        }
                        slry.setSalaryEmployees(salaryEmployees);
                        salaryRepository.save(slry);
                        log("payroll_salary", "create/edit", slry.getId(), slry.toString());

                        List<PayrollConfiguration> payrollConfigurations = payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById();
                        List<Dictionary> employeeAdditionalFields = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-payroll-field");
                        ScriptEngineManager mgr = new ScriptEngineManager();
                        ScriptEngine engine = mgr.getEngineByName("JavaScript");
                        for(SalaryEmployee se: slry.getSalaryEmployees()){
                            List<EmployeePayrollDetail> employeeDetails = se.getWorkingHourRecordEmployee().getEmployee().getEmployeePayrollDetails();
                            List<WorkingHourRecordEmployeeDayCalculation> workingHourRecordEmployeeDayCalculations = se.getWorkingHourRecordEmployee().getWorkingHourRecordEmployeeDayCalculations();
                            List<SalaryEmployeeDetail> salaryEmployeeDetails = new ArrayList<>();
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
                            String total_working_days = Util.findPayrollConfiguration(payrollConfigurations,"{total_working_days}")
                                    .replaceAll(Pattern.quote("{uig}"), Util.findWorkingHourRecordEmployeeDayCalculations(workingHourRecordEmployeeDayCalculations, "ÜİG"));
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{total_working_days}"),
                                            "{total_working_days}",
                                            String.valueOf(engine.eval(total_working_days)),
                                            total_working_days,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{total_working_days}")
                                    )
                            );
                            String calculated_working_days = Util.findPayrollConfiguration(payrollConfigurations,"{calculated_working_days}")
                                    .replaceAll(Pattern.quote("{hig}"), Util.findWorkingHourRecordEmployeeDayCalculations(workingHourRecordEmployeeDayCalculations, "HİG"));
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{calculated_working_days}"),
                                            "{calculated_working_days}",
                                            String.valueOf(engine.eval(calculated_working_days)),
                                            calculated_working_days,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{calculated_working_days}")
                                    )
                            );
                            String calculated_gross_salary = Util.findPayrollConfiguration(payrollConfigurations,"{calculated_gross_salary}")
                                    .replaceAll(Pattern.quote("{gross_salary}"), gross_salary)
                                    .replaceAll(Pattern.quote("{calculated_working_days}"), calculated_working_days)
                                    .replaceAll(Pattern.quote("{total_working_days}"), total_working_days);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{calculated_gross_salary}"),
                                            "{calculated_gross_salary}",
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(calculated_gross_salary)))),
                                            calculated_gross_salary,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{calculated_gross_salary}")
                                    )
                            );
                            String salary1 = Util.findEmployeeDetail(employeeDetails, "{salary}");
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findEmployeeDetailDescription(employeeAdditionalFields, "{salary}"),
                                            "{salary}",
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(salary1)))),
                                            salary1,
                                            salary1
                                    )
                            );
                            String calculated_salary = Util.findPayrollConfiguration(payrollConfigurations,"{calculated_salary}")
                                    .replaceAll(Pattern.quote("{salary}"), salary1)
                                    .replaceAll(Pattern.quote("{calculated_working_days}"), calculated_working_days)
                                    .replaceAll(Pattern.quote("{total_working_days}"), total_working_days);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{calculated_salary}"),
                                            "{calculated_salary}",
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(calculated_salary)))),
                                            calculated_salary,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{calculated_salary}")
                                    )
                            );
                            String percent = "*0.01";
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
                                    .replaceAll(Pattern.quote("{calculated_gross_salary}"), String.valueOf(engine.eval(calculated_gross_salary)))
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
                                    .replaceAll(Pattern.quote("{calculated_gross_salary}"), String.valueOf(engine.eval(calculated_gross_salary)))
                                    .replaceAll(Pattern.quote("{minimal_salary}"), minimal_salary)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{dsmf_deduction}"),
                                            "{dsmf_deduction}",
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(dsmf_deduction)))),
                                            dsmf_deduction,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{dsmf_deduction}")
                                    )
                            );
                            String membership_fee_for_trade_union = Util.findPayrollConfiguration(payrollConfigurations,"{membership_fee_for_trade_union}")
                                    .replaceAll(Pattern.quote("{calculated_gross_salary}"), String.valueOf(engine.eval(calculated_gross_salary)))
                                    .replaceAll(Pattern.quote("{membership_fee_for_trade_union_fee}"), membership_fee_for_trade_union_fee)
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{membership_fee_for_trade_union}"),
                                            "{membership_fee_for_trade_union}",
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(membership_fee_for_trade_union)))),
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
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(compulsory_health_insurance)))),
                                            compulsory_health_insurance,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{compulsory_health_insurance}")
                                    )
                            );
                            String unemployment_insurance = Util.findPayrollConfiguration(payrollConfigurations,"{unemployment_insurance}")
                                    .replaceAll(Pattern.quote("{calculated_gross_salary}"), String.valueOf(engine.eval(calculated_gross_salary)))
                                    .replaceAll(Pattern.quote("%"), percent);
                            salaryEmployeeDetails.add(
                                    new SalaryEmployeeDetail(
                                            se,
                                            Util.findPayrollConfigurationDescription(payrollConfigurations, "{unemployment_insurance}"),
                                            "{unemployment_insurance}",
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(unemployment_insurance)))),
                                            unemployment_insurance,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{unemployment_insurance}")
                                    )
                            );
                            String total_amount_payable_official = Util.findPayrollConfiguration(payrollConfigurations,"{total_amount_payable_official}")
                                    .replaceAll(Pattern.quote("{calculated_gross_salary}"), String.valueOf(engine.eval(calculated_gross_salary)))
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
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(total_amount_payable_official)))),
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
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(work_experience_salary)))),
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
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(total_salary)))),
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
                                            Util.format(Double.parseDouble(String.valueOf(engine.eval(total_amount_payable_non_official)))),
                                            total_amount_payable_non_official,
                                            Util.findPayrollConfiguration(payrollConfigurations,"{total_amount_payable_non_official}")
                                    )
                            );
                            se.setSalaryEmployeeDetails(salaryEmployeeDetails);
                            salaryEmployeeDetailRepository.saveAll(salaryEmployeeDetails);
                           // Id de sef olur
                            // log("payroll_salary", "create/edit", salaryEmployeeDetails.getId(), salaryEmployeeDetails.toString());
                        }
                    }
                }
            }
        }
        if(slry.getSalaryEmployees()!=null){
            for(SalaryEmployee salaryEmployee: slry.getSalaryEmployees()){
                salaryEmployee.setSalaryEmployeeDetails(salaryEmployeeDetailRepository.getSalaryEmployeeDetailsBySalaryEmployee(salaryEmployee));
            }
        }
        return mapPost2(slry, binding, redirectAttributes, "/payroll/salary");
    }
}
