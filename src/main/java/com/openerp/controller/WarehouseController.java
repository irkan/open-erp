package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.*;
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
                model.addAttribute(Constants.FILTER, new Inventory(!canViewAll()?getSessionOrganization():null));
            }
            model.addAttribute(Constants.LIST, inventoryService.findAll((Inventory) model.asMap().get(Constants.FILTER), PageRequest.of(0, 100, Sort.by("id").descending())));
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACTION)) {
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch"));
            model.addAttribute(Constants.EMPLOYEES, employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization(getSessionOrganization()));

            List<Action> actions;
            if(!data.equals(Optional.empty())){
                if(canViewAll()){
                    actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_Active(Integer.parseInt(data.get()), true);
                } else {
                    actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndOrganization(Integer.parseInt(data.get()), true, getSessionOrganization());
                    actions.addAll(actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndFromOrganization(Integer.parseInt(data.get()), true, getSessionOrganization()));
                }
            } else {
                if(canViewAll()){
                    actions = actionRepository.getActionsByActiveTrueAndInventory_Active(true);
                } else {
                    actions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndOrganization(true, getSessionOrganization());
                    actions.addAll(actionRepository.getActionsByActiveTrueAndInventory_ActiveAndFromOrganizationAndApproveFalse(true, getSessionOrganization()));
                }
            }
            model.addAttribute(Constants.LIST, actions);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Action());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.SUPPLIER)) {
            model.addAttribute(Constants.CITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("city"));
            model.addAttribute(Constants.NATIONALITIES, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("nationality"));
            model.addAttribute(Constants.GENDERS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("gender"));
            model.addAttribute(Constants.LIST, supplierRepository.getSuppliersByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Supplier());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.CONSOLIDATE)) {
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Action(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Action(
                        !canViewAll()?getSessionUser().getEmployee():null,
                        dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("consolidate", "action")
                        )
                );
            }
            model.addAttribute(Constants.LIST, actionService.findAll((Action) model.asMap().get(Constants.FILTER), PageRequest.of(0, 100, Sort.by("id").descending())));
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
            log("warehouse_inventory", "create/edit", inventory.getId(), inventory.toString());
            Action action = inventory.getActions().get(0);
            action.setInventory(inventory);
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"));
            action.setOrganization(inventory.getOrganization());
            actionRepository.save(action);
            log("warehouse_action", "create/edit", action.getId(), action.toString());
            String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getOrganization().getName()+", "+inventory.getName()+", Say: " + action.getAmount() + " ədəd";
            Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(action.getOrganization().getId());
            Transaction transaction = new Transaction(Util.getUserBranch(organization), inventory, action.getAction(), description, false, null);
            transaction.setAmount(inventory.getActions().get(0).getAmount());
            transactionRepository.save(transaction);
            log("accounting_transaction", "create/edit", transaction.getId(), transaction.toString());
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
            log("warehouse_supplier", "create/edit", supplier.getId(), supplier.toString());
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
            redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, new Response(Constants.STATUS.ERROR, messages));
            if(messages.size()==0){
                Action actn = actionRepository.getActionById(action.getId());
                actn.setAmount(actn.getAmount()-action.getAmount());
                actionRepository.save(actn);
                log("warehouse_action", "create/edit", actn.getId(), actn.toString());

                Action sendAction = new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("send", "action"),
                        actn.getOrganization(),
                        action.getAmount(),
                        action.getInventory(),
                        action.getSupplier(),
                        false);
                sendAction.setFromOrganization(action.getOrganization());
                actionRepository.save(sendAction);
                log("warehouse_action", "create/edit", sendAction.getId(), sendAction.toString());
            }
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+action.getInventory().getId());
    }

    @PostMapping(value = "/action/approve")
    public String postActionApprove(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            Response response = null;
            action = actionRepository.getActionById(action.getId());
            if (action.getFromOrganization().getId() != getSessionOrganization().getId()){
                List<String> messages = new ArrayList<>();
                messages.add("Təsdiqləmə əməliyyatı " + action.getFromOrganization().getName() + " tərəfindən edilməlidir!");
                response = new Response(Constants.STATUS.ERROR, messages);
                redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, response);
            }
            if(response==null){
                if(action.getAction().getAttr1().equalsIgnoreCase("send")){
                    Action acceptAction = new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("accept", "action"),
                            action.getFromOrganization(),
                            action.getAmount(),
                            action.getInventory(),
                            action.getSupplier(),
                            true);
                    acceptAction.setFromOrganization(action.getOrganization());

                    actionRepository.save(acceptAction);
                    log("warehouse_action", "create/edit", acceptAction.getId(), acceptAction.toString());

                    String description = acceptAction.getAction().getName()+", "+acceptAction.getSupplier().getName()+" -> "+acceptAction.getOrganization().getName()+", "+acceptAction.getInventory().getName()+", Say: " + acceptAction.getAmount() + " ədəd";
                    Transaction transaction = new Transaction(acceptAction.getOrganization(), acceptAction.getInventory(), acceptAction.getAction(), description, false, null);
                    Financing financing = financingRepository.getFinancingByActiveTrueAndInventoryAndOrganization(action.getInventory(), action.getOrganization());
                    transaction.setAmount(acceptAction.getAmount());
                    transaction.setPrice(financing.getPrice());
                    transaction.setCurrency(financing.getCurrency());
                    transaction.setRate(1d);
                    transactionRepository.save(transaction);
                    log("accounting_transaction", "create/edit", transaction.getId(), transaction.toString());
                    balance(transaction);
                }
                action.setApprove(true);
                action.setApproveDate(new Date());
                actionRepository.save(action);
                log("warehouse_action", "create/edit", action.getId(), action.toString());
            }
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
            action.setInventory(actn.getInventory());
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("consolidate", "action"));
            action.setSupplier(actn.getSupplier());
            action.setOrganization(actn.getOrganization());
            actionRepository.save(action);
            log("warehouse_action", "create/edit", action.getId(), action.toString());
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
            log("warehouse_action", "create/edit", actn.getId(), actn.toString());
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+actn.getInventory().getId());
    }

    @PostMapping(value = "/consolidate/filter")
    public String postConsolidateFilter(@ModelAttribute(Constants.FILTER) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(action, binding, redirectAttributes, "/warehouse/consolidate");
    }

    @PostMapping(value = "/action/return")
    public String postActionReturn(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
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
            log("warehouse_action", "create/edit", action.getId(), action.toString());
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
            log("warehouse_action", "create/edit", actn.getId(), actn.toString());
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
            log("warehouse_action", "create/edit", action.getId(), action.toString());
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
            log("warehouse_action", "create/edit", actn.getId(), actn.toString());
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/consolidate");
    }

    @ResponseBody
    @GetMapping(value = "/inventory/{barcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Inventory findInventory(@PathVariable("barcode") String barcode){
        List<Action> actions = actionRepository.getActionsByActiveTrueAndInventory_BarcodeAndEmployeeAndInventory_ActiveAndAction_Attr1AndAmountGreaterThan(barcode, getSessionUser().getEmployee(), true, "consolidate", 0);
        if(actions.size()>0){
            return actions.get(0).getInventory();
        }
        return null;
    }
}