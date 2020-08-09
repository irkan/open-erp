package com.openerp.controller;

import com.openerp.domain.Return;
import com.openerp.domain.SalesSchedule;
import com.openerp.domain.Schedule;
import com.openerp.entity.*;
import com.openerp.entity.Dictionary;
import com.openerp.service.SalesService;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.SALE_PRICES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sale-price"));
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            model.addAttribute(Constants.GUARANTEES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("guarantee"));
            model.addAttribute(Constants.SALES_TYPES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("sales-type"));
            model.addAttribute(Constants.TAX_CONFIGURATIONS, taxConfigurationRepository.getTaxConfigurationsByActiveTrueAndOrganization(getSessionOrganization()));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.RETURN_FORM)){
                model.addAttribute(Constants.RETURN_FORM, new Return());
            }
            if(!model.containsAttribute(Constants.FILTER)){
                Sales sales = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null);
                sales.setApprove(null);
                model.addAttribute(Constants.FILTER, sales);
            }
            Page<Sales> sales = salesService.findAll((Sales) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("approve").descending().by("id").descending()));
            for(Sales sales1: sales){
                sales1.setSalesInventories(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(sales1.getId()));
            }
            model.addAttribute(Constants.LIST, sales);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(sales, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SCHEDULE)){
            Sales filterSales = null;
            if(!model.containsAttribute(Constants.FILTER)){
                filterSales = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null);
                model.addAttribute(Constants.FILTER, new SalesSchedule(filterSales));
            }
            if(!model.containsAttribute(Constants.FORM)){
                SalesSchedule salesSchedule = (SalesSchedule) model.asMap().get(Constants.FILTER);
                model.addAttribute(Constants.FORM, new Schedule(salesSchedule.getScheduleDate()));
            }
            if(filterSales!=null && filterSales.getId()!=null){
                List<Schedule> schedules = new ArrayList<>();
                Sales sale = null;
                if(!data.equals(Optional.empty())){
                    sale = salesRepository.getSalesById(Integer.parseInt(data.get()));
                    double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                    if(sale!=null && sale.getPayment()!=null && !sale.getPayment().getCash()){
                        schedules = Util.getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule(), sale.getPayment().getPeriod(), sale.getPayment().getLastPrice(), sale.getPayment().getDown(), Util.parseInt(sale.getPayment().getGracePeriod()));
                        double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                        schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                        sale.getPayment().setLatency(Util.calculateLatency(schedules, sumOfInvoices, sale));
                        sale.getPayment().setSumOfInvoice(sumOfInvoices);
                        sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                    }
                }
                List<SalesSchedule> salesSchedules = new ArrayList<>();
                salesSchedules.add(new SalesSchedule(schedules, sale, true));
                Page<SalesSchedule> salesSchedulePage = new PageImpl<>(salesSchedules);
                model.addAttribute(Constants.LIST, salesSchedulePage);
            } else {
                SalesSchedule salesSchedule = (SalesSchedule) model.asMap().get(Constants.FILTER);
                List<SalesSchedule> salesSchedules = calculateSalesSchedule(salesSchedule);

                Page<SalesSchedule> salesSchedulePage = new PageImpl<>(salesSchedules);
                model.addAttribute(Constants.LIST, salesSchedulePage);
            }
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SERVICE)){
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization());
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
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization());
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
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization());
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
                Sales sales = new Sales((!data.equals(Optional.empty()) && !data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT))?Integer.parseInt(data.get()):null, !canViewAll()?getSessionOrganization():null);
                model.addAttribute(Constants.FILTER, new ServiceRegulator(sales, null));
            }
            Page<ServiceRegulator> serviceRegulators = serviceRegulatorService.findAll((ServiceRegulator) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, serviceRegulators);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(serviceRegulators, redirectAttributes, page);
            }
        } else if(page.equalsIgnoreCase(Constants.ROUTE.CALCULATOR)){
            model.addAttribute(Constants.PAYMENT_SCHEDULES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-schedule"));
            model.addAttribute(Constants.PAYMENT_PERIODS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("payment-period"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Sales());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.TAX_CONFIGURATION)) {
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new TaxConfiguration(getSessionOrganization()));
            }
            List<TaxConfiguration> taxConfigurations = taxConfigurationRepository.getTaxConfigurationsByActiveTrueAndOrganization(getSessionOrganization());
            if(canViewAll()){
                taxConfigurations = taxConfigurationRepository.getTaxConfigurationsByActiveTrue();
            }
            for(TaxConfiguration taxConfiguration: taxConfigurations){
                List<Sales> salesList = salesRepository.getSalesByActiveTrueAndApproveTrueAndSaledFalseAndTaxConfigurationAndReturnedFalse(taxConfiguration);
                taxConfiguration.setSalesCount(salesList.size());
                taxConfiguration.setPlannedPaymentAmountMonthly(Util.calculatePlannedPaymentMonthly(salesList));
            }
            model.addAttribute(Constants.LIST, taxConfigurations);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(taxConfigurationRepository.findAll(), redirectAttributes, page);
            }
        }
        redirectAttributes.addFlashAttribute(Constants.FILTER, model.asMap().get(Constants.FILTER));
        return "layout";
    }

    @PostMapping(value = "/tax-configuration")
    public String postTaxConfiguration(@ModelAttribute(Constants.FORM) @Validated TaxConfiguration taxConfiguration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            taxConfigurationRepository.save(taxConfiguration);
            log(taxConfiguration, "tax_configuration", "create/edit", taxConfiguration.getId(), taxConfiguration.toString());
        }
        return mapPost(taxConfiguration, binding, redirectAttributes);
    }


    @PostMapping(value = "/sales", consumes = {"multipart/form-data"})
    public String postSales(@RequestParam(name = "file1", required = false) MultipartFile file1, @RequestParam(name = "file2", required = false) MultipartFile file2, @ModelAttribute(Constants.FORM) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            salesInventoryRepository.deleteInBatch(salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(sales.getId()));
            if(sales.getSalesInventories()!=null){
                List<SalesInventory> salesInventories = new ArrayList<>();
                for(SalesInventory salesInventory: sales.getSalesInventories()){
                    salesInventories.add(new SalesInventory(salesInventory.getInventory(), sales, salesInventory.getSalesType()));
                }
                sales.setSalesInventories(salesInventories);
            }

            if(sales.getPayment().getCash()){
                sales.getPayment().setPeriod(null);
                sales.getPayment().setSchedule(null);
                sales.getPayment().setSchedulePrice(null);
                sales.getPayment().setDown(sales.getPayment().getLastPrice());
            } else {
                sales.getPayment().setSchedulePrice(Util.schedulePrice(sales.getPayment().getSchedule(), sales.getPayment().getLastPrice(), sales.getPayment().getDown()));
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
            log(sales, "sales", "create/edit", sales.getId(), sales.toString());

            if(sales.getService()){
                List<Sales> salesList = salesRepository.getSalesByActiveTrueAndApproveTrueAndServiceFalseAndCustomerAndOrganizationAndReturnedFalseOrderByIdDesc(sales.getCustomer(), sales.getOrganization());
                if(salesList.size()>0){
                    addContactHistory(salesList.get(0), "Servis əlavə edildi: "+((sales.getPayment()!=null && sales.getPayment().getDescription()!=null)?sales.getPayment().getDescription():""), sales);
                }
            }

            Person person = sales.getCustomer().getPerson();
            Dictionary documentType = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("id card", "document-type");
            personDocumentRepository.deleteInBatch(personDocumentRepository.getPersonDocumentsByPersonAndDocumentType(person, documentType));
            if(file1!=null){
                PersonDocument document1 = new PersonDocument(person, documentType, ImageResizer.compress(file1.getInputStream(), file1.getOriginalFilename()), null, file1.getOriginalFilename());
                personDocumentRepository.save(document1);
                log(sales, "person_document", "create/edit", document1.getId(), document1.toString());
            }
            if(file2!=null){
                PersonDocument document2 = new PersonDocument(person, documentType, ImageResizer.compress(file2.getInputStream(), file2.getOriginalFilename()), null, file2.getOriginalFilename());
                personDocumentRepository.save(document2);
                log(sales, "person_document", "create/edit", document2.getId(), document2.toString());
            }
        }
        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes);
    }

    @PostMapping(value = "/sales/tax-configuration")
    public String postSalesReturn(@ModelAttribute(Constants.FORM) @Validated Sales sale, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Sales sales = salesRepository.getSalesById(sale.getId());
        if(!binding.hasErrors() && sale.getTaxConfiguration()!=null && sale.getTaxConfiguration().getVoen()!=null){
            sales.setTaxConfiguration(sale.getTaxConfiguration());
            salesRepository.save(sales);

            log(sales, "sales", "edit", sales.getId(), sales.toString(), "VÖEN əlavə edildi: " + sales.getTaxConfiguration().getVoen());
        }
        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes, "/sale/sales");
    }

    @PostMapping(value = "/sales/return")
    public String postSalesReturn(@ModelAttribute(Constants.RETURN_FORM) @Validated Return returnForm, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Sales sales = salesRepository.getSalesById(returnForm.getSalesId());
        if(!binding.hasErrors()){
            for(SalesInventory salesInventory: returnForm.getSalesInventories()){
                Action action = new Action();
                action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("return", "action"));
                action.setOld(salesInventory.getInventory().getOld());
                action.setInventory(salesInventory.getInventory());
                action.setAmount(1);
                action.setOrganization(sales.getOrganization());
                actionRepository.save(action);

                log(action, "action", "return", action.getId(), action.toString());
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
                log(invoice, "invoice", "create/edit", invoice.getId(), invoice.toString());
                invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                invoiceRepository.save(invoice);
                log(invoice, "invoice", "create/edit", invoice.getId(), invoice.toString());
            }

            ContactHistory contactHistory = new ContactHistory();
            contactHistory.setOrganization(sales.getOrganization());
            contactHistory.setDescription(returnForm.getReason());
            contactHistory.setSales(sales);
            contactHistory.setUser(getSessionUser());
            contactHistoryRepository.save(contactHistory);
            log(contactHistory, "collect_contact_history", "create/edit", contactHistory.getId(), contactHistory.toString());

            sales.setReturned(true);
            sales.setReturnedDate(new Date());
            salesRepository.save(sales);

            log(sales, "sales", "delete", sales.getId(), sales.toString(), "Qaytarılma icra edildi");

            GlobalConfiguration saleReturnCreditAdvance = configurationRepository.getGlobalConfigurationByKeyAndActiveTrue("sale_return_credit_advance_limit");
            if(!sales.getService() && Util.calculateInvoice(sales.getInvoices())<=(Util.parseInt(saleReturnCreditAdvance.getAttribute())-Util.parseInt(returnForm.getReturnPrice()))){
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                String percent = "*0.01";
                Dictionary credit = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("credit", "action");

                if(sales.getVanLeader()!=null){
                    EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getVanLeader(), "{van_leader}");
                    String calculated_bonus = saleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("%"), percent);
                    Advance advance;
                    try{
                        advance = new Advance(credit,
                                sales.getVanLeader(),
                                sales.getOrganization(),
                                sales.getId() + " satış geri qaytarıldı.  "+saleDetail.getEmployee().getPerson().getFullName()+" (Ven lider) avansın silinməsi. Kredit əməliyyatı",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }

                if(sales.getDealer()!=null){
                    try{
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getDealer(), "{dealer}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);
                        Advance advance = new Advance(credit,
                                sales.getDealer(),
                                sales.getOrganization(),
                                sales.getId() + " satış geri qaytarıldı.  "+saleDetail.getEmployee().getPerson().getFullName()+" (Satıcı) avansın silinməsi. Kredit əməliyyatı",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }

                if(sales.getCanavasser()!=null){
                    try{
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getCanavasser(), "{canvasser}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);
                        Advance advance = new Advance(credit,
                                sales.getCanavasser(),
                                sales.getOrganization(),
                                sales.getId() + " satış geri qaytarıldı.  "+saleDetail.getEmployee().getPerson().getFullName()+" (Canavasser) avansın silinməsi. Kredit əməliyyatı",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }

                if(sales.getConsole()!=null){
                    try{
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getConsole(), "{consul}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);
                        Advance advance = new Advance(credit,
                                sales.getConsole(),
                                sales.getOrganization(),
                                sales.getId() + " satış geri qaytarıldı.  "+saleDetail.getEmployee().getPerson().getFullName()+" (Konsul) avansın silinməsi. Kredit əməliyyatı",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }

        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes, "/sale/sales");
    }

    @PostMapping(value = "/sales/approve")
    public String postSalesApprove(@ModelAttribute(Constants.FORM) @Validated Sales sales, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        sales = salesRepository.getSalesById(sales.getId());
        Employee employee = sales.getService()?sales.getServicer():sales.getVanLeader();// (sales.getService() && sales.getServicer()!=null)?sales.getServicer():getSessionUser().getEmployee();
        if(sales.getSalesInventories().size()>0 && !sales.getService()){
            Map<Inventory, List<SalesInventory>> salesInventoryMap = Util.groupInventory(sales.getSalesInventories());
            for(Inventory inventory: salesInventoryMap.keySet()) {
                List<Action> oldActions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndInventoryAndEmployeeAndAction_Attr1AndAmountGreaterThanOrderById(true, inventory, employee, "consolidate", 0);
                if (oldActions.size() == 0 || Util.calculateInventoryAmount(oldActions)<salesInventoryMap.get(inventory).size()) {
                    FieldError fieldError = new FieldError("", "", inventory.getBarcode() + " barkodlu " + inventory.getName() + " " + employee.getPerson().getFullName() + " adına " + (salesInventoryMap.get(inventory).size()-Util.calculateInventoryAmount(oldActions)) + " ədəd əlavə inventarın təhkim edilməsinə ehtiyyac vardır!");
                    binding.addError(fieldError);
                }
            }
        } else if(!sales.getService()) {
            FieldError fieldError = new FieldError("", "", "İnventar əlavə edilməyib!");
            binding.addError(fieldError);
        }

        if(!sales.getService() && sales.getTaxConfiguration()==null){
            FieldError fieldError = new FieldError("", "", "VÖEN təhkim edilməyib!");
            binding.addError(fieldError);
        }

        if(sales.getService() && sales.getServicer()==null){
            FieldError fieldError = new FieldError("servicer", "service", "Servis əməkdaşı seçilməyib!");
            binding.addError(fieldError);
        }

        if(!sales.getService() && sales.getVanLeader().getId()!=getSessionUser().getEmployee().getId() && !canApprove(sales.getVanLeader(), null, "sales")){
            FieldError fieldError = new FieldError("", "", "Əməliyyat " + sales.getVanLeader().getPerson().getFullName() + " tərəfindən edilməlidir!");
            binding.addError(fieldError);
        }

        if(sales.getApprove()){
            List<Log> logs = logRepository.getLogsByActiveTrueAndTableNameAndRowIdAndOperationOrderByIdDesc("sales", sales.getId(), "approve");
            FieldError fieldError = new FieldError("", "", "Əməliyyat "+(logs.size()>0?(" "+logs.get(0).getUsername() + " tərəfindən " + DateUtility.getFormattedDateTime(logs.get(0).getOperationDate()) + " tarixində "):" ")+"icra edilmişdir!");
            binding.addError(fieldError);
        }

        if(!checkBackDate(sales.getSaleDate())){
            FieldError fieldError = new FieldError("", "", "Satış tarixi " + DateUtility.getFormattedDate(sales.getSaleDate()) + " olduğu üçün təsdiqə icazə verilmir!");
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
                    action.setEmployee(employee);
                    action.setSupplier(oldActions.get(0).getSupplier());
                    actionRepository.save(action);
                    log(action, "action", "create/edit", action.getId(), action.toString());

                    Action oldAction = oldActions.get(0);
                    oldAction.setAmount(oldAction.getAmount()-1);
                    actionRepository.save(oldAction);
                    log(oldAction, "action", "create/edit", oldAction.getId(), oldAction.toString());
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
                invoice.setCreditable(true);
                if(!sales.getService()){
                    invoice.setAdvance(true);
                }
                invoice.setPrice(invoicePrice);
                invoice.setOrganization(sales.getOrganization());
                invoice.setDescription("Satışdan əldə edilən ilkin ödəniş " + invoicePrice + " AZN");
                invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                invoiceRepository.save(invoice);
                log(invoice, "invoice", "create/edit", invoice.getId(), invoice.toString());
                invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                invoiceRepository.save(invoice);
                log(invoice, "invoice", "create/edit", invoice.getId(), invoice.toString());
            }
            sales.setApprove(true);
            sales.setApproveDate(new Date());
            salesRepository.save(sales);
            log(sales, "sales", "approve", sales.getId(), sales.toString());
        }
        if(sales.getService()){
            return mapPost(sales, binding, redirectAttributes, "/sale/service");
        }
        return mapPost(sales, binding, redirectAttributes, "/sale/sales");
    }

    @PostMapping(value = "/sales/payment")
    public String postSalesPayment(@ModelAttribute(Constants.FORM) @Validated Sales sale, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Sales sales = salesRepository.getSalesById(sale.getId());
        if(sales.getService()){
            FieldError fieldError = new FieldError("", "", "Servis dəyişdirilə bilməz!");
            binding.addError(fieldError);
        }
        if(!checkBackDate(sales.getSaleDate())){
            FieldError fieldError = new FieldError("", "", "Satış tarixi " + DateUtility.getFormattedDate(sales.getSaleDate()) + " olduğu üçün əməliyyata icazə verilmir!");
            binding.addError(fieldError);
        }
        if(!sales.getService() && sale.getVanLeader()==null){
            FieldError fieldError = new FieldError("", "", "Ven lider təyin edilməyib!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            sales.getPayment().setPrice(sale.getPayment().getPrice());
            sales.getPayment().setLastPrice(sale.getPayment().getLastPrice());
            if(sales.getPayment().getCash()){
                sales.getPayment().setPeriod(null);
                sales.getPayment().setSchedule(null);
                sales.getPayment().setSchedulePrice(null);
                sales.getPayment().setDown(sales.getPayment().getLastPrice());
            } else {
                sales.getPayment().setPeriod(sale.getPayment().getPeriod());
                sales.getPayment().setSchedule(sale.getPayment().getSchedule());
                sales.getPayment().setSchedulePrice(Util.schedulePrice(sales.getPayment().getSchedule(), sales.getPayment().getLastPrice(), sales.getPayment().getDown()));
                sales.getPayment().setDown(sale.getPayment().getDown());
            }
            sales.setGuarantee(sales.getGuarantee()!=null?sales.getGuarantee():6);
            sales.setGuaranteeExpire(Util.guarantee(sales.getSaleDate()==null?new Date():sales.getSaleDate(), sales.getGuarantee()));
            sales.getPayment().setDescription(sale.getPayment().getDescription());
            sales.getPayment().setGracePeriod(sale.getPayment().getGracePeriod());

            salesRepository.save(sales);

            log(sales, "sales", "create/edit", sales.getId(), sales.toString(), "Ödəniş qrafiki yeniləndi");


            double invoicePrice = 0d;
            if(sales.getPayment().getCash()){
                invoicePrice = sales.getPayment().getLastPrice();
            } else {
                invoicePrice = sales.getPayment().getDown();
            }

            if(invoicePrice-Util.calculateInvoice(sales.getInvoices())>0){
                Invoice invoice = new Invoice();
                invoice.setSales(sales);
                invoice.setApprove(false);
                invoice.setCreditable(true);
                invoice.setPrice(invoicePrice-Util.calculateInvoice(sales.getInvoices()));
                invoice.setAdvance((!sales.getService() && Util.calculateInvoice(sales.getInvoices())==0)?true:false);
                invoice.setOrganization(sales.getOrganization());
                invoice.setDescription("Satışdan əldə edilən ödəniş " + invoice.getPrice() + " AZN");
                invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
                invoiceRepository.save(invoice);
                log(invoice, "invoice", "create/edit", invoice.getId(), invoice.toString());
                invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
                invoiceRepository.save(invoice);
                log(invoice, "invoice", "create/edit", invoice.getId(), invoice.toString());
            }


            if(Util.calculateInvoice(sales.getInvoices())>0){
                ScriptEngineManager mgr = new ScriptEngineManager();
                ScriptEngine engine = mgr.getEngineByName("JavaScript");
                String percent = "*0.01";
                Dictionary bonus = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-sale-advance", "advance");
                Dictionary credit = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("credit", "action");

                if(sale.getVanLeader()!=null && sale.getVanLeader().getId().intValue()!=sales.getVanLeader().getId().intValue()){
                    EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getVanLeader(), "{van_leader}");
                    String calculated_bonus = saleDetail.getValue()
                            .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                            .replaceAll(Pattern.quote("%"), percent);
                    Advance advance;
                    try{
                        advance = new Advance(credit,
                                sales.getVanLeader(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satışdan "+saleDetail.getEmployee().getPerson().getFullName()+" (Ven lider) çıxarıldığı üçün verilən kredit əməliyyatı. Təsdiq edilmiş satışın redaktəsi",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                    
                    try{
                        saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sale.getVanLeader(), "{van_leader}");
                        calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sale.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);

                        advance = new Advance(bonus,
                                sale.getVanLeader(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satış ven lider redaktə edildi."+saleDetail.getEmployee().getPerson().getFullName()+" (Ven lider)",
                                calculated_bonus,
                                new Date(),
                                Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "create/edit", advance.getId(), advance.toString());
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }

                }
                
                try{
                    if((sales.getDealer()!=null && sale.getDealer()!=null && sales.getDealer().getId().intValue()!=sale.getDealer().getId().intValue()) ||
                            (sales.getDealer()!=null && sale.getDealer()==null)
                    ){
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getDealer(), "{dealer}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);
                        Advance advance = new Advance(credit,
                                sales.getDealer(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satışdan "+saleDetail.getEmployee().getPerson().getFullName()+" (Satıcı) çıxarıldığı üçün verilən kredit əməliyyatı. Təsdiq edilmiş satışın redaktəsi",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }  
                
                try{
                    if((sales.getDealer()!=null && sale.getDealer()!=null && sales.getDealer().getId().intValue()!=sale.getDealer().getId().intValue()) ||
                            (sales.getDealer()==null && sale.getDealer()!=null)
                    ){
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sale.getVanLeader(), "{dealer}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sale.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);

                        Advance advance = new Advance(bonus,
                                sale.getDealer(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satış satıcı redaktə edildi."+saleDetail.getEmployee().getPerson().getFullName()+" (Satıcı)",
                                calculated_bonus,
                                new Date(),
                                Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "create/edit", advance.getId(), advance.toString());
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }   
                
                try{
                    if((sales.getCanavasser()!=null && sale.getCanavasser()!=null && sales.getCanavasser().getId().intValue()!=sale.getCanavasser().getId().intValue()) ||
                            (sales.getCanavasser()!=null && sale.getCanavasser()==null)
                    ){
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getCanavasser(), "{canvasser}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);
                        Advance advance = new Advance(credit,
                                sales.getCanavasser(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satışdan "+saleDetail.getEmployee().getPerson().getFullName()+" (Canavasser) çıxarıldığı üçün verilən kredit əməliyyatı. Təsdiq edilmiş satışın redaktəsi",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }   
                
                try{
                    if((sales.getCanavasser()!=null && sale.getCanavasser()!=null && sales.getCanavasser().getId().intValue()!=sale.getCanavasser().getId().intValue()) ||
                            (sales.getCanavasser()==null && sale.getCanavasser()!=null)
                    ){
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sale.getCanavasser(), "{canvasser}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sale.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);

                        Advance advance = new Advance(bonus,
                                sale.getCanavasser(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satış canavasser redaktə edildi."+saleDetail.getEmployee().getPerson().getFullName()+" (Canavasser)",
                                calculated_bonus,
                                new Date(),
                                Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "create/edit", advance.getId(), advance.toString());
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                } 
                
                try{
                    if((sales.getConsole()!=null && sale.getConsole()!=null && sales.getConsole().getId().intValue()!=sale.getConsole().getId().intValue()) ||
                            (sales.getConsole()!=null && sale.getConsole()==null)
                    ){
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sales.getConsole(), "{consul}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sales.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);
                        Advance advance = new Advance(credit,
                                sales.getConsole(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satışdan "+saleDetail.getEmployee().getPerson().getFullName()+" (Konsul) çıxarıldığı üçün verilən kredit əməliyyatı. Təsdiq edilmiş satışın redaktəsi",
                                calculated_bonus,
                                new Date(),
                                -1*Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "credit", advance.getId(), advance.toString(), "Kredit əməliyyatı!");
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }  
                
                try{
                    if((sales.getConsole()!=null && sale.getConsole()!=null && sales.getConsole().getId().intValue()!=sale.getConsole().getId().intValue()) ||
                            (sales.getConsole()==null && sale.getConsole()!=null)
                    ){
                        EmployeeSaleDetail saleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(sale.getConsole(), "{consul}");
                        String calculated_bonus = saleDetail.getValue()
                                .replaceAll(Pattern.quote("{sale_price}"), String.valueOf(sale.getPayment().getLastPrice()))
                                .replaceAll(Pattern.quote("%"), percent);

                        Advance advance = new Advance(bonus,
                                sale.getConsole(),
                                sales.getOrganization(),
                                sales.getId() + " nömrəli satış konsul redaktə edildi."+saleDetail.getEmployee().getPerson().getFullName()+" (Konsul)",
                                calculated_bonus,
                                new Date(),
                                Double.parseDouble(String.valueOf(engine.eval(calculated_bonus))));

                        advanceRepository.save(advance);

                        log(advance, "advance", "create/edit", advance.getId(), advance.toString());
                    }
                } catch (Exception e){
                    log.error(e.getMessage(), e);
                }
            }
        }

        sales.setVanLeader(sale.getVanLeader());
        sales.setCanavasser(sale.getCanavasser());
        sales.setConsole(sale.getConsole());
        sales.setCanavasser(sale.getCanavasser());
        salesRepository.save(sales);

        log(sales, "sales", "create/edit", sales.getId(), sales.toString(), "Satış əməkdaşları yeniləndi");

        
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
    @GetMapping(value = "/payment/schedule/{lastPrice}/{down}/{schedule}/{period}/{gracePeriod}/{saleDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Schedule> getPaymentSchedulePrice(Model model, @PathVariable("lastPrice") double lastPrice, @PathVariable("down") double down, @PathVariable("schedule") int scheduleId, @PathVariable("period") int periodId, @PathVariable(name = "gracePeriod",required = false) String gracePeriodStr, @PathVariable(name = "saleDate", value = "") String saleDate){
        try {
            return Util.getSchedulePayment(saleDate.equalsIgnoreCase("0")?DateUtility.getFormattedDate(new Date()):saleDate, dictionaryRepository.getDictionaryById(scheduleId), dictionaryRepository.getDictionaryById(periodId), lastPrice, down, Util.parseInt(gracePeriodStr));
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @PostMapping(value = "/invoice")
    public String postInvoice(@ModelAttribute(Constants.FORM) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            invoice(invoice);
        }
        return mapPost(invoice, binding, redirectAttributes);
    }

    @PostMapping(value = "/invoice/filter")
    public String postInvoiceFilter(@ModelAttribute(Constants.FILTER) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(invoice, binding, redirectAttributes, "/sale/invoice");
    }

    @PostMapping(value = "/schedule/filter")
    public String postScheduleFilter(@ModelAttribute(Constants.FILTER) @Validated SalesSchedule salesSchedule, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(salesSchedule, binding, redirectAttributes, "/sale/schedule");
    }

    @PostMapping(value = "/schedule/transfer")
    public String postScheduleTransfer(@ModelAttribute(Constants.FORM) @Validated Schedule schedule, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            Invoice invoice = new Invoice();
            invoice.setSales(schedule.getSales());
            invoice.setApprove(false);
            invoice.setInvoiceDate(schedule.getInvoiceDate()!=null?schedule.getInvoiceDate():invoice.getInvoiceDate());
            invoice.setPrice(schedule.getPayableAmount());
            invoice.setOrganization(schedule.getSales().getOrganization());
            invoice.setDescription("Ödəniş " + invoice.getPrice() + " AZN" + (schedule.getDescription()!=null?" " + schedule.getDescription():""));
            invoice.setPaymentChannel(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cash", "payment-channel"));
            invoiceRepository.save(invoice);
            invoice.setChannelReferenceCode(String.valueOf(invoice.getId()));
            invoiceRepository.save(invoice);
            log(invoice, "sales_invoice", "create/edit", invoice.getId(), invoice.toString(), "Ödəniş qrafikindən yaradılan hesab-faktura");

            addContactHistory(invoice.getSales(), "Hesab-faktura yaradıldı: " + invoice.getId(), null);
        }
        Sales filterSales = new Sales(schedule.getSales().getId(), !canViewAll()?getSessionOrganization():null);
        return mapFilter(new SalesSchedule(filterSales, schedule.getScheduleDate()), binding, redirectAttributes, "/sale/schedule");
    }

    @PostMapping(value = "/demonstration")
    public String postDemonstration(@ModelAttribute(Constants.FORM) @Validated Demonstration demonstration, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            demonstrationRepository.save(demonstration);
            log(demonstration, "demonstration", "create/edit", demonstration.getId(), demonstration.toString());
            EmployeeSaleDetail demonstrationSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(demonstration.getEmployee(), "{demonstration}");
            double price = demonstration.getAmount()*Double.parseDouble(demonstrationSaleDetail.getValue());
            Advance advance = new Advance(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-demonstration-advance", "advance"), demonstration.getEmployee(), demonstration.getOrganization(), demonstration.getId() + " nömrəli nümayişdən əldə edilən bonus", "", demonstration.getDemonstrateDate(), price);
            advanceRepository.save(advance);
            log(advance, "advance", "create/edit", advance.getId(), advance.toString(), "Nümayişdən yaranan avans ödənişi");
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
            log(invc, "invoice", "consolidate", invc.getId(), invc.toString());
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
            log(serviceRegulator, "service_regulator", "create/edit", serviceRegulator.getId(), serviceRegulator.toString());
        }
        return mapPost(serviceRegulator, binding, redirectAttributes);
    }

    @ResponseBody
    @GetMapping(value = "/sales/check/{saleId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sales getSalesCheck(@PathVariable("saleId") String saleId){
        try {
             return salesRepository.getSalesByIdAndActiveTrue(Integer.parseInt(saleId));
        } catch (Exception e){
            log(null, "error", "", "", null, "", e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @PostMapping(value = "/invoice/approve")
    public String postInvoiceApprove(@ModelAttribute(Constants.FORM) @Validated Invoice invoice, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Invoice invc = invoiceRepository.getInvoiceById(invoice.getId());
        if(invc.getSales()==null){
            FieldError fieldError = new FieldError("", "", "SATIŞ kodu tapılmadı!");
            binding.addError(fieldError);
        } else if(!invc.getSales().getService() && invc.getCollector()==null && invc.getPrice()>0){
            FieldError fieldError = new FieldError("", "", "Yığımçı təyin edilməlidir!");
            binding.addError(fieldError);
        }
        if(invc.getApprove()){
            List<Log> logs = logRepository.getLogsByActiveTrueAndTableNameAndRowIdAndOperationOrderByIdDesc("invoice", invc.getId(), "approve");
            FieldError fieldError = new FieldError("", "", "Təsdiq əməliyyatı"+(logs.size()>0?(" "+logs.get(0).getUsername() + " tərəfindən " + DateUtility.getFormattedDateTime(logs.get(0).getOperationDate()) + " tarixində "):" ")+"icra edilmişdir!");
            binding.addError(fieldError);
        }
        if(!canApprove(invc.getOrganization())){
            FieldError fieldError = new FieldError("", "", "Sizin təsdiq əməliyyatına icazəniz yoxdur!");
            binding.addError(fieldError);
        }
        if(!checkBackDate(invc.getInvoiceDate())){
            FieldError fieldError = new FieldError("", "", "HF tarixi " + DateUtility.getFormattedDate(invc.getInvoiceDate()) + " olduğu üçün təsdiqə icazə verilmir!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            invc.setApprove(true);
            invc.setApproveDate(new Date());
            invc.setDescription(invoice.getDescription());
            invc.setAdvance(invoice.getAdvance());
            invoiceRepository.save(invc);
            log(invc, "invoice", "approve", invc.getId(), invc.toString());

            Transaction transaction = new Transaction();
            transaction.setApprove(false);
            transaction.setDebt(invc.getPrice()>0?true:false);
            transaction.setInventory(invc.getSales().getSalesInventories().get(0).getInventory());
            transaction.setOrganization(invc.getOrganization());
            transaction.setPrice(Math.abs(invc.getPrice()));
            transaction.setCurrency("AZN");
            transaction.setRate(Util.getRate(currencyRateRepository.getCurrencyRateByCode(transaction.getCurrency().toUpperCase())));
            double sumPrice = Util.amountChecker(transaction.getAmount()) * transaction.getPrice() * transaction.getRate();
            transaction.setSumPrice(sumPrice);
            transaction.setAction(invc.getSales().getSalesInventories().get(0).getInventory().getActions().get(0).getAction()); //burda duzelis edilmelidir
            transaction.setDescription("Satış|Servis, Kod: "+invc.getSales().getId() + " -> "
                    + invc.getSales().getCustomer().getPerson().getFullName() + " -> "
                    + " barkod: " + invc.getSales().getSalesInventories().get(0).getInventory().getName()
                    + " " + invc.getSales().getSalesInventories().get(0).getInventory().getBarcode()
            );
            transactionRepository.save(transaction);
            log(transaction, "transaction", "create/edit", transaction.getId(), transaction.toString());

            List<Advance> advances = new ArrayList<>();
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine engine = mgr.getEngineByName("JavaScript");
            String percent = "*0.01";
            Dictionary advance = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-sale-advance", "advance");
            Sales sales = invc.getSales();
            //Sabuhinin isteyi ile legv edildi Farid OK verdi 10.07.2020
            /*if(sales!=null && sales.getServicer()!=null){
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
                        Util.getUserBranch(invc.getCollector().getOrganization()),
                        sales.getId() + " nömrəli satış və " + invoice.getId() + " nömrəli hesab fakturadan əldə edilən bonus. "+invc.getCollector().getPerson().getFullName()+" (Yığımçı)",
                        advancePrice,
                        sales.getSaleDate(),
                        Double.parseDouble(String.valueOf(engine.eval(advancePrice)))
                ));
            }*/
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
                            invc.getApproveDate(),
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
                            invc.getApproveDate(),
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
                            invc.getApproveDate(),
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
                            invc.getApproveDate(),
                            Double.parseDouble(String.valueOf(engine.eval(calculated_bonus)))
                    ));
                }
            }
            advanceRepository.saveAll(advances);

            log(advances, "advance", "create/edit", null, advances.toString());

            Sales sales1 = salesRepository.getSalesById(sales.getId());
            if(sales1!=null && sales1.getPayment()!=null && Util.calculateInvoice(sales1.getInvoices())>=sales1.getPayment().getLastPrice()){
                sales1.setSaled(true);
            } else if(sales1!=null && sales1.getPayment()!=null && Util.calculateInvoice(sales1.getInvoices())<sales1.getPayment().getLastPrice()){
                sales1.setSaled(false);
            }

            log(sales1, "sales", "create/edit", sales1.getId(), sales1.toString(), "Satıldı statusu yeniləndi!");
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
            log(invoice1, "invoice", "create/edit", invoice1.getId(), invoice1.toString());
        }
        return mapPost(invc, binding, redirectAttributes, "/sale/invoice/");
    }

    @ResponseBody
    @GetMapping(value = "/api/service/inventory/{salesId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SalesInventory> getSalesInventories(@PathVariable("salesId") Integer salesId){
        return salesInventoryRepository.getSalesInventoriesByActiveTrueAndSales_Id(salesId);
    }

    @ResponseBody
    @GetMapping(value = "/api/sales/{salesId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Sales getSales(@PathVariable("salesId") Integer salesId){
        return salesRepository.getSalesById(salesId);
    }

    @ResponseBody
    @GetMapping(value = "/api/invoice/{invoiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Invoice getInvoice(@PathVariable("invoiceId") Integer invoiceId){
        return invoiceRepository.getInvoiceById(invoiceId);
    }

    public List<Sales> collectSalesList(SalesSchedule salesSchedule) {
        Dictionary paymentPeriod = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1(String.valueOf(salesSchedule.getScheduleDate().getDate()), "payment-period");
        List<Sales> salesList = new ArrayList<>();
        List<ContactHistory> contactHistories = contactHistoryRepository.getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveAndSalesSaledAndOrganizationAndNextContactDateIsNotNullOrderByIdDesc(salesSchedule.getScheduleDate(), true, false, getSessionOrganization());
        if(canViewAll()){
            contactHistories = contactHistoryRepository.getContactHistoriesByActiveTrueAndNextContactDateAndSalesApproveAndSalesSaledAndNextContactDateIsNotNullOrderByIdDesc(salesSchedule.getScheduleDate(), true, false);
        }
        for(ContactHistory contactHistory: contactHistories){
            if(contactHistory.getSales()!=null){
                salesList.add(contactHistory.getSales());
            }
        }

        List<Sales> salesList1 = salesRepository.getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndOrganizationAndReturnedFalse(false, paymentPeriod, getSessionOrganization());
        if(canViewAll()){
            salesList1 = salesRepository.getSalesByActiveTrueAndApproveTrueAndPayment_CashAndPayment_PeriodAndSaledFalseAndReturnedFalse(false, paymentPeriod);
        }

        salesList.addAll(salesList1);

        List<Sales> salesList2 = salesRepository.getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaleDateAndSaledFalseAndOrganizationAndReturnedFalse(true, salesSchedule.getScheduleDate(), getSessionOrganization());
        if (canViewAll()){
            salesList2 = salesRepository.getSalesByActiveTrueAndApproveTrueAndPayment_CashAndSaleDateAndSaledFalseAndReturnedFalse(true, salesSchedule.getScheduleDate());
        }

        salesList.addAll(salesList2);

        return new ArrayList<>(new LinkedHashSet<>(salesList));
    }

    private List<SalesSchedule> calculateSalesSchedule(SalesSchedule salesSchedule) {
        List<Sales> salesList = collectSalesList(salesSchedule);
        List<SalesSchedule> salesSchedules = new ArrayList<>();
        for(Sales sales: salesList){
            List<Schedule> schedules = new ArrayList<>();
            double sumOfInvoices = Util.calculateInvoice(sales.getInvoices());
            if(sales.getPayment()!=null && !sales.getPayment().getCash()){
                schedules = Util.getSchedulePayment(DateUtility.getFormattedDate(sales.getSaleDate()), sales.getPayment().getSchedule(), sales.getPayment().getPeriod(), sales.getPayment().getLastPrice(), sales.getPayment().getDown(), Util.parseInt(sales.getPayment().getGracePeriod()));
                double plannedPayment = Util.calculatePlannedPayment(sales, schedules);
                schedules = Util.calculateSchedule(sales, schedules, sumOfInvoices);
                sales.getPayment().setLatency(Util.calculateLatency(schedules, sumOfInvoices, sales));
                sales.getPayment().setSumOfInvoice(sumOfInvoices);
                sales.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                schedules = Util.getDatedSchedule(schedules, salesSchedule.getScheduleDate());
            } else if(sales.getPayment()!=null && sales.getPayment().getCash()) {
                double lastPrice = sales.getPayment().getLastPrice()==null?0:sales.getPayment().getLastPrice();
                if(sumOfInvoices<lastPrice){
                    schedules.add(new Schedule(lastPrice-sumOfInvoices, salesSchedule.getScheduleDate()));
                }
            }
            salesSchedules.add(new SalesSchedule(schedules, sales));
        }
        return salesSchedules;
    }
}
