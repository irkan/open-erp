package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
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
import java.util.*;
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
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(getSessionUser().getEmployee().getOrganization().getId());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            Util.convertedEmployees(employees, positions);
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployees(employees, positions));
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.SALE_PRICES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sale-price"));
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            model.addAttribute(Constants.GUARANTEES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("guarantee"));
            model.addAttribute(Constants.LIST, salesRepository.getSalesByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales());
            }
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CALCULATOR)){
            model.addAttribute(Constants.SALE_PRICES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sale-price"));
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/sales")
    public String postSales(@ModelAttribute(Constants.FORM) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            Action oldAction =  actionRepository.getActionById(sales.getAction().getId());
            oldAction.setAmount(oldAction.getAmount()-1);

            if(sales.getId()==null){
                Action action = new Action();
                action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("sell", "action"));
                action.setAmount(1);
                action.setInventory(oldAction.getInventory());
                action.setSupplier(oldAction.getSupplier());
                action.setWarehouse(oldAction.getWarehouse());
                action.setEmployee(oldAction.getEmployee());
                sales.setAction(action);
            }
            if(sales.getPayment().getCash()){
                sales.getPayment().setPeriod(null);
                sales.getPayment().setSchedule(null);
            }
            sales.setGuaranteeExpire(Util.guarantee(sales.getGuarantee()));
            salesRepository.save(sales);

            if(sales.getPayment()!=null && sales.getPayment().getSchedules()!=null && sales.getPayment().getSchedules().size()>0){
                List<Schedule> schedules = sales.getPayment().getSchedules();
                for(Schedule schedule: schedules){
                    schedule.setPayment(sales.getPayment());
                }
                scheduleRepository.saveAll(schedules);
            }

            actionRepository.save(oldAction);
        }
        return mapPost(sales, binding, redirectAttributes);
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
