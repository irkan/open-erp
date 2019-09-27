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

@Controller
@RequestMapping("/accounting")
public class AcoountingController extends SkeletonController {

    @GetMapping(value = "/{page}")
    public String route(Model model, @PathVariable("page") String page, RedirectAttributes redirectAttributes) throws Exception {
        session.setAttribute(Constants.PAGE, page);
        String description = "";
        List<Module> moduleList = (List<Module>) session.getAttribute(Constants.MODULES);
        for(Module m: moduleList){
            if(m.getPath().equalsIgnoreCase(page)){
                description = m.getDescription();
                break;
            }
        }
        session.setAttribute(Constants.MODULE_DESCRIPTION, description);

        if (page.equalsIgnoreCase(Constants.ROUTE.EXPENSE)) {
            model.addAttribute(Constants.LIST, expenseRepository.findAll());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Expense());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/expense")
    public String postExpense(@ModelAttribute(Constants.FORM) @Validated Expense expense, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            expenseRepository.save(expense);
        }
        return mapPost(expense, binding, redirectAttributes);
    }
}
