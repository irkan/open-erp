package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.*;
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
        session.setAttribute(Constants.PAGE, page);
        String description = "";
        List<Module> moduleList = (List<Module>) session.getAttribute(Constants.MODULES);
        for (Module m : moduleList) {
            if (m.getPath().equalsIgnoreCase(page)) {
                description = m.getDescription();
                break;
            }
        }
        session.setAttribute(Constants.MODULE_DESCRIPTION, description);

        if (page.equalsIgnoreCase(Constants.ROUTE.INVENTORY)) {
            List<Inventory> inventories = null;
            if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                inventories = inventoryRepository.getInventoriesByActiveTrueOrderByIdDesc();
            } else {
                inventories = inventoryRepository.getInventoriesByActiveTrueAndAction_Organization_IdOrderByIdDesc(
                        getSessionUser().getEmployee().getOrganization().getId()
                );
            }
            model.addAttribute(Constants.LIST, inventories);
            model.addAttribute(Constants.SUPPLIERS, supplierRepository.getSuppliersByActiveTrue());
            model.addAttribute(Constants.INVENTORY_GROUPS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("inventory-group"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Inventory(new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("buy", "action"),
                        getSessionUser().getEmployee().getOrganization())));
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACTION)) {
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch"));
            List<Action> actions = null;
            if(!data.equals(Optional.empty())){
                if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                    actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_Active(Integer.parseInt(data.get()), true);
                } else {
                    actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndOrganization(Integer.parseInt(data.get()), true, getSessionUser().getEmployee().getOrganization());
                }
            } else {
                if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                    actions = actionRepository.getActionsByActiveTrueAndInventory_Active(true);
                } else {
                    actions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndOrganization(true, getSessionUser().getEmployee().getOrganization());
                }
            }

            List<Employee> employees;
            if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                employees = employeeRepository.getEmployeesByContractEndDateIsNullAndOrganization_Id(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId());
            } else {
                employees = employeeRepository.getEmployeesByContractEndDateIsNull();
            }
            model.addAttribute(Constants.EMPLOYEES, employees);

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
            List<Action> actions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndEmployee_Id(true, getSessionUser().getEmployee().getId());
            model.addAttribute(Constants.LIST, actions);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Action());
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
            Action action = inventory.getAction();
            action.setInventory(inventory);
            actionRepository.save(action);
            String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getOrganization().getName()+", "+inventory.getName()+", Say: " + action.getAmount() + " ədəd";
            Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(action.getOrganization().getId());
            Transaction transaction = new Transaction(Util.getUserBranch(organization), inventory, action.getAction(), description, false, null);
            transaction.setAmount(inventory.getAction().getAmount());
            transactionRepository.save(transaction);
        }
        return mapPost(inventory, binding, redirectAttributes);
    }

    @PostMapping(value = "/supplier")
    public String postSupplier(@ModelAttribute(Constants.FORM) @Validated Supplier supplier, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            supplierRepository.save(supplier);
        }
        return mapPost(supplier, binding, redirectAttributes);
    }

    @PostMapping(value = "/action/transfer")
    public String postActionTransfer(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            List<String> messages = new ArrayList<>();
            if(financingRepository.getFinancingByActiveTrueAndInventoryAndOrganization(action.getInventory(), action.getFromOrganization())==null){
                messages.add("Maliyyətləndirmə edilməyib! Alış və qiymətləndirilmə təsdiqlənməlidir!");
            }
            if(action.getFromOrganization().getId().intValue()==action.getOrganization().getId().intValue()){
                messages.add(action.getOrganization().getName() + " - özündən özünə Göndərmə əməliyyatı edilə bilməz!");
            }
            redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, new Response(Constants.STATUS.ERROR, messages));
            if(messages.size()==0){
                Action actn = actionRepository.getActionById(action.getId());
                actn.setAmount(actn.getAmount()-action.getAmount());
                actionRepository.save(actn);

                Action sendAction = new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("send", "action"),
                        action.getOrganization(),
                        action.getAmount(),
                        action.getInventory(),
                        action.getSupplier(),
                        false);
                sendAction.setFromOrganization(actn.getOrganization());
                actionRepository.save(sendAction);
            }
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+action.getInventory().getId());
    }

    @PostMapping(value = "/action/approve")
    public String postActionApprove(@ModelAttribute(Constants.FORM) @Validated Action action, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            Response response = null;
            action = actionRepository.getActionById(action.getId());
            if (Util.getUserBranch(action.getOrganization()).getId() != Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId()){
                List<String> messages = new ArrayList<>();
                messages.add("Təsdiqləmə əməliyyatı " + Util.getUserBranch(action.getOrganization()).getName() + " tərəfindən edilməlidir!");
                response = new Response(Constants.STATUS.ERROR, messages);
                redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, response);
            }
            if(response==null){
                if(action.getAction().getAttr1().equalsIgnoreCase("send")){
                    String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getOrganization().getName()+", "+action.getInventory().getName()+", Say: " + action.getAmount() + " ədəd";
                    Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(action.getOrganization().getId());
                    Transaction transaction = new Transaction(Util.getUserBranch(organization), action.getInventory(), action.getAction(), description, false, null);
                    Financing financing = financingRepository.getFinancingByActiveTrueAndInventoryAndOrganization(action.getInventory(), action.getFromOrganization());
                    transaction.setAmount(action.getAmount());
                    transaction.setPrice(financing.getPrice());
                    transaction.setCurrency(financing.getCurrency());
                    transaction.setRate(1d);
                    transactionRepository.save(transaction);
                }
                action.setApprove(!action.getApprove());
                action.setApproveDate(new Date());
                actionRepository.save(action);
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
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+actn.getInventory().getId());
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
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
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
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/consolidate");
    }

    @ResponseBody
    @GetMapping(value = "/inventory/{barcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Inventory findInventory(@PathVariable("barcode") String barcode){
        return inventoryRepository.getInventoryByBarcodeAndActiveTrue(barcode);
    }
}
