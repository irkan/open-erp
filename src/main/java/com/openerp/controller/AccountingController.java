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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/accounting")
public class AccountingController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
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

        if (page.equalsIgnoreCase(Constants.ROUTE.TRANSACTION)) {
            model.addAttribute(Constants.CURRENCIES,  dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("currency"));
            model.addAttribute(Constants.ACCOUNTS,
                    accountRepository.getAccountsByActiveTrueAndOrganization(
                            Util.getUserBranch(getSessionUser().getEmployee().getOrganization())));
            model.addAttribute(Constants.LIST, transactionRepository.findAll());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Transaction());
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACCOUNT)) {
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrueAndOrganizationType_Attr1("branch"));
            model.addAttribute(Constants.CURRENCIES,  dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("currency"));
            model.addAttribute(Constants.LIST, accountRepository.getAccountsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Account());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/account")
    public String postAccount(@ModelAttribute(Constants.FORM) @Validated Account account, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            accountRepository.save(account);
        }
        return mapPost(account, binding, redirectAttributes);
    }

    @PostMapping(value = "/transaction")
    public String postTransaction(@ModelAttribute(Constants.FORM) @Validated Transaction transaction, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            transactionRepository.save(transaction);
        }
        return mapPost(transaction, binding, redirectAttributes);
    }

    @PostMapping(value = "/transaction-approve")
    public String postTransactionApprove(@ModelAttribute(Constants.FORM) @Validated Transaction transaction, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        if(!binding.hasErrors()){
            Transaction trn = transactionRepository.getTransactionById(transaction.getId());
            trn.setApprove(true);
            trn.setApproveDate(new Date());
            trn.setPrice(transaction.getPrice());
            trn.setCurrency(transaction.getCurrency());
            trn.setRate(getRate(transaction.getCurrency()));
            double sumPrice = trn.getAmount()*transaction.getPrice()*trn.getRate();
            trn.setSumPrice(sumPrice);
            trn.setAccount(transaction.getAccount());
            transactionRepository.save(trn);
        }
        return mapPost(transaction, binding, redirectAttributes, "/accounting/transaction");
    }
}
