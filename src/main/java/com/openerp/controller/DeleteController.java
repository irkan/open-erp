package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/delete")
public class DeleteController extends SkeletonController {

    @PostMapping(value = "/{path}")
    public String getSubModules(RedirectAttributes redirectAttributes, @PathVariable("path") String path, @RequestParam(name="deletedId", defaultValue = "0") String id, @RequestBody String parameter, BindingResult binding) throws Exception {
        User user = getSessionUser();
        String parent = "admin";
        for(UserModuleOperation umo: user.getUserModuleOperations()){
            if(umo.getModuleOperation()!=null && umo.getModuleOperation().getModule()!=null
                    && umo.getModuleOperation().getModule().getPath().equalsIgnoreCase(path)){
                if(umo.getModuleOperation().getModule().getModule()==null){
                    parent = umo.getModuleOperation().getModule().getPath();
                } else if(umo.getModuleOperation().getModule().getModule().getModule()==null) {
                    parent = umo.getModuleOperation().getModule().getModule().getPath();
                } else if(umo.getModuleOperation().getModule().getModule().getModule().getModule()==null) {
                    parent = umo.getModuleOperation().getModule().getModule().getModule().getPath();
                }
                break;
            }
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(null, Constants.TEXT.SUCCESS));
        if(path.equalsIgnoreCase(Constants.ROUTE.DICTIONARY_TYPE)){
            DictionaryType dictionaryType = dictionaryTypeRepository.getDictionaryTypeById(Integer.parseInt(id));
            dictionaryType.setActive(false);
            dictionaryTypeRepository.save(dictionaryType);
            log(dictionaryType, "admin_dictionary_type", "create/edit", dictionaryType.getId(), dictionaryType.toString());
            if(dictionaryType!=null){
                for(Dictionary dictionary: dictionaryRepository.getDictionariesByDictionaryType_Id(dictionaryType.getId())){
                    dictionary.setActive(false);
                    dictionaryRepository.save(dictionary);
                    log(dictionary, "admin_dictionary_type", "create/edit", dictionary.getId(), dictionary.toString());
                }
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)){
            Dictionary dictionary = dictionaryRepository.getDictionaryById(Integer.parseInt(id));
            dictionary.setActive(false);
            dictionaryRepository.save(dictionary);
            log(dictionary, "admin_dictionary", "create/edit", dictionary.getId(), dictionary.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.MODULE)){
            Module module = moduleRepository.getModuleById(Integer.parseInt(id));
            module.setActive(false);
            moduleRepository.save(module);
            log(module, "admin_module", "create/edit", module.getId(), module.toString());
            for(ModuleOperation moduleOperation: moduleOperationRepository.getModuleOperationsByModule_Active(false)){
                userModuleOperationRepository.deleteInBatch(userModuleOperationRepository.getUserModuleOperationsByModuleOperation_Id(moduleOperation.getId()));
                log(moduleOperation, "admin_user_module_operation", "delete-in-batch", moduleOperation.getId(), moduleOperation.toString());
                moduleOperationRepository.deleteById(moduleOperation.getId());
                log(moduleOperation, "admin_module_operation", "delete", moduleOperation.getId(), moduleOperation.toString());
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.OPERATION)){
            Operation operation = operationRepository.getOperationById(Integer.parseInt(id));
            operation.setActive(false);
            operationRepository.save(operation);
            log(operation, "admin_operation", "create/edit", operation.getId(), operation.toString());
            for(ModuleOperation moduleOperation: moduleOperationRepository.getModuleOperationsByOperation_Active(false)){
                userModuleOperationRepository.deleteInBatch(userModuleOperationRepository.getUserModuleOperationsByModuleOperation_Id(moduleOperation.getId()));
                log(operation, "admin_operation", "delete-in-batch", operation.getId(), operation.toString());
                moduleOperationRepository.deleteById(moduleOperation.getId());
                log(moduleOperation, "admin_module_operation", "delete", moduleOperation.getId(), moduleOperation.toString());
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.MODULE_OPERATION)){
            Module module = moduleRepository.getModuleById(Integer.parseInt(id));
            log(module, "admin_module_operation", "delete", module.getId(), module.toString());
            moduleRepository.delete(module);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.USER_MODULE_OPERATION)){
            User userObject = userRepository.getUserByActiveTrueAndId(Integer.parseInt(id));
            userRepository.save(userObject);
            log(userObject, "admin_user", "delete", userObject.getId(), userObject.toString());
            List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(user.getId(), true);
            userModuleOperationRepository.deleteInBatch(userModuleOperations);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.EMPLOYEE)){
            Employee employee = employeeRepository.getEmployeeById(Integer.parseInt(id));
            List<User> users = userRepository.getUsersByEmployeeAndActiveTrue(employee);
            if(employee!=null && users.size()>0){
                FieldError fieldError = new FieldError("", "",  "Əməkdaş silinə bilməz! "+users.get(0).getUsername()+" adı ilə aktiv istifadəçi mövcuddur! Öncə istifadəçini deaktiv edin!");
                binding.addError(fieldError);
            }
            redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
            if(!binding.hasErrors()){
                employee.setActive(false);
                employeeRepository.save(employee);
                log(employee, "hr_employee", "delete", employee.getId(), employee.toString());
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ORGANIZATION)){
            Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(Integer.parseInt(id));
            organization.setActive(false);
            organizationRepository.save(organization);
            log(organization, "hr_organization", "delete", organization.getId(), organization.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SUPPLIER)){
            Supplier supplier = supplierRepository.getSuppliersById(Integer.parseInt(id));
            supplier.setActive(false);
            supplierRepository.save(supplier);
            log(supplier, "warehouse_supplier", "create/edit", supplier.getId(), supplier.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ACCOUNT)){
            Account account = accountRepository.getAccountById(Integer.parseInt(id));
            account.setActive(false);
            accountRepository.save(account);
            log(account, "accounting_account", "create/edit", account.getId(), account.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.NON_WORKING_DAY)){
            NonWorkingDay nonWorkingDay = nonWorkingDayRepository.getNonWorkingDayById(Integer.parseInt(id));
            nonWorkingDay.setActive(false);
            nonWorkingDayRepository.save(nonWorkingDay);
            log(nonWorkingDay, "hr_non_working_day", "create/edit", nonWorkingDay.getId(), nonWorkingDay.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SHORTENED_WORKING_DAY)){
            ShortenedWorkingDay shortenedWorkingDay = shortenedWorkingDayRepository.getShortenedWorkingDayById(Integer.parseInt(id));
            shortenedWorkingDay.setActive(false);
            shortenedWorkingDayRepository.save(shortenedWorkingDay);
            log(shortenedWorkingDay, "hr_shortened_working_day", "create/edit", shortenedWorkingDay.getId(), shortenedWorkingDay.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.PAYROLL_CONFIGURATION)){
            PayrollConfiguration payrollConfiguration = payrollConfigurationRepository.getPayrollConfigurationById(Integer.parseInt(id));
            payrollConfiguration.setActive(false);
            payrollConfigurationRepository.save(payrollConfiguration);
            log(payrollConfiguration, "payroll_configuration", "create/edit", payrollConfiguration.getId(), payrollConfiguration.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.VACATION)){
            Vacation vacation = vacationRepository.getVacationById(Integer.parseInt(id));
            vacation.setActive(false);
            vacationRepository.save(vacation);
            log(vacation, "hr_vacation", "create/edit", vacation.getId(), vacation.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.WORKING_HOUR_RECORD)){
            WorkingHourRecord workingHourRecord = workingHourRecordRepository.getWorkingHourRecordById(Integer.parseInt(id));
            workingHourRecord.setActive(false);
            workingHourRecordRepository.save(workingHourRecord);
            log(workingHourRecord, "payroll_working_hour_record", "create/edit", workingHourRecord.getId(), workingHourRecord.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SALARY)){
            Salary salary = salaryRepository.getSalaryById(Integer.parseInt(id));
            salary.setActive(false);
            salaryRepository.save(salary);
            log(salary, "payroll_salary", "create/edit", salary.getId(), salary.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ADVANCE)){
            Advance advance = advanceRepository.getAdvanceById(Integer.parseInt(id));
            advance.setActive(false);
            advanceRepository.save(advance);
            log(advance, "payroll_advance", "create/edit", advance.getId(), advance.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.INVENTORY)){
            Inventory inventory = inventoryRepository.getInventoryById(Integer.parseInt(id));
            int amount = Util.calculateInventoryAmount(inventory.getActions(), getSessionOrganization().getId()).getAllItemsCount();
            if(amount==0){
                inventory.setActive(false);
                inventoryRepository.save(inventory);
                log(inventory, "warehouse_inventory", "create/edit", inventory.getId(), inventory.toString());
            } else {
                FieldError fieldError = new FieldError("", "",  "İnventar qalığı " + amount + " olduğu üçün silinmədi!");
                binding.addError(fieldError);
                redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ACTION)){
            Action action = actionRepository.getActionById(Integer.parseInt(id));
            action.setActive(false);
            actionRepository.save(action);
            log(action, "warehouse_action", "delete", action.getId(), action.toString(), "Silinmə əməliyyatının ləğvi");

            List<Action> actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndActionOrderByIdDesc(action.getInventory().getId(), true, dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"));
            if(actions.size()>0){
                Action returnAction = actions.get(0);
                returnAction.setAmount(returnAction.getAmount()+action.getAmount());
                actionRepository.save(returnAction);
                log(returnAction, "warehouse_action", "create/edit", returnAction.getId(), returnAction.toString());
            } else {
                Action buy = new Action(
                        dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"),
                        action.getOrganization(),
                        action.getAmount(),
                        action.getInventory(),
                        action.getSupplier(),
                        false
                );
                actionRepository.save(buy);

                log(buy, "warehouse_action", "create/edit", buy.getId(), buy.toString(), "Yeni alış əməliyyatı");
            }
            return "redirect:/"+parent+"/"+path+"/"+action.getInventory().getId();
        } else if(path.equalsIgnoreCase(Constants.ROUTE.IDDISCOUNT)){
            IDDiscount idDiscount = iDDiscountRepository.getIDDiscountById(Integer.parseInt(id));
            idDiscount.setActive(false);
            iDDiscountRepository.save(idDiscount);
            log(idDiscount, "idgroup_id_discount", "delete", idDiscount.getId(), idDiscount.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.CONTACT_HISTORY)){
            ContactHistory contactHistory = contactHistoryRepository.getContactHistoryById(Integer.parseInt(id));
            contactHistory.setActive(false);
            contactHistoryRepository.save(contactHistory);
            log(contactHistory, "collect_payment_regulator_note", "delete", contactHistory.getId(), contactHistory.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.CUSTOMER)){
            Customer customer = customerRepository.getCustomerByIdAndActiveTrue(Integer.parseInt(id));
            customer.setActive(false);
            customerRepository.save(customer);
            log(customer, "crm_customer", "create/edit", customer.getId(), customer.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.CONFIGURATION)){
            Configuration configuration = configurationRepository.getConfigurationById(Integer.parseInt(id));
            configuration.setActive(false);
            configurationRepository.save(configuration);
            log(configuration, "admin_configuration", "create/edit", configuration.getId(), configuration.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.NOTIFICATION)){
            Notification notification = notificationRepository.getNotificationById(Integer.parseInt(id));
            log(notification, "admin_configuration", "delete", notification.getId(), notification.toString());
            notificationRepository.delete(notification);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.FINANCING)){
            Financing financing = financingRepository.getFinancingById(Integer.parseInt(id));
            financing.setActive(false);
            financingRepository.save(financing);
            log(financing, "accounting_financing", "create/edit", financing.getId(), financing.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SALES)){
            Sales sales = salesRepository.getSalesByIdAndActiveTrue(Integer.parseInt(id));
            sales.setActive(false);
            salesRepository.save(sales);
            log(sales, "sale_sales", "delete", sales.getId(), sales.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.LOG)){
            Log log = logRepository.getLogById(Integer.parseInt(id));
            log.setActive(false);
            logRepository.save(log);
            log(log, "admin_log", "delete", log.getId(), log.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.WEB_SERVICE_AUTHENTICATOR)){
            WebServiceAuthenticator webServiceAuthenticator = webServiceAuthenticatorRepository.getWebServiceAuthenticatorById(Integer.parseInt(id));
            webServiceAuthenticator.setActive(false);
            webServiceAuthenticatorRepository.save(webServiceAuthenticator);
            log(webServiceAuthenticator, "admin_web_service_authenticator", "delete", webServiceAuthenticator.getId(), webServiceAuthenticator.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.DEMONSTRATION)){
            Demonstration demonstration = demonstrationRepository.getDemonstrationById(Integer.parseInt(id));
            demonstration.setActive(false);
            demonstrationRepository.save(demonstration);
            log(demonstration, "sale_demonstration", "delete", demonstration.getId(), demonstration.toString());
            EmployeeSaleDetail demonstrationSaleDetail = employeeSaleDetailRepository.getEmployeeSaleDetailByEmployeeAndKey(demonstration.getEmployee(), "{demonstration}");
            double price = demonstration.getAmount()*Double.parseDouble(demonstrationSaleDetail.getValue());
            Advance advance = new Advance(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("bonus-demonstration-advance", "advance"), demonstration.getEmployee(), demonstration.getOrganization(), "Nümayişin silinməsinə görə kredit: Nümayiş №" + demonstration.getId(), "", demonstration.getDemonstrateDate(), price);
            advanceRepository.save(advance);
            log(advance, "payroll_advance", "create/edit", advance.getId(), advance.toString(), demonstration.getId() + "nğmrəli nümayişin silinməsinə görə kredit avans verilmişdir");
        } else if(path.equalsIgnoreCase(Constants.ROUTE.INVOICE)){
            Invoice invoice = invoiceRepository.getInvoiceById(Integer.parseInt(id));
            if(!invoice.getApprove()){
                invoice.setActive(false);
                invoiceRepository.save(invoice);
                log(invoice, "sale_invoice", "delete", invoice.getId(), invoice.toString());
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.PERIOD)){
            periodRepository.deleteById(Integer.parseInt(id));
            log(null, "admin_period", "delete", Integer.parseInt(id), "");
        } else if(path.equalsIgnoreCase(Constants.ROUTE.TRANSACTION)){
            transactionRepository.deleteById(Integer.parseInt(id));
            log(null, "accounting_transaction", "delete", Integer.parseInt(id), "", "Məlumat silindi");
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SERVICE_REGULATOR)){
            serviceRegulatorRepository.deleteById(Integer.parseInt(id));
            log(null, "sale_service_regulator", "delete", Integer.parseInt(id), "");
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ENDPOINT)){
            endpointRepository.deleteById(Integer.parseInt(id));
            log(null, "admin_endpoint", "delete", Integer.parseInt(id), "");
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SESSION)){
            String username = "";
            for(HttpSession httpSession: httpSessionConfig.getActiveSessions()){
                if(httpSession.getId().equalsIgnoreCase(id)){
                    User user1 = (User) httpSession.getAttribute(Constants.USER);
                    username = user1.getUsername();
                    httpSession.invalidate();
                    break;
                }
            }
            log(null, "", "destroy", null, username+" istifadəçisinin sessiyası destroy edildi");
        }
        return "redirect:/"+parent+"/"+path;
    }
}
