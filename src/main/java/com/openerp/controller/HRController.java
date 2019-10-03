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

import java.io.File;
import java.util.List;
import java.util.Optional;

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
            NonWorkingDay nwd = nonWorkingDayRepository.getNonWorkingDayByNonWorkingDate(nonWorkingDay.getNonWorkingDate());
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
}
