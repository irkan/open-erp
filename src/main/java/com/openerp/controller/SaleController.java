package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

        if (page.equalsIgnoreCase(Constants.ROUTE.SALES)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.SALE_PRICES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sale-price"));
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            model.addAttribute(Constants.GUARANTEES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("guarantee"));

            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Sales(!canViewAll()?getSessionOrganization():null));
            }
            Page<Sales> sales = salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, 100, Sort.by("id").descending()));
            for(Sales sales1: sales){
                sales1.setSalesInventories(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales(sales1));
            }
            model.addAttribute(Constants.LIST, sales);
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SERVICE)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales(getSessionOrganization(), true));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Sales(!canViewAll()?getSessionOrganization():null, true));
            }
            model.addAttribute(Constants.LIST, salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, 100, Sort.by("id").descending())));
        } else if (page.equalsIgnoreCase(Constants.ROUTE.INVOICE)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            Util.convertedEmployeesByPosition(employees, positions);
            model.addAttribute(Constants.PAYMENT_CHANNEL, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-channel"));
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Invoice(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Invoice(!canViewAll()?getSessionOrganization():null));
            }
            model.addAttribute(Constants.LIST, invoiceService.findAll((Invoice) model.asMap().get(Constants.FILTER), PageRequest.of(0, 100, Sort.by("id").descending())));
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DEMONSTRATION)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            Util.convertedEmployeesByPosition(employees, positions);
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Demonstration(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Demonstration(!canViewAll()?getSessionOrganization():null));
            }
            model.addAttribute(Constants.LIST, demonstrationService.findAll((Demonstration) model.asMap().get(Constants.FILTER), PageRequest.of(0, 100, Sort.by("id").descending())));
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DEMONSTRATION_DETAIL)){

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
            if(sales.getId()==null){
                List<SalesInventory> salesInventories = new ArrayList<>();
                for(SalesInventory salesInventory: sales.getSalesInventories()){
                    salesInventories.add(new SalesInventory(salesInventory.getInventory(), sales));
                }
                sales.setSalesInventories(salesInventories);
            }
            if(sales.getPayment().getCash()){
                sales.getPayment().setPeriod(null);
                sales.getPayment().setSchedule(null);
            }
            sales.setGuaranteeExpire(Util.guarantee(sales.getSaleDate()==null?new Date():sales.getSaleDate(), sales.getGuarantee()));
            salesRepository.save(sales);
            log("sale_sales", "create/edit", sales.getId(), sales.toString());

            for(SalesInventory salesInventory: sales.getSalesInventories()){
                List<Action> oldActions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndInventoryAndEmployeeAndAction_Attr1AndAmountGreaterThanOrderById(true, salesInventory.getInventory(), getSessionUser().getEmployee(), "consolidate", 0);
                if(oldActions.size()>0){
                    Action action = new Action();
                    action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("sell", "action"));
                    action.setAmount(1);
                    action.setInventory(salesInventory.getInventory());
                    action.setOrganization(sales.getOrganization());
                    action.setEmployee(getSessionUser().getEmployee());
                    action.setSupplier(oldActions.get(0).getSupplier());
                    actionRepository.save(action);
                    log("warehouse_action", "create/edit", action.getId(), action.toString());

                    Action oldAction = oldActions.get(0);
                    oldAction.setAmount(oldAction.getAmount()-1);
                    actionRepository.save(oldAction);
                    log("warehouse_action", "create/edit", oldAction.getId(), oldAction.toString());
                }
            }

            if(sales.getPayment()!=null && sales.getPayment().getSchedules()!=null && sales.getPayment().getSchedules().size()>0){
                List<Schedule> schedules = sales.getPayment().getSchedules();
                for(Schedule schedule: schedules){
                    schedule.setPayment(sales.getPayment());
                }
                scheduleRepository.saveAll(schedules);
                //Id de error olur
                //log("warehouse_action", "create/edit", schedules.getId(), schedules.toString());
            }

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
                invoice.setOrganization(sales.getOrganization());
                invoice.setDescription("Satışdan əldə edilən ödəniş " + invoicePrice + " AZN");
                invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                invoiceRepository.save(invoice);
                log("sale_invoice", "create/edit", invoice.getId(), invoice.toString());
                invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                invoiceRepository.save(invoice);
                log("sale_invoice", "create/edit", invoice.getId(), invoice.toString());
            }
        }
        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes);
    }

    @PostMapping(value = "/sales/filter")
    public String postSalesFilter(@ModelAttribute(Constants.FILTER) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(sales, binding, redirectAttributes, "/sale/sales");
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
            log("sale_invoice", "create/edit", invc.getId(), invc.toString());
            invc.setChannelReferenceCode(String.valueOf(invc.getId()));
            invoiceRepository.save(invc);
            log("sale_invoice", "create/edit", invc.getId(), invc.toString());
        }
        return mapPost(invoice, binding, redirectAttributes);
    }

    @PostMapping(value = "/invoice/filter")
    public String postInvoiceFilter(@ModelAttribute(Constants.FILTER) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(invoice, binding, redirectAttributes, "/sale/invoice");
    }

    @PostMapping(value = "/demonstration")
    public String postDemonstration(@ModelAttribute(Constants.FORM) @Validated Demonstration demonstration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            demonstrationRepository.save(demonstration);
            List<EmployeePayrollDetail> employeeDetails = demonstration.getEmployee().getEmployeePayrollDetails();
            String value = Util.findEmployeeDetail(employeeDetails, "{demonstration}");
            double price = demonstration.getAmount()*Double.parseDouble(value);
            Advance advance = new Advance(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-demonstration-advance", "advance"), demonstration.getEmployee(), demonstration.getEmployee().getOrganization(), "", "", demonstration.getCreatedDate(), price);
            advanceRepository.save(advance);
        }
        return mapPost(demonstration, binding, redirectAttributes);
    }

    @PostMapping(value = "/demonstration/filter")
    public String postDemonstrationFilter(@ModelAttribute(Constants.FILTER) @Validated Demonstration demonstration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(demonstration, binding, redirectAttributes, "/sale/demonstration");
    }

    @PostMapping(value = "/invoice/consolidate")
    public String postInvoiceConsolidate(@ModelAttribute(Constants.FORM) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Invoice invc = invoiceRepository.getInvoiceById(invoice.getId());
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            invc.setCollector(invoice.getCollector());
            invoiceRepository.save(invc);
            log("sale_invoice", "create/edit", invc.getId(), invc.toString());
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
            log("sale_invoice", "create/edit", invc.getId(), invc.toString());

            Transaction transaction = new Transaction();
            transaction.setApprove(false);
            transaction.setAmount(1);
            transaction.setDebt(true);
            transaction.setInventory(invc.getSales().getSalesInventories().get(0).getInventory());
            transaction.setOrganization(invc.getOrganization());
            transaction.setPrice(invc.getPrice());
            transaction.setCurrency("AZN");
            transaction.setRate(getRate(transaction.getCurrency()));
            double sumPrice = transaction.getAmount() * transaction.getPrice() * transaction.getRate();
            transaction.setSumPrice(sumPrice);
            transaction.setAction(invc.getSales().getSalesInventories().get(0).getInventory().getActions().get(0).getAction()); //burda duzelis edilmelidir
            transaction.setDescription("Satış, Kod: "+invc.getSales().getId() + " -> "
                    + invc.getSales().getCustomer().getPerson().getFullName() + " -> "
                    + " barkod: " + invc.getSales().getSalesInventories().get(0).getInventory().getName()
                    + " " + invc.getSales().getSalesInventories().get(0).getInventory().getBarcode()
            );
            transactionRepository.save(transaction);
            log("accounting_transaction", "create/edit", transaction.getId(), transaction.toString());

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
               //Id de error verir
                // log("accounting_transaction", "create/edit", advances.getId(), advances.toString());
            }
        }
        return mapPost(invc, binding, redirectAttributes, "/sale/invoice/");
    }
}
