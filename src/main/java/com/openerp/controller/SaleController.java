package com.openerp.controller;

import com.openerp.domain.Return;
import com.openerp.domain.SalesSchedules;
import com.openerp.domain.Schedule;
import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
            model.addAttribute(Constants.SALES_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sales-type"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.RETURN_FORM)){
                model.addAttribute(Constants.RETURN_FORM, new Return());
            }
            if(!model.containsAttribute(Constants.FILTER)){
                Sales sales = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null, new Payment((Double) null));
                sales.setApprove(null);
                model.addAttribute(Constants.FILTER, sales);
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
            model.addAttribute(Constants.SALES_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sales-type"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales(getSessionOrganization(), true));
            }
            if(!model.containsAttribute(Constants.RETURN_FORM)){
                model.addAttribute(Constants.RETURN_FORM, new Return());
            }
            if(!model.containsAttribute(Constants.FILTER)){
                Sales sales = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null, true, null);
                sales.setApprove(null);
                model.addAttribute(Constants.FILTER, sales);
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
                Sales sales = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null);
                sales.setService(null);
                model.addAttribute(Constants.FILTER, new Invoice(!canViewAll()?getSessionOrganization():null, null, null, null, sales));
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
        }  else if (page.equalsIgnoreCase(Constants.ROUTE.SERVICE_REGULATOR)){
            model.addAttribute(Constants.SERVICE_NOTIFICATIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("service-notification"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new ServiceRegulator(new Sales(getSessionOrganization())));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                Sales sales = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null, new Payment((Double) null));
                model.addAttribute(Constants.FILTER, new ServiceRegulator(sales, null));
            }
            Page<ServiceRegulator> serviceRegulators = serviceRegulatorService.findAll((ServiceRegulator) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, serviceRegulators);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(serviceRegulators, redirectAttributes, page);
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
                salesInventories.add(new SalesInventory(salesInventory.getInventory(), sales, salesInventory.getSalesType()));
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

            if(!sales.getService()){
                if(sales.getId()!=null){
                    serviceRegulatorRepository.deleteInBatch(serviceRegulatorRepository.getServiceRegulatorsBySales(sales));
                }
                List<ServiceRegulator> serviceRegulators = new ArrayList<>();
                for(Dictionary serviceNotification: dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("service-notification")){
                    serviceRegulators.add(new ServiceRegulator(sales, serviceNotification, sales.getSaleDate()));
                }
                sales.setServiceRegulators(serviceRegulators);
            }
            salesRepository.save(sales);
            log(sales, "sale_sales", "create/edit", sales.getId(), sales.toString());
        }
        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes);
    }

    @PostMapping(value = "/sales/return")
    public String postSalesReturn(@ModelAttribute(Constants.RETURN_FORM) @Validated Return returnForm, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Sales sales = salesRepository.getSalesById(returnForm.getSalesId());
        for(SalesInventory salesInventory: returnForm.getSalesInventories()){
            Action action = new Action();
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("return", "action"));
            action.setOld(salesInventory.getInventory().getOld());
            action.setInventory(salesInventory.getInventory());
            action.setAmount(1);
            action.setOrganization(sales.getOrganization());
            actionRepository.save(action);

            log(action, "warehouse_action", "return", action.getId(), action.toString());
        }

        if(returnForm.getReturnPrice()>0){
            Invoice invoice = new Invoice();
            invoice.setSales(sales);
            invoice.setApprove(false);
            invoice.setPrice(-1*returnForm.getReturnPrice());
            invoice.setOrganization(sales.getOrganization());
            invoice.setDescription("Qaytarılmaya görə müştəriyə ediləcək ödəniş " + returnForm.getReturnPrice() + " AZN");
            invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
            invoiceRepository.save(invoice);
            log(invoice, "sale_invoice", "create/edit", invoice.getId(), invoice.toString());
            invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
            invoiceRepository.save(invoice);
            log(invoice, "sale_invoice", "create/edit", invoice.getId(), invoice.toString());
        }

        ContactHistory contactHistory = new ContactHistory();
        contactHistory.setOrganization(sales.getOrganization());
        contactHistory.setDescription(returnForm.getReason());
        contactHistory.setSales(sales);
        contactHistoryRepository.save(contactHistory);
        log(contactHistory, "collect_contact_history", "create/edit", contactHistory.getId(), contactHistory.toString());

        sales.setActive(false);
        salesRepository.save(sales);

        log(sales, "sale_sales", "delete", sales.getId(), sales.toString(), "Qaytarılma icra edildi");

        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes, "/sale/sales");
    }

    @PostMapping(value = "/sales/approve")
    public String postSalesApprove(@ModelAttribute(Constants.FORM) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        sales = salesRepository.getSalesById(sales.getId());
        Employee employee = (sales.getService() && sales.getServicer()!=null)?sales.getServicer():getSessionUser().getEmployee();
        if(sales.getSalesInventories().size()>0){
            for(SalesInventory salesInventory: sales.getSalesInventories()) {
                List<Action> oldActions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndInventoryAndEmployeeAndAction_Attr1AndAmountGreaterThanOrderById(true, salesInventory.getInventory(), employee, "consolidate", 0);
                if (oldActions.size() == 0) {
                    FieldError fieldError = new FieldError("", "", salesInventory.getInventory().getBarcode() + " barkodlu " + salesInventory.getInventory().getName() + " " + employee.getPerson().getFullName() + " adına təhkim edilməmişdir");
                    binding.addError(fieldError);
                }
            }
        } else {
            FieldError fieldError = new FieldError("", "", "İnventar əlavə edilməyib!");
            binding.addError(fieldError);
        }

        if(sales.getService() && sales.getServicer()==null){
            FieldError fieldError = new FieldError("servicer", "service", "Servis əməkdaşı seçilməyib!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            for(SalesInventory salesInventory: sales.getSalesInventories()){
                List<Action> oldActions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndInventoryAndEmployeeAndAction_Attr1AndAmountGreaterThanOrderById(true, salesInventory.getInventory(), employee, "consolidate", 0);
                if(oldActions.size()>0){
                    Action action = new Action();
                    action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("sell", "action"));
                    action.setAmount(1);
                    action.setInventory(salesInventory.getInventory());
                    action.setOrganization(sales.getOrganization());
                    action.setEmployee(getSessionUser().getEmployee());
                    action.setSupplier(oldActions.get(0).getSupplier());
                    actionRepository.save(action);
                    log(action, "warehouse_action", "create/edit", action.getId(), action.toString());

                    Action oldAction = oldActions.get(0);
                    oldAction.setAmount(oldAction.getAmount()-1);
                    actionRepository.save(oldAction);
                    log(oldAction, "warehouse_action", "create/edit", oldAction.getId(), oldAction.toString());
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
                if(!sales.getService()){
                    invoice.setAdvance(true);
                }
                invoice.setPrice(invoicePrice);
                invoice.setOrganization(sales.getOrganization());
                invoice.setDescription("Satışdan əldə edilən ödəniş " + invoicePrice + " AZN");
                invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                invoiceRepository.save(invoice);
                log(invoice, "sale_invoice", "create/edit", invoice.getId(), invoice.toString());
                invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                invoiceRepository.save(invoice);
                log(invoice, "sale_invoice", "create/edit", invoice.getId(), invoice.toString());
            }
            sales.setApprove(true);
            sales.setApproveDate(new Date());
            salesRepository.save(sales);
            log(sales, "sale_sales", "approve", sales.getId(), sales.toString());
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
            return getSchedulePayment(saleDate.equalsIgnoreCase("0")?DateUtility.getFormattedDate(new Date()):saleDate, scheduleId, periodId, lastPrice, down);
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
            log(invc, "sale_invoice", "create/edit", invc.getId(), invc.toString());
            invc.setChannelReferenceCode(String.valueOf(invc.getId()));
            invoiceRepository.save(invc);
            log(invc, "sale_invoice", "create/edit", invc.getId(), invc.toString(), "Kanal referans kodu update edildi");
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
            log(demonstration, "sale_demonstration", "create/edit", demonstration.getId(), demonstration.toString());
            EmployeeSaleDetail demonstrationSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(demonstration.getEmployee(), "{demonstration}");
            double price = demonstration.getAmount()*Double.parseDouble(demonstrationSaleDetail.getValue());
            Advance advance = new Advance(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-demonstration-advance", "advance"), demonstration.getEmployee(), demonstration.getOrganization(), demonstration.getId() + " nömrəli nümayişdən əldə edilən bonus", "", demonstration.getDemonstrateDate(), price);
            advanceRepository.save(advance);
            log(advance, "payroll_advance", "create/edit", advance.getId(), advance.toString(), "Nümayişdən yaranan avans ödənişi");
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
            log(invc, "sale_invoice", "consolidate", invc.getId(), invc.toString());
        }
        return mapPost(invc, binding, redirectAttributes, "/sale/invoice/");
    }

    @PostMapping(value = "/service-regulator/filter")
    public String postServiceRegulatorFilter(@ModelAttribute(Constants.FILTER) @Validated ServiceRegulator serviceRegulator, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(serviceRegulator, binding, redirectAttributes, "/sale/service-regulator");
    }

    @PostMapping(value = "/service-regulator")
    public String postServiceRegulator(@ModelAttribute(Constants.FORM) @Validated ServiceRegulator serviceRegulator, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            serviceRegulatorRepository.save(serviceRegulator);
            log(serviceRegulator, "sale_service_regulator", "create/edit", serviceRegulator.getId(), serviceRegulator.toString());
        }
        return mapPost(serviceRegulator, binding, redirectAttributes);
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
            log(invc, "sale_invoice", "approve", invc.getId(), invc.toString());

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
            transaction.setDescription("Satış|Servis, Kod: "+invc.getSales().getId() + " -> "
                    + invc.getSales().getCustomer().getPerson().getFullName() + " -> "
                    + " barkod: " + invc.getSales().getSalesInventories().get(0).getInventory().getName()
                    + " " + invc.getSales().getSalesInventories().get(0).getInventory().getBarcode()
            );
            transactionRepository.save(transaction);
            log(transaction, "accounting_transaction", "create/edit", transaction.getId(), transaction.toString());

            List<Advance> advances = new ArrayList<>();
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            String percent = "*0.01";
            Dictionary advance = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-sale-advance", "advance");
            Sales sales = invc.getSales();

            if(sales!=null && sales.getServicer()!=null){
                EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getServicer(), "{service}");
                String advancePrice = saleDetail.getValue().replaceAll(Pattern.quote("%"), percent);
                advances.add(new Advance(advance,
                        sales.getServicer(),
                        Util.getUserBranch(sales.getServicer().getOrganization()),
                        sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. "+sales.getServicer().getPerson().getFullName()+" (Servis işçisi)",
                        advancePrice,
                        sales.getSaleDate(),
                        Double.parseDouble(String.valueOf(engine.eval(advancePrice)))
                ));
            }
            if(sales!=null && invc.getCollector()!=null){
                EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(invc.getCollector(), "{collect}");
                String advancePrice = saleDetail.getValue().replaceAll(Pattern.quote("%"), percent);
                advances.add(new Advance(advance,
                        invc.getCollector(),
                        Util.getUserBranch(sales.getServicer().getOrganization()),
                        sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. "+invc.getCollector().getPerson().getFullName()+" (Yığımçı)",
                        advancePrice,
                        sales.getSaleDate(),
                        Double.parseDouble(String.valueOf(engine.eval(advancePrice)))
                ));
            }
            if(sales!=null && invc.getApprove() && invc.getAdvance()){
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
            }
            advanceRepository.saveAll(advances);

            log(advances, "accounting_advance", "create/edit", null, advances.toString());

            Sales sales1 = salesRepository.getSalesById(sales.getId());
            if(sales1!=null && sales1.getPayment()!=null && Util.calculateInvoice(sales1.getInvoices())>=sales1.getPayment().getLastPrice()){
                sales1.setSaled(true);
            } else if(sales1!=null && sales1.getPayment()!=null && Util.calculateInvoice(sales1.getInvoices())<sales1.getPayment().getLastPrice()){
                sales1.setSaled(false);
            }

            log(sales1, "sale_sales", "create/edit", sales1.getId(), sales1.toString(), "Satıldı statusu yeniləndi!");
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
            invoice1.setApprove(false);
            invoiceRepository.save(invoice1);
            log(invoice1, "sale_invoice", "create/edit", invoice1.getId(), invoice1.toString());
        }
        return mapPost(invc, binding, redirectAttributes, "/sale/invoice/");
    }

    @ResponseBody
    @GetMapping(value = "/api/service/inventory/{salesId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SalesInventory> getSalesInventories(@PathVariable("salesId") Integer salesId){
        return salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(salesId);
    }


}
