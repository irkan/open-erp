package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        if (page.equalsIgnoreCase(Constants.ROUTE.INVENTORY)) {
            model.addAttribute(Constants.SUPPLIERS, supplierRepository.getSuppliersByActiveTrue());
            model.addAttribute(Constants.INVENTORY_GROUPS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("inventory-group"));

            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Inventory(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Inventory(!canViewAll()?getSessionOrganization():null, null));
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Inventory){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<Inventory> inventories = inventoryService.findAll((Inventory) model.asMap().get(Constants.FILTER), PageRequest.of(0, 3*paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, inventories);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(inventories, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACTION)) {
            model.addAttribute(Constants.SUPPLIERS, supplierRepository.getSuppliersByActiveTrue());
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrueAndType_Attr1("branch"));
            model.addAttribute(Constants.ACTIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("action"));
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));

            if(!model.containsAttribute(Constants.FORM)){
                Dictionary buy = dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action");
                Inventory inventory = new Inventory(!data.equals(Optional.empty())?Integer.parseInt(data.get()):null, true);
                model.addAttribute(Constants.FORM, new Action(buy, inventory, getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Action(
                                new Inventory(!data.equals(Optional.empty())?Integer.parseInt(data.get()):null, true),
                                !canViewAll()?getSessionOrganization():null
                        )
                );
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Action){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }

            Page<Action> actions = actionService.findAll((Action) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            Page<Action> pageData;
            if(!data.equals(Optional.empty())){
                List<Action> actionList = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndFromOrganization(Integer.parseInt(data.get()), true, getSessionOrganization());
                actionList.addAll(actions.getContent());
                pageData = new PageImpl<Action>(actionList);
            } else {
                List<Action> actionList = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndFromOrganizationAndApproveFalse(true, getSessionOrganization());
                actionList.addAll(actions.getContent());
                pageData = new PageImpl<Action>(actionList);
            }
            model.addAttribute(Constants.LIST, pageData);
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SUPPLIER)) {
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.NATIONALITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality"));
            model.addAttribute(Constants.GENDERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender"));
            model.addAttribute(Constants.LIST, supplierRepository.getSuppliersByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Supplier());
            }
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(supplierRepository.findAll(), redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.CONSOLIDATE)) {
            List<Employee> employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganizationAndActiveTrue(getSessionOrganization());
            List<Dictionary> positions = dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("position");
            model.addAttribute(Constants.EMPLOYEES, Util.convertedEmployeesByPosition(employees, positions));

            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Action(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                Action action = new Action(
                        getSessionUser().getEmployee(),
                        dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("consolidate", "action"),
                        null,
                        1,
                        !canViewAll()?getSessionOrganization():null
                );
                model.addAttribute(Constants.FILTER, action);
            }
            if(session.getAttribute(Constants.SESSION_FILTER)!=null &&
                    session.getAttribute(Constants.SESSION_FILTER) instanceof Action){
                model.addAttribute(Constants.FILTER, session.getAttribute(Constants.SESSION_FILTER));
            }
            Page<Action> actions = actionService.findAll((Action) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, actions);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(actions, redirectAttributes, page);
            }
        }
        return "layout";
    }

    @PostMapping(value = "/inventory")
    public String postInventory(@ModelAttribute(Constants.FORM) @Validated Inventory inventory, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            if(inventory.getGroup().getId()!=null && (inventory.getBarcode()==null || inventory.getBarcode().trim().length()==0)){
                inventory.setBarcode(Util.generateBarcode(inventory.getGroup().getId()));
            }
            inventoryRepository.save(inventory);
            log(inventory, "inventory", "create/edit", inventory.getId(), inventory.toString());
            Action action = inventory.getActions().get(0);
            action.setInventory(inventory);
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"));
            action.setOrganization(inventory.getOrganization());
            actionRepository.save(action);
            log(action, "action", "create/edit", action.getId(), action.toString());
            String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getOrganization().getName()+", "+inventory.getName()+", Say: " + action.getAmount() + " ədəd";
            Transaction transaction = new Transaction(getSessionOrganization(), inventory, action.getAction(), description, false, null);
            transaction.setAmount(inventory.getActions().get(0).getAmount());
            transactionRepository.save(transaction);
            log(transaction, "transaction", "create/edit", transaction.getId(), transaction.toString());
        }
        return mapPost(inventory, binding, redirectAttributes);
    }

    @PostMapping(value = "/inventory/filter")
    public String postInventoryFilter(@ModelAttribute(Constants.FILTER) @Validated Inventory inventory, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(inventory, binding, redirectAttributes, "/warehouse/inventory");
    }

    @PostMapping(value = "/supplier")
    public String postSupplier(@ModelAttribute(Constants.FORM) @Validated Supplier supplier, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            supplierRepository.save(supplier);
            log(supplier, "supplier", "create/edit", supplier.getId(), supplier.toString());
        }
        return mapPost(supplier, binding, redirectAttributes);
    }

    @PostMapping(value = "/action/transfer")
    public String postActionTransfer(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            List<String> messages = new ArrayList<>();
            List<Transaction> transactions = transactionRepository.getTransactionsByInventoryAndApproveFalseAndOrganization(action.getInventory(), action.getOrganization());
            if(action.getFromOrganization()!=null){
                transactions.addAll(
                        transactionRepository.getTransactionsByInventoryAndApproveFalseAndOrganization(action.getInventory(), action.getFromOrganization())
                );
            }

            if(transactions.size()>0){
                String transactionIds="";
                for(Transaction transaction: transactions){
                    transactionIds += transaction.getId() + ", ";
                }
                messages.add("Əməliyyatın icrası üçün " + transactionIds + " nömrəli tranzaksiyalar təsdiqlənməlidir!");
            }
            if(action.getFromOrganization().getId().intValue()==action.getOrganization().getId().intValue()){
                messages.add(action.getOrganization().getName() + " - özündən özünə Göndərmə əməliyyatı edilə bilməz!");
            }

            Action actn = actionRepository.getActionById(action.getId());
            if(actn.getAmount()-action.getAmount()<0){
                messages.add("Say maksimum "+ actn.getAmount() + " olmalıdır!");
            }

            redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, new Response(Constants.STATUS.ERROR, messages));
            if(messages.size()==0){
                actn.setAmount(actn.getAmount()-action.getAmount());
                actionRepository.save(actn);
                log(actn, "action", "create/edit", actn.getId(), actn.toString(), "Transfer üçün çıxılan say");

                Action sendAction = new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("send", "action"),
                        actn.getOrganization(),
                        action.getAmount(),
                        action.getInventory(),
                        action.getSupplier(),
                        true);
                sendAction.setFromOrganization(action.getOrganization());
                actionRepository.save(sendAction);

                log(sendAction, "action", "create/edit", sendAction.getId(), sendAction.toString(), "Transfer göndərilmə");

                List<Inventory> inventories = inventoryRepository.getInventoriesByActiveTrueAndBarcodeAndOrganizationOrderByIdAsc(actn.getInventory().getBarcode(), action.getOrganization());
                Inventory inventory = new Inventory(inventories.size()>0?inventories.get(0).getId():null);
                inventory.setOrganization(action.getOrganization());
                inventory.setGroup(actn.getInventory().getGroup());
                inventory.setBarcode(actn.getInventory().getBarcode());
                inventory.setName(actn.getInventory().getName());
                inventory.setDescription(actn.getInventory().getDescription());
                inventoryRepository.save(inventory);

                log(inventory, "inventory", "create/edit", inventory.getId(), inventory.toString());

                Action acceptAction = new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("accept", "action"),
                        action.getOrganization(),
                        action.getAmount(),
                        inventory,
                        action.getSupplier(),
                        false);
                acceptAction.setFromOrganization(sendAction.getOrganization());
                actionRepository.save(acceptAction);

                log(acceptAction, "action", "create/edit", acceptAction.getId(), acceptAction.toString(), "Transfer qəbul edilmə");
            }
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+action.getInventory().getId());
    }

    @PostMapping(value = "/action/approve")
    public String postActionApprove(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            Response response = null;
            action = actionRepository.getActionById(action.getId());
            if (action.getOrganization().getId() != getUserOrganization().getId()){
                List<String> messages = new ArrayList<>();
                messages.add("Təsdiqləmə əməliyyatı " + action.getFromOrganization().getName() + " tərəfindən edilməlidir!");
                response = new Response(Constants.STATUS.ERROR, messages);
                redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, response);
            }
            if(response==null){
                if(action.getAction().getAttr1().equalsIgnoreCase("accept")){
                    action.setApprove(true);
                    action.setApproveDate(new Date());

                    actionRepository.save(action);
                    log(action, "action", "create/edit", action.getId(), action.toString());

                    String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getOrganization().getName()+", "+action.getInventory().getName()+", Say: " + action.getAmount() + " ədəd";
                    Transaction transaction = new Transaction(action.getOrganization(), action.getInventory(), action.getAction(), description, false, null);
                    List<Financing> financings = financingRepository.getFinancingByActiveTrueAndInventory_BarcodeAndOrganizationOrderByIdAsc(action.getInventory().getBarcode(), action.getFromOrganization());
                    transaction.setAmount(action.getAmount());
                    transaction.setPrice(financings.size()>0?financings.get(0).getPrice():0);
                    transaction.setCurrency(financings.size()>0?financings.get(0).getCurrency():"AZN");
                    transaction.setRate(1d);
                    transactionRepository.save(transaction);
                    log(transaction, "transaction", "create/edit", transaction.getId(), transaction.toString());
                    balance(transaction);
                }
                action.setApprove(true);
                action.setApproveDate(new Date());
                actionRepository.save(action);
                log(action, "action", "create/edit", action.getId(), action.toString());
            }
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+action.getInventory().getId());
    }

    @PostMapping(value = "/action")
    public String postAction(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            actionRepository.save(action);
            log(action, "action", "create/edit", action.getId(), action.toString(), "Alış əməliyyatı " + action.getAmount() + " ədəd");

            String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getOrganization().getName()+", "+action.getInventory().getName()+", Say: " + action.getAmount() + " ədəd";
            Transaction transaction = new Transaction(action.getOrganization(), action.getInventory(), action.getAction(), description, false, null);
            transaction.setAmount(action.getAmount());
            transactionRepository.save(transaction);
            log(transaction, "transaction", "create/edit", transaction.getId(), transaction.toString());
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+action.getInventory().getId());
    }

    @PostMapping(value = "/action/consolidate")
    public String postActionConsolidate(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Action actn = actionRepository.getActionById(action.getId());
        if(financingRepository.getFinancingByActiveTrueAndInventoryAndOrganization(actn.getInventory(), actn.getOrganization())==null){
            FieldError fieldError = new FieldError("", "", "Maliyyətləndirmə edilməyib! Alış və qiymətləndirilmə təsdiqlənməlidir!");
            binding.addError(fieldError);
        }
        if(actn.getAmount()-action.getAmount()<0){
            FieldError fieldError = new FieldError("amount", "amount", "Say limitini aşmısınız!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            action.setId(null);
            action.setOld(actn.getOld());
            action.setInventory(actn.getInventory());
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("consolidate", "action"));
            action.setSupplier(actn.getSupplier());
            action.setOrganization(actn.getOrganization());
            actionRepository.save(action);
            log(action, "action", "create/edit", action.getId(), action.toString());
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
            log(actn, "action", "create/edit", actn.getId(), actn.toString());
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+actn.getInventory().getId());
    }

    @PostMapping(value = "/consolidate/filter")
    public String postConsolidateFilter(@ModelAttribute(Constants.FILTER) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(action, binding, redirectAttributes, "/warehouse/consolidate");
    }

    @PostMapping(value = "/action/filter")
    public String postActionFilter(@ModelAttribute(Constants.FILTER) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(action, binding, redirectAttributes, "/warehouse/action");
    }

    @PostMapping(value = "/action/return")
    public String postActionReturn(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        boolean status = false;
        Action actn = actionRepository.getActionById(action.getId());
        if(actn.getAmount()-action.getAmount()<0){
            FieldError fieldError = new FieldError("amount", "amount", "Maksimum "+ actn.getAmount() + " ədəd qaytara bilərsiniz!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            List<Action> actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndActionOrderByIdDesc(actn.getInventory().getId(), true, dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"));
            if(actions.size()>0){
                Action buy = actions.get(0);
                buy.setAmount(buy.getAmount()+action.getAmount());
                actionRepository.save(buy);
                log(buy, "action", "create/edit", buy.getId(), buy.toString(), buy.getId() + " nömrəli Alış hərəkətinə əlavə edildi");
                status = true;
            } else {
                actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndActionOrderByIdDesc(actn.getInventory().getId(), true, dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("accept", "action"));
                if(actions.size()>0){
                    Action accept = actions.get(0);
                    accept.setAmount(accept.getAmount()+action.getAmount());
                    actionRepository.save(accept);
                    log(accept, "action", "create/edit", accept.getId(), accept.toString(), accept.getId() + " nömrəli Qəbuledilmə hərəkətinə əlavə edildi");
                    status = true;
                }
            }

            if(status){
                actn.setAmount(actn.getAmount() - action.getAmount());
                actionRepository.save(actn);
                log(actn, "action", "create/edit", actn.getId(), actn.toString(), "Qaytarılma əməliyyatı");
            }
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+actn.getInventory().getId());
    }

    @PostMapping(value = "/action/cancellation")
    public String postActionCancellation(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Action actn = actionRepository.getActionById(action.getId());
        if(actn.getAmount()-action.getAmount()<0){
            FieldError fieldError = new FieldError("amount", "amount", "Maksimum "+ actn.getAmount() + " ədəd silə bilərsiniz!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors() && actn!=null) {
            action.setId(null);
            action.setInventory(actn.getInventory());
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("cancellation", "action"));
            action.setSupplier(actn.getSupplier());
            action.setOrganization(actn.getOrganization());
            action.setApprove(true);
            action.setApproveDate(new Date());
            actionRepository.save(action);
            log(action, "action", "create/edit", action.getId(), action.toString());
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
            log(actn, "action", "cancellation", actn.getId(), actn.toString());
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+actn.getInventory().getId());
    }

    @PostMapping(value = "/consolidate/return")
    public String postConsolidateReturn(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Action actn = actionRepository.getActionById(action.getId());
        if(actn.getAmount()-action.getAmount()<0){
            FieldError fieldError = new FieldError("amount", "amount", "Maksimum "+ actn.getAmount() + " ədəd qaytara bilərsiniz!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding, Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            action.setId(null);
            action.setInventory(actn.getInventory());
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("return", "action"));
            action.setSupplier(actn.getSupplier());
            action.setOrganization(actn.getOrganization());
            action.setEmployee(actn.getEmployee());
            action.setApprove(false);
            actionRepository.save(action);
            log(action, "action", "create/edit", action.getId(), action.toString());
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
            log(actn, "action", "create/edit", actn.getId(), actn.toString());
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/consolidate");
    }

    @ResponseBody
    @GetMapping(value = "/api/inventory/{barcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Inventory findInventory(@PathVariable("barcode") String barcode){
        try {
            List<Inventory> inventories = inventoryRepository.getInventoriesByActiveTrueAndBarcodeAndOrganizationOrderByIdAsc(barcode, getSessionOrganization());
            for(Inventory inventory: inventories){
                int amount = Util.calculateInventoryAmount(inventory.getActions(), getSessionOrganization().getId()).getAllItemsCount();
                if(amount>0){
                    return inventory;
                }
            }
        } catch (Exception e){
            log(null, "error", "inventory", "", null, "", "WAREHOUSE API INVENTORY Xəta baş verdi! " + e.getMessage());
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }
}