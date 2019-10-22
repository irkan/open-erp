package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
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

import java.io.File;
import java.time.LocalDate;
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
            model.addAttribute(Constants.EMPLOYEE_ADDITIONAL_FIELDS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("employee-additional-field"));
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
        if (!binding.hasErrors()) {
            for(EmployeeDetail ed: employee.getEmployeeDetails()){
                ed.setEmployee(employee);
            }
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
            employeeRepository.save(employee);
        }
        return mapPost(employee, binding, redirectAttributes);
    }

    @PostMapping(value = "/non-working-day")
    public String postNonWorkingDay(@ModelAttribute(Constants.FORM) @Validated NonWorkingDay nonWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            nonWorkingDayRepository.save(nonWorkingDay);
        }
        return mapPost(nonWorkingDay, binding, redirectAttributes);
    }

    @PostMapping(value = "/non-working-day/upload", consumes = {"multipart/form-data"})
    public String postNonWorkingDayUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        List<NonWorkingDay> nonWorkingDays = ReadWriteExcelFile.readXLSXFileNonWorkingDays(file.getInputStream());
        for(NonWorkingDay nonWorkingDay: nonWorkingDays){
            NonWorkingDay nwd = nonWorkingDayRepository.getNonWorkingDayByNonWorkingDateAndActiveTrue(nonWorkingDay.getNonWorkingDate());
            if(nwd!=null && !nwd.getActive()){
                nwd.setActive(true);
                nonWorkingDayRepository.save(nwd);
            } else {
                try {
                    nonWorkingDayRepository.save(nonWorkingDay);
                } catch (Exception e){
                    log.error(e);
                }
            }
        }
        return mapPost(redirectAttributes, "/hr/non-working-day");
    }

    @PostMapping(value = "/shortened-working-day")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated ShortenedWorkingDay shortenedWorkingDay, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            shortenedWorkingDayRepository.save(shortenedWorkingDay);
        }
        return mapPost(shortenedWorkingDay, binding, redirectAttributes);
    }

    @PostMapping(value = "/shortened-working-day/upload", consumes = {"multipart/form-data"})
    public String postShortenedWorkingDayUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        List<ShortenedWorkingDay> shortenedWorkingDays = ReadWriteExcelFile.readXLSXFileShortenedWorkingDays(file.getInputStream());
        for(ShortenedWorkingDay shortenedWorkingDay: shortenedWorkingDays){
            ShortenedWorkingDay swd = shortenedWorkingDayRepository.getShortenedWorkingDayByWorkingDate(shortenedWorkingDay.getWorkingDate());
            if(swd!=null && !swd.getActive()){
                swd.setActive(true);
                shortenedWorkingDayRepository.save(swd);
            } else {
                try {
                    shortenedWorkingDayRepository.save(shortenedWorkingDay);
                } catch (Exception e){
                    log.error(e);
                }
            }
        }
        return mapPost(redirectAttributes, "/hr/shortened-working-day");
    }

    @PostMapping(value = "/vacation")
    public String postVacation(@ModelAttribute(Constants.FORM) @Validated Vacation vacation, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            String range = vacation.getDateRange();
            if(range.length()>15){
                String rangeDates[] = range.split("-");
                vacation.setStartDate(DateUtility.getUtilDate(rangeDates[0].trim()));
                vacation.setEndDate(DateUtility.getUtilDate(rangeDates[1].trim()));
                vacationRepository.save(vacation);

                if(vacation.getId()!=null && vacation.getStartDate()!=null && vacation.getEndDate()!=null){
                    vacationDetailRepository.deleteInBatch(vacationDetailRepository.getVacationDetailsByVacation_Id(vacation.getId()));
                    Calendar start = Calendar.getInstance();
                    start.setTime(vacation.getStartDate());
                    Calendar end = Calendar.getInstance();
                    end.setTime(vacation.getEndDate());
                    end.add(Calendar.DATE, 1);

                    for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                        vacationDetailRepository.save(new VacationDetail(vacation.getIdentifier().getAttr1(), date, vacation, vacation.getEmployee()));
                    }

                    String description = vacation.getIdentifier().getName() + " " + DateUtility.getFormattedDate(vacation.getStartDate()) + " - " + DateUtility.getFormattedDate(vacation.getEndDate());
                    Advance advance = new Advance(vacation.getIdentifier(), vacation.getEmployee(), description, vacation.getStartDate(), 473.50, false);
                    advanceRepository.save(advance);
                }
            }
        }
        return mapPost(vacation, binding, redirectAttributes);
    }

    @PostMapping(value = "/business-trip")
    public String postBusinessTrip(@ModelAttribute(Constants.FORM) @Validated BusinessTrip businessTrip, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
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
