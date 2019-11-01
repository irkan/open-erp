package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.http.MediaType;
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
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SALES)){
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.SALE_PRICES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sale-price"));
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/sale-group")
    public String postInventory(@ModelAttribute(Constants.FORM) @Validated SaleGroup saleGroup, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            saleGroupRepository.save(saleGroup);
        }
        return mapPost(saleGroup, binding, redirectAttributes);
    }

    @ResponseBody
    @GetMapping(value = "/payment/schedule/{lastPrice}/{down}/{schedule}/{period}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Schedule> getPaymentSchedule(Model model, @PathVariable("lastPrice") double lastPrice, @PathVariable("down") double down, @PathVariable("schedule") int scheduleId, @PathVariable("period") int periodId){
        try {
            Dictionary schedule = dictionaryRepository.getDictionaryById(scheduleId);
            Dictionary period = dictionaryRepository.getDictionaryById(periodId);
            lastPrice = lastPrice - down;
            int scheduleCount = Integer.parseInt(schedule.getAttr1());
            double schedulePrice = Math.ceil(lastPrice/scheduleCount);
            Date today = new Date();
            Date startDate = DateUtility.generate(Integer.parseInt(period.getAttr1()), today.getMonth(), today.getYear()+1900);
            List<Schedule> schedules = new ArrayList<>();
            Date scheduleDate = DateUtils.addMonths(startDate, 1);
            for(int i=0; i<scheduleCount; i++){
                scheduleDate = DateUtils.addMonths(scheduleDate, 1);
                Schedule schedule1 = new Schedule(null, schedulePrice, scheduleDate);
                schedules.add(schedule1);
            }
            return schedules;
        } catch (Exception e){
            log.error(e);
        }
        return null;
    }
}
