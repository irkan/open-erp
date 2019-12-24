package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/delete")
public class DeleteController extends SkeletonController {

    @PostMapping(value = "/{path}")
    public String getSubModules(RedirectAttributes redirectAttributes, @PathVariable("path") String path, @RequestParam(name="deletedId", defaultValue = "0") String id) throws Exception {
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
            log("admin_dictionary_type", "delete", dictionaryType.getId(), dictionaryType.toString());
            if(dictionaryType!=null){
                for(Dictionary dictionary: dictionaryRepository.getDictionariesByDictionaryType_Id(dictionaryType.getId())){
                    dictionary.setActive(false);
                    dictionaryRepository.save(dictionary);
                    log("admin_dictionary_type", "delete", dictionary.getId(), dictionary.toString());
                }
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.DICTIONARY)){
            Dictionary dictionary = dictionaryRepository.getDictionaryById(Integer.parseInt(id));
            dictionary.setActive(false);
            dictionaryRepository.save(dictionary);
            log("admin_dictionary", "delete", dictionary.getId(), dictionary.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.MODULE)){
            Module module = moduleRepository.getModuleById(Integer.parseInt(id));
            module.setActive(false);
            moduleRepository.save(module);
            log("admin_module", "delete", module.getId(), module.toString());
            for(ModuleOperation moduleOperation: moduleOperationRepository.getModuleOperationsByModule_Active(false)){
                userModuleOperationRepository.deleteInBatch(userModuleOperationRepository.getUserModuleOperationsByModuleOperation_Id(moduleOperation.getId()));
                log("admin_user_module_operation", "delete-in-batch", moduleOperation.getId(), moduleOperation.toString());
                moduleOperationRepository.deleteById(moduleOperation.getId());
                log("admin_module_operation", "delete", moduleOperation.getId(), moduleOperation.toString());
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.OPERATION)){
            Operation operation = operationRepository.getOperationById(Integer.parseInt(id));
            operation.setActive(false);
            operationRepository.save(operation);
            log("admin_operation", "delete", operation.getId(), operation.toString());
            for(ModuleOperation moduleOperation: moduleOperationRepository.getModuleOperationsByOperation_Active(false)){
                userModuleOperationRepository.deleteInBatch(userModuleOperationRepository.getUserModuleOperationsByModuleOperation_Id(moduleOperation.getId()));
                log("admin_operation", "delete", operation.getId(), operation.toString());
                moduleOperationRepository.deleteById(moduleOperation.getId());
                log("admin_module_operation", "delete", moduleOperation.getId(), moduleOperation.toString());
            }
        } else if(path.equalsIgnoreCase(Constants.ROUTE.MODULE_OPERATION)){
            moduleOperationRepository.deleteById(Integer.parseInt(id));
            //sora buna baxariq
            // log("admin_module_operation", "delete", moduleOperation.getId(), moduleOperation.toString())
        } else if(path.equalsIgnoreCase(Constants.ROUTE.USER_MODULE_OPERATION)){
            User userObject = userRepository.getUserByActiveTrueAndId(Integer.parseInt(id));
            userRepository.save(userObject);
            log("admin_user", "delete", userObject.getId(), userObject.toString());
            List<UserModuleOperation> userModuleOperations = userModuleOperationRepository.getUserModuleOperationsByUser_IdAndUser_Active(user.getId(), true);
            userModuleOperationRepository.deleteInBatch(userModuleOperations);
            //Listin icinde nie metod hell etmek olmur
            //log("admin_user_module_operation", "delete-in-batch", userModuleOperations.getId(), userModuleOperations.toString());
        } else if(path.equalsIgnoreCase(Constants.ROUTE.EMPLOYEE)){
            employeeRepository.deleteById(Integer.parseInt(id));
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ORGANIZATION)){
            organizationRepository.deleteById(Integer.parseInt(id));
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SUPPLIER)){
            Supplier supplier = supplierRepository.getSuppliersById(Integer.parseInt(id));
            supplier.setActive(false);
            supplierRepository.save(supplier);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ACCOUNT)){
            Account account = accountRepository.getAccountById(Integer.parseInt(id));
            account.setActive(false);
            accountRepository.save(account);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.NON_WORKING_DAY)){
            NonWorkingDay nonWorkingDay = nonWorkingDayRepository.getNonWorkingDayById(Integer.parseInt(id));
            nonWorkingDay.setActive(false);
            nonWorkingDayRepository.save(nonWorkingDay);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SHORTENED_WORKING_DAY)){
            ShortenedWorkingDay shortenedWorkingDay = shortenedWorkingDayRepository.getShortenedWorkingDayById(Integer.parseInt(id));
            shortenedWorkingDay.setActive(false);
            shortenedWorkingDayRepository.save(shortenedWorkingDay);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.PAYROLL_CONFIGURATION)){
            PayrollConfiguration payrollConfiguration = payrollConfigurationRepository.getPayrollConfigurationById(Integer.parseInt(id));
            payrollConfiguration.setActive(false);
            payrollConfigurationRepository.save(payrollConfiguration);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.VACATION)){
            Vacation vacation = vacationRepository.getVacationById(Integer.parseInt(id));
            vacation.setActive(false);
            vacationRepository.save(vacation);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.WORKING_HOUR_RECORD)){
            WorkingHourRecord workingHourRecord = workingHourRecordRepository.getWorkingHourRecordById(Integer.parseInt(id));
            workingHourRecord.setActive(false);
            workingHourRecordRepository.save(workingHourRecord);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.SALARY)){
            Salary salary = salaryRepository.getSalaryById(Integer.parseInt(id));
            salary.setActive(false);
            salaryRepository.save(salary);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ADVANCE)){
            Advance advance = advanceRepository.getAdvanceById(Integer.parseInt(id));
            advance.setActive(false);
            advanceRepository.save(advance);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.INVENTORY)){
            Inventory inventory = inventoryRepository.getInventoryById(Integer.parseInt(id));
            inventory.setActive(false);
            inventoryRepository.save(inventory);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ACTION)){
            Action action = actionRepository.getActionById(Integer.parseInt(id));
            action.setActive(false);
            actionRepository.save(action);
            List<Action> actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndActionOrderByIdDesc(action.getInventory().getId(), true, dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"));
            if(actions.size()>0){
                Action returnAction = actions.get(0);
                returnAction.setAmount(returnAction.getAmount()+action.getAmount());
                actionRepository.save(returnAction);
            }
            return "redirect:/"+parent+"/"+path+"/"+action.getInventory().getId();
        } else if(path.equalsIgnoreCase(Constants.ROUTE.ITEM)){
            Item item = itemRepository.getItemById(Integer.parseInt(id));
            item.setActive(false);
            itemRepository.save(item);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.PAYMENT_REGULATOR_NOTE)){
            PaymentRegulatorNote paymentRegulatorNote = paymentRegulatorNoteRepository.getPaymentRegulatorNoteById(Integer.parseInt(id));
            paymentRegulatorNote.setActive(false);
            paymentRegulatorNoteRepository.save(paymentRegulatorNote);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.CUSTOMER)){
            Customer customer = customerRepository.getCustomerByIdAndActiveTrue(Integer.parseInt(id));
            customer.setActive(false);
            customerRepository.save(customer);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.CONFIGURATION)){
            Configuration configuration = configurationRepository.getConfigurationById(Integer.parseInt(id));
            configuration.setActive(false);
            configurationRepository.save(configuration);
        } else if(path.equalsIgnoreCase(Constants.ROUTE.NOTIFICATION)){
            notificationRepository.delete(notificationRepository.getNotificationById(Integer.parseInt(id)));
        } else if(path.equalsIgnoreCase(Constants.ROUTE.FINANCING)){
            Financing financing = financingRepository.getFinancingById(Integer.parseInt(id));
            financing.setActive(false);
            financingRepository.save(financing);
        }
        return "redirect:/"+parent+"/"+path;
    }
}
