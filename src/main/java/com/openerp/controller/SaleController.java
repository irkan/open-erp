package com.openerp.controller;

import com.openerp.domain.SalesSchedules;
import com.openerp.domain.Schedule;
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
                model.addAttribute(Constants.FILTER, new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null, new Payment((Double) null)));
            }
            Page<Sales> sales = salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            for(Sales sales1: sales){
                sales1.setSalesInventories(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(sales1.getId()));
            }
            model.addAttribute(Constants.LIST, sales);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(sales, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SCHEDULE)){
            List<Schedule> schedules = new ArrayList<>();
            Sales sale = null;
            if(!data.equals(Optional.empty())){
                sale = salesRepository.getSalesById(Integer.parseInt(data.get()));
                if(sale!=null && sale.getPayment()!=null && !sale.getPayment().getCash()){
                    double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                    schedules = getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule().getId(), sale.getPayment().getPeriod().getId(), sale.getPayment().getLastPrice(), sale.getPayment().getDown());
                    double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                    schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                    sale.getPayment().setLatency(Util.calculateLatency(schedules, sumOfInvoices, sale));
                    sale.getPayment().setSumOfInvoice(sumOfInvoices);
                    sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                }
            }
            model.addAttribute(Constants.LIST, new SalesSchedules(schedules, sale));
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SERVICE)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales(getSessionOrganization(), true));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null, true, null));
            }
            Page<Sales> sales = salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, sales);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(sales, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.INVOICE)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            Util.convertedEmployeesByPosition(employees, positions);
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            model.addAttribute(Constants.PAYMENT_CHANNEL, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-channel"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Invoice(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Invoice(!canViewAll()?getSessionOrganization():null, null, null, null));
            }
            Page<Invoice> invoices = invoiceService.findAll((Invoice) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, invoices);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(invoices, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.DEMONSTRATION)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            Util.convertedEmployeesByPosition(employees, positions);
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Demonstration(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Demonstration(!canViewAll()?getSessionOrganization():null, null, null));
            }
            Page<Demonstration> demonstrations = demonstrationService.findAll((Demonstration) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, demonstrations);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(demonstrations, redirectAttributes, page);
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
            salesInventoryRepository.deleteInBatch(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(sales.getId()));
            List<SalesInventory> salesInventories = new ArrayList<>();
            for(SalesInventory salesInventory: sales.getSalesInventories()){
                salesInventories.add(new SalesInventory(salesInventory.getInventory(), sales));
            }
            sales.setSalesInventories(salesInventories);

            if(sales.getPayment().getCash()){
                sales.getPayment().setPeriod(null);
                sales.getPayment().setSchedule(null);
                sales.getPayment().setSchedulePrice(null);
                sales.getPayment().setDown(sales.getPayment().getLastPrice());
            } else {
                sales.getPayment().setSchedulePrice(schedulePrice(sales.getPayment().getSchedule().getId(), sales.getPayment().getLastPrice(), sales.getPayment().getDown()));
            }
            sales.setGuarantee(sales.getGuarantee()!=null?sales.getGuarantee():6);
            sales.setGuaranteeExpire(Util.guarantee(sales.getSaleDate()==null?new Date():sales.getSaleDate(), sales.getGuarantee()));
            salesRepository.save(sales);
            log("sale_sales", "create/edit", sales.getId(), sales.toString());
        }
        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes);
    }

    @PostMapping(value = "/sales/approve")
    public String postSalesApprove(@ModelAttribute(Constants.FORM) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        sales = salesRepository.getSalesById(sales.getId());
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
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
                invoice.setCreditable(false);
                invoice.setAdvance(true);
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
            sales.setApprove(true);
            sales.setApproveDate(new Date());
            salesRepository.save(sales);
            log("sale_sales", "approve", sales.getId(), sales.toString());
        }
        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes, "/sale/sales");
    }

    @PostMapping(value = "/sales/filter")
    public String postSalesFilter(@ModelAttribute(Constants.FILTER) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(sales, binding, redirectAttributes, "/sale/sales");
    }

    @ResponseBody
    @GetMapping(value = "/payment/schedule/{lastPrice}/{down}/{schedule}/{period}/{saleDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Schedule> getPaymentSchedulePrice(Model model, @PathVariable("lastPrice") double lastPrice, @PathVariable("down") double down, @PathVariable("schedule") int scheduleId, @PathVariable("period") int periodId, @PathVariable(name = "saleDate", value = "") String saleDate){
        try {
            return getSchedulePayment(saleDate, scheduleId, periodId, lastPrice, down);
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
                invoice.setApprove(false);
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
            Advance advance = new Advance(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-demonstration-advance", "advance"), demonstration.getEmployee(), demonstration.getOrganization(), demonstration.getId() + " nömrəli nümayişdən əldə edilən bonus", "", demonstration.getDemonstrateDate(), price);
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
            //calculateSchedule(invoice.getSales().getId(), invoice.getPrice());
            invc.setApprove(true);
            invc.setApproveDate(new Date());
            invc.setDescription(invoice.getDescription());
            invc.setAdvance(invoice.getAdvance());
            invoiceRepository.save(invc);
            log("sale_invoice", "create/edit", invc.getId(), invc.toString());

            Transaction transaction = new Transaction();
            transaction.setApprove(false);
            transaction.setDebt(invc.getPrice()>0?true:false);
            transaction.setInventory(invc.getSales().getSalesInventories().get(0).getInventory());
            transaction.setOrganization(invc.getOrganization());
            transaction.setPrice(Math.abs(invc.getPrice()));
            transaction.setCurrency("AZN");
            transaction.setRate(getRate(transaction.getCurrency()));
            double sumPrice = Util.amountChecker(transaction.getAmount()) * transaction.getPrice() * transaction.getRate();
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
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. "+canvasserSaleDetail.getEmployee().getPerson().getFullName()+" (Canvasser)",
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
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus."+dealerSaleDetail.getEmployee().getPerson().getFullName()+" (Diller)",
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
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus."+vanLeaderSaleDetail.getEmployee().getPerson().getFullName()+" (Ven lider)",
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
                            sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus."+consulSaleDetail.getEmployee().getPerson().getFullName()+" (Konsul)",
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

    @PostMapping(value = "/invoice/credit")
    public String postInvoiceCredit(@ModelAttribute(Constants.FORM) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Invoice invc = invoiceRepository.getInvoiceById(invoice.getId());
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            Invoice invoice1 = new Invoice();
            invoice1.setOrganization(invc.getOrganization());
            invoice1.setSales(invc.getSales());
            invoice1.setDescription("Kredit: " + invoice.getDescription());
            invoice1.setCreditable(false);
            invoice1.setPrice(-1*invc.getPrice());
            invoiceRepository.save(invoice1);
            log("sale_invoice", "create/edit", invoice1.getId(), invoice1.toString());

            Transaction transaction = new Transaction();
            transaction.setApprove(false);
            transaction.setInventory(invc.getSales().getSalesInventories().get(0).getInventory());
            transaction.setOrganization(invc.getOrganization());
            transaction.setPrice(invc.getPrice());
            transaction.setCurrency("AZN");
            transaction.setRate(getRate(transaction.getCurrency()));
            double sumPrice = Util.amountChecker(transaction.getAmount()) * transaction.getPrice() * transaction.getRate();
            transaction.setSumPrice(sumPrice);
            transaction.setAction(invc.getSales().getSalesInventories().get(0).getInventory().getActions().get(0).getAction()); //burda duzelis edilmelidir
            transaction.setDescription("Satış, Kod: "+invc.getSales().getId() + " -> "
                    + invc.getSales().getCustomer().getPerson().getFullName() + " -> "
                    + " barkod: " + invc.getSales().getSalesInventories().get(0).getInventory().getName()
                    + " " + invc.getSales().getSalesInventories().get(0).getInventory().getBarcode()
            );
            transactionRepository.save(transaction);
            log("accounting_transaction", "create/edit", transaction.getId(), transaction.toString());
        }
        return mapPost(invc, binding, redirectAttributes, "/sale/invoice/");
    }

    @ResponseBody
    @GetMapping(value = "/api/service/inventory/{salesId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SalesInventory> getSalesInventories(@PathVariable("salesId") Integer salesId){
        return salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(salesId);
    }


}
