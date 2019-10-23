package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.Util;
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
                inventories = inventoryRepository.getInventoriesByActiveTrue();
            } else {
                inventories = inventoryRepository.getInventoriesByActiveTrueAndAction_Warehouse_Id(
                        Util.findWarehouse(organizationRepository.getOrganizationsByActiveTrueAndOrganization(getSessionUser().getEmployee().getOrganization())).getId()
                );
            }
            model.addAttribute(Constants.LIST, inventories);
            model.addAttribute(Constants.SUPPLIERS, supplierRepository.getSuppliersByActiveTrue());
            model.addAttribute(Constants.INVENTORY_GROUPS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("inventory-group"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Inventory(new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrue("buy"),
                        Util.findWarehouse(organizationRepository.getOrganizationsByActiveTrueAndOrganization(getSessionUser().getEmployee().getOrganization())))));
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACTION)) {
            model.addAttribute(Constants.WAREHOUSES, organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("warehouse"));
            List<Action> actions = null;
            if(!data.equals(Optional.empty())){
                if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                    actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_Active(Integer.parseInt(data.get()), true);
                } else {
                    actions = actionRepository.getActionsByActiveTrueAndInventory_IdAndInventory_ActiveAndWarehouse(Integer.parseInt(data.get()), true, Util.findWarehouse(organizationRepository.getOrganizationsByActiveTrueAndOrganization(getSessionUser().getEmployee().getOrganization())));
                }
            } else {
                if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                    actions = actionRepository.getActionsByActiveTrueAndInventory_Active(true);
                } else {
                    actions = actionRepository.getActionsByActiveTrueAndInventory_ActiveAndWarehouse(true, Util.findWarehouse(organizationRepository.getOrganizationsByActiveTrueAndOrganization(getSessionUser().getEmployee().getOrganization())));
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
            String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getWarehouse().getName()+", "+inventory.getName()+", Say: " + action.getAmount() + " ədəd";
            Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(action.getWarehouse().getId());
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
    public String postActionTransfer(@ModelAttribute(Constants.FORM) @Validated Action action, @RequestParam(name="fromWarehouse", defaultValue = "0") int fromWarehouseId, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            List<String> messages = new ArrayList<>();
            if(financingRepository.getFinancingByActiveTrueAndInventory(action.getInventory())==null){
                messages.add("Maliyyətləndirmə edilməyib! Alış və qiymətləndirilmə təsdiqlənməlidir!");
            }
            if(fromWarehouseId==action.getWarehouse().getId()){
                messages.add(action.getWarehouse().getName() + " - özündən özünə Göndərmə əməliyyatı edilə bilməz!");
            }
            redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, new Response(Constants.STATUS.ERROR, messages));
            if(messages.size()==0){
                Action actn = actionRepository.getActionById(action.getId());
                actn.setAmount(actn.getAmount()-action.getAmount());
                actionRepository.save(actn);

                Action sendAction = new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrue("send"),
                        action.getWarehouse(),
                        action.getAmount(),
                        action.getInventory(),
                        action.getSupplier(),
                        false);
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
            if (Util.getUserBranch(action.getWarehouse()).getId() != Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId()){
                List<String> messages = new ArrayList<>();
                messages.add("Təsdiqləmə əməliyyatı " + Util.getUserBranch(action.getWarehouse()).getName() + " tərəfindən edilməlidir!");
                response = new Response(Constants.STATUS.ERROR, messages);
                redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, response);
            }
            if(response==null){
                String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getWarehouse().getName()+", "+action.getInventory().getName()+", Say: " + action.getAmount() + " ədəd";
                Organization organization = organizationRepository.getOrganizationByIdAndActiveTrue(action.getWarehouse().getId());
                Transaction transaction = new Transaction(Util.getUserBranch(organization), action.getInventory(), action.getAction(), description, false, null);
                Financing financing = financingRepository.getFinancingByActiveTrueAndInventory(action.getInventory());
                transaction.setAmount(action.getAmount());
                transaction.setPrice(financing.getPrice());
                transaction.setCurrency(financing.getCurrency());
                transaction.setRate(1d);
                transactionRepository.save(transaction);
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
        if(financingRepository.getFinancingByActiveTrueAndInventory(actn.getInventory())==null){
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
            action.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrue("consolidate"));
            action.setSupplier(actn.getSupplier());
            action.setWarehouse(actn.getWarehouse());
            actionRepository.save(action);
            actn.setAmount(actn.getAmount() - action.getAmount());
            actionRepository.save(actn);
        }
        return mapPost(action, binding, redirectAttributes, "/warehouse/action/"+actn.getInventory().getId());
    }
}
