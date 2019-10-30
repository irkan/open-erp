package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.ReadWriteExcelFile;
import com.openerp.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/hr")
public class HRController extends SkeletonController {

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

        if (page.equalsIgnoreCase(Constants.ROUTE.ORGANIZATION)){
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.ORGANIZATION_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("organization-type"));
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.LIST, organizationRepository.findOrganizationsByOrganizationIsNullAndActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Organization());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.EMPLOYEE)){
            model.addAttribute(Constants.EMPLOYEE_PAYROLL_FIELDS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-payroll-field"));
            model.addAttribute(Constants.EMPLOYEE_SALE_FIELDS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-sale-field"));
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.POSITIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position"));
            model.addAttribute(Constants.NATIONALITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality"));
            model.addAttribute(Constants.GENDERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender"));
            model.addAttribute(Constants.MARITAL_STATUSES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("marital-status"));
            model.addAttribute(Constants.WEEK_DAYS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("week-day"));
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.LIST, employeeRepository.getEmployeesByContractEndDateIsNull());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Employee());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.NON_WORKING_DAY)){
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier"));
            model.addAttribute(Constants.LIST, nonWorkingDayRepository.getNonWorkingDaysByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new NonWorkingDay());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SHORTENED_WORKING_DAY)){
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier"));
            model.addAttribute(Constants.SHORTENED_TIMES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("shortened-time"));
            model.addAttribute(Constants.LIST, shortenedWorkingDayRepository.getShortenedWorkingDaysByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new ShortenedWorkingDay());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.WORK_ATTENDANCE)){

        } else if (page.equalsIgnoreCase(Constants.ROUTE.BUSINESS_TRIP)){
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.getEmployeesByContractEndDateIsNull());
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("business-trip", "identifier"));
            model.addAttribute(Constants.LIST, businessTripRepository.getBusinessTripsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new BusinessTrip());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.VACATION)){
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.getEmployeesByContractEndDateIsNull());
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("vacation", "identifier"));
            model.addAttribute(Constants.LIST, vacationRepository.getVacationsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Vacation());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ILLNESS)){
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.getEmployeesByContractEndDateIsNull());
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("illness", "identifier"));
            model.addAttribute(Constants.LIST, illnessRepository.getIllnessesByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Illness());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/organization")
    public String postOrganization(@ModelAttribute(Constants.FORM) @Validated Organization organization, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            organizationRepository.save(organization);
        }
        return mapPost(organization, binding, redirectAttributes);
    }

    @PostMapping(value = "/employee")
    public String postEmployee(@ModelAttribute(Constants.FORM) @Validated Employee employee, @RequestParam(name = "employeeRestDays", defaultValue = "0") int[] ids, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(employee.getPerson().getFirstName().length()<2){
            FieldError fieldError = new FieldError("person.firstName", "person.firstName", "Minimum 2 simvol olmalıdır!");
            binding.addError(fieldError);
        }
        if(employee.getPerson().getLastName().length()<2){
            FieldError fieldError = new FieldError("person.lastName", "person.lastName", "Minimum 2 simvol olmalıdır!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            if(employee!=null && employee.getId()!=null){
                employeeRestDayRepository.deleteInBatch(employeeRestDayRepository.getEmployeeRestDaysByEmployee(employee));
            }
            List<EmployeeRestDay> erds = new ArrayList<>();
            if(ids[0]!=0){
                for(int id: ids){
                    Dictionary weekDay = dictionaryRepository.getDictionaryById(id);
                    if(weekDay!=null){
                        EmployeeRestDay erd = new EmployeeRestDay(employee, weekDay.getName(), weekDay.getAttr1(), Integer.parseInt(weekDay.getAttr2()), weekDay);
                        erds.add(erd);
                    }
                }
            }
            employee.setEmployeeRestDays(erds);
            int employeeId = 0;
            if(employee.getId()!=null){
                employeeId = employee.getId();
            }
            List<PayrollConfiguration> payrollConfigurations = payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById();
            employeePayrollDetailRepository.deleteInBatch(employeePayrollDetailRepository.getEmployeePayrollDetailsByEmployee_Id(employeeId));
            List<EmployeePayrollDetail> employeePayrollDetails = new ArrayList<>();
            for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-payroll-field")){
                EmployeePayrollDetail employeeDetailField1 = new EmployeePayrollDetail(employee, dictionary, dictionary.getAttr1(), dictionary.getAttr2());
                if(employeeDetailField1.getKey().equalsIgnoreCase("{main_vacation_days}")){
                    employeeDetailField1.setValue(Util.calculateMainVacationDays(payrollConfigurations, employee));
                } else if(employeeDetailField1.getKey().equalsIgnoreCase("{additional_vacation_days}")){
                    employeeDetailField1.setValue(Util.calculateAdditionalVacationDays(payrollConfigurations, employee, Util.findPreviousWorkExperience(employeePayrollDetails)));
                }
                employeePayrollDetails.add(employeeDetailField1);
            }
            employee.setEmployeePayrollDetails(employeePayrollDetails);

            employeeSaleDetailRepository.deleteInBatch(employeeSaleDetailRepository.getEmployeeSaleDetailsByEmployee_Id(employeeId));
            List<EmployeeSaleDetail> employeeSaleDetails = new ArrayList<>();
            for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-sale-field")){
                EmployeeSaleDetail employeeDetailField1 = new EmployeeSaleDetail(employee, dictionary, dictionary.getAttr1(), dictionary.getAttr2());
                employeeSaleDetails.add(employeeDetailField1);
            }
            employee.setEmployeeSaleDetails(employeeSaleDetails);

            employeeRepository.save(employee);
        }
        return mapPost(employee, binding, redirectAttributes);
    }

    @PostMapping(value = "/employee/payroll")
    public String postEmployeePayroll(@ModelAttribute(Constants.FORM) @Validated Employee employee, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        employeePayrollDetailRepository.deleteInBatch(employeePayrollDetailRepository.getEmployeePayrollDetailsByEmployee_Id(employee.getId()));
        List<PayrollConfiguration> payrollConfigurations = payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById();
        List<EmployeePayrollDetail> employeePayrollDetails = new ArrayList<>();
        for(int i=0; i<employee.getEmployeePayrollDetails().size(); i++){
            EmployeePayrollDetail employeePayrollDetail = employee.getEmployeePayrollDetails().get(i);
            employeePayrollDetail.setEmployee(employee);
            if(employeePayrollDetail.getKey().equalsIgnoreCase("{main_vacation_days}")){
                employeePayrollDetail.setValue(Util.calculateMainVacationDays(payrollConfigurations, employee));
            } else if(employeePayrollDetail.getKey().equalsIgnoreCase("{additional_vacation_days}")){
                employeePayrollDetail.setValue(Util.calculateAdditionalVacationDays(payrollConfigurations, employee, Util.findPreviousWorkExperience(employeePayrollDetails)));
            }
            if(employeePayrollDetail.getValue().trim().length()<1){
                FieldError fieldError = new FieldError("", "", employeePayrollDetail.getEmployeePayrollField().getName() + ": Boş olmalıdır!");
                binding.addError(fieldError);
            }
            employeePayrollDetails.add(employeePayrollDetail);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            employeePayrollDetailRepository.saveAll(employeePayrollDetails);
        }
        return mapPost(employee, binding, redirectAttributes, "/hr/employee");
    }

    @PostMapping(value = "/employee/sale")
    public String postEmployeeSale(@ModelAttribute(Constants.FORM) @Validated Employee employee, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        employeeSaleDetailRepository.deleteInBatch(employeeSaleDetailRepository.getEmployeeSaleDetailsByEmployee_Id(employee.getId()));
        List<EmployeeSaleDetail> employeeSaleDetails = new ArrayList<>();
        for(int i=0; i<employee.getEmployeeSaleDetails().size(); i++){
            EmployeeSaleDetail employeeSaleDetail = employee.getEmployeeSaleDetails().get(i);
            employeeSaleDetail.setEmployee(employee);
            if(employeeSaleDetail.getValue().trim().length()<1){
                FieldError fieldError = new FieldError("", "", employeeSaleDetail.getEmployeeSaleField().getName() + ": Boş olmalıdır!");
                binding.addError(fieldError);
            }
            employeeSaleDetails.add(employeeSaleDetail);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            employeeSaleDetailRepository.saveAll(employeeSaleDetails);
        }
        return mapPost(employee, binding, redirectAttributes, "/hr/employee");
    }

    @PostMapping(value = "/non-working-day")
    public String postNonWorkingDay(@ModelAttribute(Constants.FORM) @Validated NonWorkingDay nonWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            nonWorkingDayRepository.save(nonWorkingDay);
        }
        return mapPost(nonWorkingDay, binding, redirectAttributes);
    }

    @PostMapping(value = "/non-working-day/upload", consumes = {"multipart/form-data"})
    public String postNonWorkingDayUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        List<NonWorkingDay> nonWorkingDays = ReadWriteExcelFile.readXLSXFileNonWorkingDays(file.getInputStream());
        for(NonWorkingDay nonWorkingDay: nonWorkingDays){
            List<NonWorkingDay> nwds = nonWorkingDayRepository.getNonWorkingDaysByNonWorkingDateAndActiveTrue(nonWorkingDay.getNonWorkingDate());
            if(nwds!=null && nwds.size()==0){
                nonWorkingDayRepository.save(nonWorkingDay);
            }
        }
        return mapPost(redirectAttributes, "/hr/non-working-day");
    }

    @PostMapping(value = "/shortened-working-day")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated ShortenedWorkingDay shortenedWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            shortenedWorkingDayRepository.save(shortenedWorkingDay);
        }
        return mapPost(shortenedWorkingDay, binding, redirectAttributes);
    }

    @PostMapping(value = "/shortened-working-day/upload", consumes = {"multipart/form-data"})
    public String postShortenedWorkingDayUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        List<ShortenedWorkingDay> shortenedWorkingDays = ReadWriteExcelFile.readXLSXFileShortenedWorkingDays(file.getInputStream());
        for(ShortenedWorkingDay shortenedWorkingDay: shortenedWorkingDays){
            List<ShortenedWorkingDay> swds = shortenedWorkingDayRepository.getShortenedWorkingDaysByWorkingDate(shortenedWorkingDay.getWorkingDate());
            if(swds!=null && swds.size()==0){
                shortenedWorkingDayRepository.save(shortenedWorkingDay);
            }
        }
        return mapPost(redirectAttributes, "/hr/shortened-working-day");
    }

    @PostMapping(value = "/vacation")
    public String postVacation(@ModelAttribute(Constants.FORM) @Validated Vacation vacation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            String rangeDates[] = vacation.getDateRange().split("-");
            vacation.setStartDate(DateUtility.getUtilDate(rangeDates[0].trim()));
            vacation.setEndDate(DateUtility.getUtilDate(rangeDates[1].trim()));
            if(vacation.getStartDate().getTime() - vacation.getEmployee().getContractStartDate().getTime() < 15768000000l  && (vacation.getIdentifier().getAttr1().equalsIgnoreCase("M"))){
                FieldError fieldError = new FieldError("dateRange", "dateRange", "İşə başlama tarixindən 6 ayı keçməmişdir!");
                binding.addError(fieldError);
            }

            if(!binding.hasErrors()){
                vacationRepository.save(vacation);

                if(vacation.getId()!=null && vacation.getStartDate()!=null && vacation.getEndDate()!=null){
                    vacationDetailRepository.deleteInBatch(vacationDetailRepository.getVacationDetailsByVacation_Id(vacation.getId()));
                    Calendar start = Calendar.getInstance();
                    start.setTime(vacation.getStartDate());
                    Calendar end = Calendar.getInstance();
                    end.setTime(vacation.getEndDate());
                    end.add(Calendar.DATE, 1);

                    List<VacationDetail> vacationDetails = new ArrayList<>();
                    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                        vacationDetails.add(new VacationDetail(vacation.getIdentifier().getAttr1(), date, vacation, vacation.getEmployee()));
                    }
                    vacationDetailRepository.saveAll(vacationDetails);

                    List<SalaryEmployee> salaryEmployees = salaryEmployeeRepository.getSalaryEmployeesBySalary_ActiveAndEmployee_IdOrderByEmployeeDesc(true, vacation.getEmployee().getId());


                    String description = vacation.getIdentifier().getName() + " " + DateUtility.getFormattedDate(vacation.getStartDate()) + " - " + DateUtility.getFormattedDate(vacation.getEndDate());
                    Advance advance = new Advance(vacation.getIdentifier(), vacation.getEmployee(), description, vacation.getStartDate(), 473.50, false);
                    advanceRepository.save(advance);
                }
            }
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        return mapPost(vacation, binding, redirectAttributes);
    }

    @PostMapping(value = "/business-trip")
    public String postBusinessTrip(@ModelAttribute(Constants.FORM) @Validated BusinessTrip businessTrip, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            String range = businessTrip.getDateRange();
            if(range.length()>15){
                String rangeDates[] = range.split("-");
                businessTrip.setStartDate(DateUtility.getUtilDate(rangeDates[0].trim()));
                businessTrip.setEndDate(DateUtility.getUtilDate(rangeDates[1].trim()));
                businessTripRepository.save(businessTrip);

                if(businessTrip.getId()!=null && businessTrip.getStartDate()!=null && businessTrip.getEndDate()!=null){
                    businessTripDetailRepository.deleteInBatch(businessTripDetailRepository.getBusinessTripDetailsByBusinessTrip_Id(businessTrip.getId()));
                    Calendar start = Calendar.getInstance();
                    start.setTime(businessTrip.getStartDate());
                    Calendar end = Calendar.getInstance();
                    end.setTime(businessTrip.getEndDate());
                    end.add(Calendar.DATE, 1);

                    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                        businessTripDetailRepository.save(new BusinessTripDetail(businessTrip.getIdentifier().getAttr1(), date, businessTrip, businessTrip.getEmployee()));
                    }
                }
            }
        }
        return mapPost(businessTrip, binding, redirectAttributes);
    }

    @PostMapping(value = "/illness")
    public String postIllness(@ModelAttribute(Constants.FORM) @Validated Illness illness, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            String range = illness.getDateRange();
            if(range.length()>15){
                String rangeDates[] = range.split("-");
                illness.setStartDate(DateUtility.getUtilDate(rangeDates[0].trim()));
                illness.setEndDate(DateUtility.getUtilDate(rangeDates[1].trim()));
                illnessRepository.save(illness);

                if(illness.getId()!=null && illness.getStartDate()!=null && illness.getEndDate()!=null){
                    illnessDetailRepository.deleteInBatch(illnessDetailRepository.getIllnessDetailsByIllness_Id(illness.getId()));
                    Calendar start = Calendar.getInstance();
                    start.setTime(illness.getStartDate());
                    Calendar end = Calendar.getInstance();
                    end.setTime(illness.getEndDate());
                    end.add(Calendar.DATE, 1);

                    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                        illnessDetailRepository.save(new IllnessDetail(illness.getIdentifier().getAttr1(), date, illness, illness.getEmployee()));
                    }
                }
            }
        }
        return mapPost(illness, binding, redirectAttributes);
    }
}
