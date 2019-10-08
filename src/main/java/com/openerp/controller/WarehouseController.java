package com.openerp.controller;

import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.Util;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
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
            model.addAttribute(Constants.LIST, inventoryRepository.findAll());
            model.addAttribute(Constants.SUPPLIERS, supplierRepository.getSuppliersByActiveTrue());
            model.addAttribute(Constants.INVENTORY_GROUPS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("inventory-group"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Inventory(new Action(dictionaryRepository.getDictionaryByAttr1AndActiveTrue("buy"),
                        Util.findWarehouse(organizationRepository.getOrganizationsByActiveTrueAndOrganization(getSessionUser().getEmployee().getOrganization())))));
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACTION)) {
            int id = Integer.parseInt(data.get());
            model.addAttribute(Constants.LIST, actionRepository.getActionsByActiveTrueAndInventory_Id(id));
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
        if(!binding.hasErrors()){
            if(inventory.getGroup().getId()!=null && (inventory.getBarcode()==null || inventory.getBarcode().trim().length()==0)){
                inventory.setBarcode(Util.generateBarcode(inventory.getGroup().getId()));
            }
            inventoryRepository.save(inventory);
            Action action = inventory.getAction();
            action.setInventory(inventory);
            actionRepository.save(action);
            String description = action.getAction().getName()+", "+action.getSupplier().getName()+" -> "+action.getWarehouse().getName()+", "+inventory.getName()+", Say: " + action.getAmount() + " ədəd";
            Transaction transaction = new Transaction(action, action.getAction(), description, false);
            transaction.setAmount(inventory.getAction().getAmount());
            transactionRepository.save(transaction);
        }
        return mapPost(inventory, binding, redirectAttributes);
    }

    @PostMapping(value = "/supplier")
    public String postSupplier(@ModelAttribute(Constants.FORM) @Validated Supplier supplier, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            supplierRepository.save(supplier);
        }
        return mapPost(supplier, binding, redirectAttributes);
    }
}
