package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.*;
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

        if (page.equalsIgnoreCase(Constants.ROUTE.SALES)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.SALE_PRICES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sale-price"));
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            model.addAttribute(Constants.GUARANTEES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("guarantee"));
            model.addAttribute(Constants.LIST, salesRepository.getSalesByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.INVOICE)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            Util.convertedEmployeesByPosition(employees, positions);
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            List<Invoice> invoices;
            if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                invoices = invoiceRepository.getInvoicesByActiveTrueOrderByInvoiceDateDesc();
            } else {
                invoices = invoiceRepository.getInvoicesByActiveTrueAndOrganizationOrderByInvoiceDateDesc(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()));
            }
            model.addAttribute(Constants.LIST, invoices);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Invoice());
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
                action.setOrganization(oldAction.getOrganization());
                action.setEmployee(oldAction.getEmployee());
                sales.setAction(action);
                sales.setOrganization(action.getOrganization());
            }
            if(sales.getPayment().getCash()){
                sales.getPayment().setPeriod(null);
                sales.getPayment().setSchedule(null);
            }
            sales.setGuaranteeExpire(Util.guarantee(sales.getSaleDate()==null?new Date():sales.getSaleDate(), sales.getGuarantee()));
            salesRepository.save(sales);

            if(sales.getPayment()!=null && sales.getPayment().getSchedules()!=null && sales.getPayment().getSchedules().size()>0){
                List<Schedule> schedules = sales.getPayment().getSchedules();
                for(Schedule schedule: schedules){
                    schedule.setPayment(sales.getPayment());
                }
                scheduleRepository.saveAll(schedules);
            }

            actionRepository.save(oldAction);

            double invoicePrice = 0d;
            if(sales.getPayment().getCash()){
                invoicePrice = sales.getPayment().getLastPrice();
            } else {
                invoicePrice = sales.getPayment().getDown();
            }

            if(invoicePrice>0){
                Invoice invoice = new Invoice();
                invoice.setSales(sales);
                invoice.setApprove(false);
                invoice.setPrice(invoicePrice);
                invoice.setOrganization(oldAction.getOrganization());
                invoice.setDescription("Satışdan əldə edilən ödəniş " + invoicePrice + " AZN");
                invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                invoiceRepository.save(invoice);
                invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                invoiceRepository.save(invoice);

            }
        }
        return mapPost(sales, binding, redirectAttributes);
    }

    @ResponseBody
    @GetMapping(value = "/payment/schedule/{lastPrice}/{down}/{schedule}/{period}/{saleDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Schedule> getPaymentSchedule(Model model, @PathVariable("lastPrice") double lastPrice, @PathVariable("down") double down, @PathVariable("schedule") int scheduleId, @PathVariable("period") int periodId, @PathVariable(name = "saleDate", value = "") String saleDate){
        try {
            Dictionary schedule = dictionaryRepository.getDictionaryById(scheduleId);
            Dictionary period = dictionaryRepository.getDictionaryById(periodId);
            lastPrice = lastPrice - down;
            int scheduleCount = Integer.parseInt(schedule.getAttr1());
            double schedulePrice = Math.ceil(lastPrice/scheduleCount);
            Date saleDt = saleDate.trim().length()>0?DateUtility.getUtilDate(saleDate):new Date();
            Date startDate = DateUtility.generate(Integer.parseInt(period.getAttr1()), saleDt.getMonth(), saleDt.getYear()+1900);
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

    @PostMapping(value = "/invoice")
    public String postInvoice(@ModelAttribute(Constants.FORM) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            Invoice invc;
            if(invoice.getId()==null){
                invoice.setDescription("Satışdan əldə edilən ödəniş " + invoice.getPrice() + " AZN");
                invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                invc = invoice;
            } else {
                invc = invoiceRepository.getInvoiceById(invoice.getId());
                invc.setSales(invoice.getSales());
                invc.setPrice(invoice.getPrice());
                invc.setInvoiceDate(invoice.getInvoiceDate());
                invc.setDescription(invoice.getDescription());
            }
            invoiceRepository.save(invc);
            invc.setChannelReferenceCode(String.valueOf(invc.getId()));
            invoiceRepository.save(invc);
        }
        return mapPost(invoice, binding, redirectAttributes);
    }

    @PostMapping(value = "/invoice/consolidate")
    public String postInvoiceConsolidate(@ModelAttribute(Constants.FORM) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Invoice invc = invoiceRepository.getInvoiceById(invoice.getId());
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            invc.setCollector(invoice.getCollector());
            invoiceRepository.save(invc);
        }
        return mapPost(invc, binding, redirectAttributes, "/sale/invoice/");
    }

    @ResponseBody
    @GetMapping(value = "/sales/check/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sales getSalesCheck(@PathVariable("saleId") String saleId){
        try {
             return salesRepository.getSalesByIdAndActiveTrue(Integer.parseInt(saleId));
        } catch (Exception e){
            log.error(e);
        }
        return null;
    }

    @PostMapping(value = "/invoice/approve")
    public String postInvoiceApprove(@ModelAttribute(Constants.FORM) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Invoice invc = invoiceRepository.getInvoiceById(invoice.getId());
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            invc.setApprove(true);
            invc.setApproveDate(new Date());
            invc.setDescription(invoice.getDescription());
            invc.setAdvance(invoice.getAdvance());
            invoiceRepository.save(invc);

            Transaction transaction = new Transaction();
            transaction.setApprove(false);
            transaction.setAmount(1);
            transaction.setDebt(true);
            transaction.setInventory(invc.getSales().getAction().getInventory());
            transaction.setOrganization(invc.getOrganization());
            transaction.setPrice(invc.getPrice());
            transaction.setCurrency("AZN");
            transaction.setRate(getRate(transaction.getCurrency()));
            double sumPrice = transaction.getAmount() * transaction.getPrice() * transaction.getRate();
            transaction.setSumPrice(sumPrice);
            transaction.setAction(invc.getSales().getAction().getAction());
            transaction.setDescription("Satış, Kod: "+invc.getSales().getId() + " -> "
                    + invc.getSales().getCustomer().getPerson().getFullName() + " -> "
                    + " barkod: " + invc.getSales().getAction().getInventory().getName()
                    + " " + invc.getSales().getAction().getInventory().getBarcode()
            );
            transactionRepository.save(transaction);


            if(invc.getSales()!=null && invc.getApprove() && invc.getAdvance()){
                List<Advance> advances = new ArrayList<>();
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                Dictionary advance = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-sale-advance", "advance");
                Sales sales = invc.getSales();
                String percent = "*0.01";
                if(sales.getCanavasser()!=null){
                    EmployeeSaleDetail canvasserSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getCanavasser(), "{canvasser}");
                    String calculated_bonus = canvasserSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advances.add(new Advance(advance,
                            sales.getCanavasser(),
                            Util.getUserBranch(sales.getCanavasser().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. (Canvasser)",
                            calculated_bonus,
                            sales.getSaleDate(),
                            Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    ));
                }

                if(sales.getDealer()!=null){
                    EmployeeSaleDetail dealerSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getDealer(), "{dealer}");
                    String calculated_bonus = dealerSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advances.add(new Advance(advance,
                            sales.getDealer(),
                            Util.getUserBranch(sales.getDealer().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. (Diller)",
                            calculated_bonus,
                            sales.getSaleDate(),
                            Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    ));
                }

                if(sales.getVanLeader()!=null){
                    EmployeeSaleDetail vanLeaderSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getVanLeader(), "{van_leader}");
                    String calculated_bonus = vanLeaderSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advances.add(new Advance(advance,
                            sales.getVanLeader(),
                            Util.getUserBranch(sales.getVanLeader().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. (Ven lider)",
                            calculated_bonus,
                            sales.getSaleDate(),
                            Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    ));
                }

                if(sales.getConsole()!=null){
                    EmployeeSaleDetail consulSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getConsole(), "{consul}");
                    String calculated_bonus = consulSaleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("%"), percent);
                    advances.add(new Advance(advance,
                            sales.getConsole(),
                            Util.getUserBranch(sales.getConsole().getOrganization()),
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. (Konsul)",
                            calculated_bonus,
                            sales.getSaleDate(),
                            Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    ));
                }
                advanceRepository.saveAll(advances);
            }
        }
        return mapPost(invc, binding, redirectAttributes, "/sale/invoice/");
    }
}
