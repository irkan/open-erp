package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.ReadWriteExcelFile;
import com.openerp.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
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
        if (page.equalsIgnoreCase(Constants.ROUTE.ORGANIZATION)){
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.ORGANIZATION_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("organization-type"));
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.LIST, organizationRepository.findOrganizationsByOrganizationIsNullAndActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Organization());
            }
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(organizationRepository.findAll(), redirectAttributes, page);
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
            List<Employee> employees;
            if(canViewAll()){
                employees = employeeRepository.getEmployeesByActiveTrueOrderByContractEndDate();
            } else {
                employees = employeeRepository.getEmployeesByOrganizationAndActiveTrueOrderByContractEndDate(getSessionOrganization());
            }
            model.addAttribute(Constants.LIST, employees);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Employee(getSessionOrganization()));
            }
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(employees, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.NON_WORKING_DAY)){
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new NonWorkingDay());
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new NonWorkingDay());
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof NonWorkingDay){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<NonWorkingDay> nonWorkingDays = nonWorkingDayService.findAll((NonWorkingDay) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, nonWorkingDays);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(nonWorkingDays, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SHORTENED_WORKING_DAY)){
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("identifier"));
            model.addAttribute(Constants.SHORTENED_TIMES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("shortened-time"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new ShortenedWorkingDay());
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new ShortenedWorkingDay());
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof ShortenedWorkingDay){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<ShortenedWorkingDay> shortenedWorkingDays = shortenedWorkingDayService.findAll((ShortenedWorkingDay) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, shortenedWorkingDays);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(shortenedWorkingDays, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.WORK_ATTENDANCE)){

        } else if (page.equalsIgnoreCase(Constants.ROUTE.BUSINESS_TRIP)){
            model.addAttribute(Constants.EMPLOYEES, canViewAll()?employeeRepository.getEmployeesByContractEndDateIsNullAndActiveTrue():employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization()));
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("business-trip", "identifier"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new BusinessTrip(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new BusinessTrip(!canViewAll()?getSessionOrganization():null));
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof BusinessTrip){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<BusinessTrip> businessTrips = businessTripService.findAll((BusinessTrip) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, businessTrips);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(businessTrips, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.VACATION)){
            model.addAttribute(Constants.EMPLOYEES, canViewAll()?employeeRepository.getEmployeesByContractEndDateIsNullAndActiveTrue():employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization()));
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("vacation", "identifier"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Vacation(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Vacation(!canViewAll()?getSessionOrganization():null));
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Vacation){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<Vacation> vacations = vacationService.findAll((Vacation) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, vacations);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(vacations, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ILLNESS)){
            model.addAttribute(Constants.EMPLOYEES, canViewAll()?employeeRepository.getEmployeesByContractEndDateIsNullAndActiveTrue():employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization()));
            model.addAttribute(Constants.IDENTIFIERS, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("illness", "identifier"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Illness(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Illness(!canViewAll()?getSessionOrganization():null));
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Illness){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<Illness> illnesses = illnessService.findAll((Illness) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, illnesses);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(illnesses, redirectAttributes, page);
            }
        }
        return "layout";
    }

    @PostMapping(value = "/organization")
    public String postOrganization(@ModelAttribute(Constants.FORM) @Validated Organization organization, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            organizationRepository.save(organization);
            log(organization, "organization", "create/edit", organization.getId(), organization.toString());
        }
        return mapPost(organization, binding, redirectAttributes);
    }

    @PostMapping(value = "/employee")
    public String postEmployee(@ModelAttribute(Constants.FORM) @Validated Employee employee, @RequestParam(name = "employeeRestDays", defaultValue = "0") int[] ids, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if (!binding.hasErrors()) {
            employeeRepository.save(employee);
            log(employee, "employee", "create/edit", employee.getId(), employee.toString());

            try{
                if(employee!=null && employee.getId()!=null){
                    employeeRestDayRepository.deleteInBatch(employeeRestDayRepository.getEmployeeRestDaysByEmployee(employee));
                    log(employee, "employee_rest_day", "delete-in-batch", employee.getId(), employee.toString());
                }
                List<EmployeeRestDay> employeeRestDays = new ArrayList<>();
                if(ids[0]!=0){
                    for(int id: ids){
                        Dictionary weekDay = dictionaryRepository.getDictionaryById(id);
                        if(weekDay!=null){
                            employeeRestDays.add(new EmployeeRestDay(employee, weekDay.getName(), weekDay.getAttr1(), Integer.parseInt(weekDay.getAttr2()), weekDay));
                        }
                    }
                    if(employeeRestDays.size()>0){
                        employeeRestDayRepository.saveAll(employeeRestDays);
                    }
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }

            if(employeePayrollDetailRepository.getEmployeePayrollDetailsByEmployee_Id(employee.getId()).size()==0) {
                try {
                    List<PayrollConfiguration> payrollConfigurations = payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById();
                    List<EmployeePayrollDetail> employeePayrollDetails = new ArrayList<>();
                    for (Dictionary dictionary : dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-payroll-field")) {
                        EmployeePayrollDetail employeeDetailField1 = new EmployeePayrollDetail(employee, dictionary, dictionary.getAttr1(), dictionary.getAttr2());
                        if (employeeDetailField1.getKey().equalsIgnoreCase("{main_vacation_days}")) {
                            employeeDetailField1.setValue(Util.calculateMainVacationDays(payrollConfigurations, employee));
                        } else if (employeeDetailField1.getKey().equalsIgnoreCase("{additional_vacation_days}")) {
                            employeeDetailField1.setValue(Util.calculateAdditionalVacationDays(payrollConfigurations, employee, Util.findPreviousWorkExperience(employeePayrollDetails)));
                        }
                        employeePayrollDetails.add(employeeDetailField1);
                    }
                    if (employeePayrollDetails.size() > 0) {
                        employeePayrollDetailRepository.saveAll(employeePayrollDetails);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }

            if(employeeSaleDetailRepository.getEmployeeSaleDetailsByEmployee_Id(employee.getId()).size()==0){
                try{
                    List<EmployeeSaleDetail> employeeSaleDetails = new ArrayList<>();
                    for(Dictionary dictionary: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-sale-field")){
                        employeeSaleDetails.add(new EmployeeSaleDetail(employee, dictionary, dictionary.getAttr1(), dictionary.getAttr2()));
                    }
                    if(employeeSaleDetails.size()>0){
                        employeeSaleDetailRepository.saveAll(employeeSaleDetails);
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }
        }
        return mapPost(employee, binding, redirectAttributes);
    }

    @PostMapping(value = "/employee/payroll")
    public String postEmployeePayroll(@ModelAttribute(Constants.FORM) @Validated Employee object, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Employee employee = employeeRepository.getEmployeeById(object.getId());
        employeePayrollDetailRepository.deleteInBatch(employeePayrollDetailRepository.getEmployeePayrollDetailsByEmployee_Id(employee.getId()));
       // log("employee", "create/edit", employee.getId(), employee.toString());
        List<PayrollConfiguration> payrollConfigurations = payrollConfigurationRepository.getPayrollConfigurationsByActiveTrueOrderById();
        List<EmployeePayrollDetail> employeePayrollDetails = new ArrayList<>();
        for(EmployeePayrollDetail epd: object.getEmployeePayrollDetails()){
            epd.setEmployee(employee);
            if(epd.getKey().equalsIgnoreCase("{main_vacation_days}")){
                epd.setValue(Util.calculateMainVacationDays(payrollConfigurations, employee));
            } else if(epd.getKey().equalsIgnoreCase("{additional_vacation_days}")){
                epd.setValue(Util.calculateAdditionalVacationDays(payrollConfigurations, employee, Util.findPreviousWorkExperience(employeePayrollDetails)));
            }
            if(epd.getValue().trim().length()<1){
                FieldError fieldError = new FieldError("", "", epd.getEmployeePayrollField().getName() + ": Boş olmalıdır!");
                binding.addError(fieldError);
            }
            employeePayrollDetails.add(epd);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            employeePayrollDetailRepository.saveAll(employeePayrollDetails);
            log(employeePayrollDetails, "employee_payroll_detail", "create/edit", null, employeePayrollDetails.toString());
        }
        return mapPost(employee, binding, redirectAttributes, "/hr/employee");
    }

    @PostMapping(value = "/employee/sale")
    public String postEmployeeSale(@ModelAttribute(Constants.FORM) @Validated Employee employee, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        employeeSaleDetailRepository.deleteInBatch(employeeSaleDetailRepository.getEmployeeSaleDetailsByEmployee_Id(employee.getId()));
        //log("employee", "create/edit", employee.getId(), employee.toString());
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
            log(employeeSaleDetails, "employee_sale_detail", "create/edit", null, employeeSaleDetails.toString());
        }
        return mapPost(employee, binding, redirectAttributes, "/hr/employee");
    }

    @PostMapping(value = "/non-working-day")
    public String postNonWorkingDay(@ModelAttribute(Constants.FORM) @Validated NonWorkingDay nonWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            nonWorkingDayRepository.save(nonWorkingDay);
            log(nonWorkingDay, "non_working_day", "create/edit", nonWorkingDay.getId(), nonWorkingDay.toString());
        }
        return mapPost(nonWorkingDay, binding, redirectAttributes);
    }

    @PostMapping(value = "/non-working-day/filter")
    public String postNonWorkingDayFilter(@ModelAttribute(Constants.FILTER) @Validated NonWorkingDay nonWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(nonWorkingDay, binding, redirectAttributes, "/hr/non-working-day");
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
        log(nonWorkingDays, "non_working_day", "create/edit", null, nonWorkingDays.toString(), "NonWorkingDay upload edildi");
        return mapPost(redirectAttributes, "/hr/non-working-day");
    }

    @PostMapping(value = "/shortened-working-day")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated ShortenedWorkingDay shortenedWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            shortenedWorkingDayRepository.save(shortenedWorkingDay);
            log(shortenedWorkingDay, "shortened_working_day", "create/edit", shortenedWorkingDay.getId(), shortenedWorkingDay.toString());
        }
        return mapPost(shortenedWorkingDay, binding, redirectAttributes);
    }

    @PostMapping(value = "/shortened-working-day/filter")
    public String postShortenedWorkingDayFilter(@ModelAttribute(Constants.FILTER) @Validated ShortenedWorkingDay shortenedWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(shortenedWorkingDay, binding, redirectAttributes, "/hr/shortened-working-day");
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
        log(shortenedWorkingDays, "shortened_working_day", "create/edit", null, shortenedWorkingDays.toString(), "ShortenedWorkingDay upload edildi");
        return mapPost(redirectAttributes, "/hr/shortened-working-day");
    }

    @PostMapping(value = "/vacation")
    public String postVacation(@ModelAttribute(Constants.FORM) @Validated Vacation vacation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        binding = check(binding, vacation.getDateRange(), vacation.getEmployee());
        if(!binding.hasErrors()){
            String rangeDates[] = vacation.getDateRange().split("-");
            vacation.setStartDate(DateUtility.getUtilDate(rangeDates[0].trim()));
            vacation.setEndDate(DateUtility.getUtilDate(rangeDates[1].trim()));
            vacation.setEmployee(employeeRepository.getEmployeeById(vacation.getEmployee().getId()));
            if(vacation.getStartDate().getTime() - vacation.getEmployee().getContractStartDate().getTime() < 5253120000l  && (vacation.getIdentifier().getAttr1().equalsIgnoreCase("M"))){
                FieldError fieldError = new FieldError("dateRange", "dateRange", "İşə başlama tarixindən 2 ayı keçməmişdir!");
                binding.addError(fieldError);
            }

            if(!binding.hasErrors()){
                if(vacation.getStartDate()!=null && vacation.getEndDate()!=null){
                    if(vacation.getId()!=null){
                        vacationDetailRepository.deleteInBatch(vacationDetailRepository.getVacationDetailsByVacation_Id(vacation.getId()));
                        //log("shortened_working_day", "create/edit", shortenedWorkingDay.getId(), shortenedWorkingDay.toString());
                    }
                    Calendar start = Calendar.getInstance();
                    start.setTime(vacation.getStartDate());
                    Calendar end = Calendar.getInstance();
                    end.setTime(vacation.getEndDate());
                    end.add(Calendar.DATE, 1);

                    List<VacationDetail> vacationDetails = new ArrayList<>();
                    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                        vacationDetails.add(new VacationDetail(vacation.getIdentifier().getAttr1(), date, vacation, vacation.getEmployee()));
                    }
                    vacation.setVacationDetails(vacationDetails);
                    vacationRepository.save(vacation);
                    log(vacation, "vacation", "create/edit", vacation.getId(), vacation.toString());

                    Advance advance = calculateVacationPrice(vacation); //mezuniyyet haqqi hesablanir
                    advanceRepository.save(advance);
                    log(advance, "advance", "create/edit", advance.getId(), advance.toString());
                }
            }
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        return mapPost(vacation, binding, redirectAttributes);
    }

    @PostMapping(value = "/vacation/filter")
    public String postVacationFilter(@ModelAttribute(Constants.FILTER) @Validated Vacation vacation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(vacation, binding, redirectAttributes, "/hr/vacation");
    }

    @PostMapping(value = "/business-trip")
    public String postBusinessTrip(@ModelAttribute(Constants.FORM) @Validated BusinessTrip businessTrip, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        binding = check(binding, businessTrip.getDateRange(), businessTrip.getEmployee());
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            String rangeDates[] = businessTrip.getDateRange().split("-");
            businessTrip.setStartDate(DateUtility.getUtilDate(rangeDates[0].trim()));
            businessTrip.setEndDate(DateUtility.getUtilDate(rangeDates[1].trim()));
            businessTrip.setEmployee(employeeRepository.getEmployeeById(businessTrip.getEmployee().getId()));
            if(businessTrip.getStartDate()!=null && businessTrip.getEndDate()!=null){
                if(businessTrip.getId()!=null){
                    businessTripDetailRepository.deleteInBatch(businessTripDetailRepository.getBusinessTripDetailsByBusinessTrip_Id(businessTrip.getId()));
                   // log("advance", "create/edit", advance.getId(), advance.toString());
                }
                Calendar start = Calendar.getInstance();
                start.setTime(businessTrip.getStartDate());
                Calendar end = Calendar.getInstance();
                end.setTime(businessTrip.getEndDate());
                end.add(Calendar.DATE, 1);

                List<BusinessTripDetail> businessTripDetails = new ArrayList<>();
                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    businessTripDetails.add(new BusinessTripDetail(businessTrip.getIdentifier().getAttr1(), date, businessTrip, businessTrip.getEmployee()));
                }
                businessTrip.setBusinessTripDetails(businessTripDetails);
                businessTripRepository.save(businessTrip);
                log(businessTrip, "business_trip", "create/edit", businessTrip.getId(), businessTrip.toString());
            }
        }
        return mapPost(businessTrip, binding, redirectAttributes);
    }

    @PostMapping(value = "/business-trip/filter")
    public String postBusinessTripFilter(@ModelAttribute(Constants.FILTER) @Validated BusinessTrip businessTrip, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(businessTrip, binding, redirectAttributes, "/hr/business-trip");
    }

    @PostMapping(value = "/illness")
    public String postIllness(@ModelAttribute(Constants.FORM) @Validated Illness illness, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        binding = check(binding, illness.getDateRange(), illness.getEmployee());
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            String rangeDates[] = illness.getDateRange().split("-");
            illness.setStartDate(DateUtility.getUtilDate(rangeDates[0].trim()));
            illness.setEndDate(DateUtility.getUtilDate(rangeDates[1].trim()));
            illness.setEmployee(employeeRepository.getEmployeeById(illness.getEmployee().getId()));
            if(illness.getStartDate()!=null && illness.getEndDate()!=null){
                if(illness.getId()!=null){
                    illnessDetailRepository.deleteInBatch(illnessDetailRepository.getIllnessDetailsByIllness_Id(illness.getId()));
                   // log("business_trip", "create/edit", businessTrip.getId(), businessTrip.toString());
                }
                Calendar start = Calendar.getInstance();
                start.setTime(illness.getStartDate());
                Calendar end = Calendar.getInstance();
                end.setTime(illness.getEndDate());
                end.add(Calendar.DATE, 1);

                List<IllnessDetail> illnessDetails = new ArrayList<>();
                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    illnessDetails.add(new IllnessDetail(illness.getIdentifier().getAttr1(), date, illness, illness.getEmployee()));
                }
                illness.setIllnessDetails(illnessDetails);
                illnessRepository.save(illness);
                log(illness, "illness", "create/edit", illness.getId(), illness.toString());
            }
        }
        return mapPost(illness, binding, redirectAttributes);
    }

    @PostMapping(value = "/illness/filter")
    public String postIllnessFilter(@ModelAttribute(Constants.FILTER) @Validated Illness illness, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(illness, binding, redirectAttributes, "/hr/illness");
    }

    private double lastMonthSalary(SalaryEmployee salaryEmployee){
        for(SalaryEmployeeDetail sed: salaryEmployee.getSalaryEmployeeDetails()){
            if(sed.getKey().equalsIgnoreCase("{total_amount_payable_official}")){
                return Double.parseDouble(sed.getValue());
            }
        }
        return 0;
    }

    private BindingResult check(BindingResult binding, String range, Employee employee){
        String rangeDates[] = range.split("-");
        Date startDate = DateUtility.getUtilDate(rangeDates[0].trim());
        Date endDate = DateUtility.getUtilDate(rangeDates[1].trim());
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.add(Calendar.DATE, 1);
        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
            List<VacationDetail> vacationDetails = vacationDetailRepository.getVacationDetailsByEmployeeAndVacationDateAndVacation_Active(employee, date, true);
            if(vacationDetails!=null && vacationDetails.size()>0){
                FieldError fieldError = new FieldError("dateRange", "dateRange", DateUtility.getFormattedDate(date) + " - tarixdə məzuniyyət mövcuddur");
                binding.addError(fieldError);
            }

            List<BusinessTripDetail> businessTripDetails = businessTripDetailRepository.getBusinessTripDetailsByEmployeeAndBusinessTripDateAndBusinessTrip_Active(employee, date, true);
            if(businessTripDetails!=null && businessTripDetails.size()>0){
                FieldError fieldError = new FieldError("dateRange", "dateRange", DateUtility.getFormattedDate(date) + " - tarixdə ezamiyyət mövcuddur");
                binding.addError(fieldError);
            }

            List<IllnessDetail> illnessDetails = illnessDetailRepository.getIllnessDetailsByEmployeeAndIllnessDateAndIllness_Active(employee, date, true);
            if(illnessDetails!=null && illnessDetails.size()>0){
                FieldError fieldError = new FieldError("dateRange", "dateRange", DateUtility.getFormattedDate(date) + " - tarixdə xəstəlik mövcuddur");
                binding.addError(fieldError);
            }
        }
        return binding;
    }

    @ResponseBody
    @GetMapping(value = "/api/employee/{dataId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployee(@PathVariable("dataId") Integer dataId){
        return employeeRepository.getEmployeeById(dataId);
    }

    @ResponseBody
    @GetMapping(value = "/api/organization/{dataId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Organization getOrganization(@PathVariable("dataId") Integer dataId){
        return organizationRepository.getOrganizationById(dataId);
    }


}
