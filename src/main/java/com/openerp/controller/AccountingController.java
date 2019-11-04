package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.repository.ActionRepository;
import com.openerp.repository.InventoryRepository;
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
            model.addAttribute(Constants.EXPENSES, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("expense", "action"));

            List<Transaction> transactions = null;
            if(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getOrganization()==null){
                transactions = transactionRepository.getTransactionsByOrderByApproveDescCreatedDateDesc();
            } else {
                transactions = transactionRepository.getTransactionsByBranchOrderByApproveDescCreatedDateDesc(Util.getUserBranch(getSessionUser().getEmployee().getOrganization()));
            }
            model.addAttribute(Constants.LIST, transactions);
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
        } else if (page.equalsIgnoreCase(Constants.ROUTE.FINANCING)) {
            model.addAttribute(Constants.LIST, financingRepository.getFinancingsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Financing());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/account")
    public String postAccount(@ModelAttribute(Constants.FORM) @Validated Account account, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            accountRepository.save(account);
        }
        return mapPost(account, binding, redirectAttributes);
    }

    @PostMapping(value = "/transaction")
    public String postTransaction(@ModelAttribute(Constants.FORM) @Validated Transaction transaction, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            transactionRepository.save(transaction);
        }
        return mapPost(transaction, binding, redirectAttributes);
    }

    @PostMapping(value = "/transaction/approve")
    public String postTransactionApprove(@ModelAttribute(Constants.FORM) @Validated Transaction transaction, BindingResult binding, RedirectAttributes redirectAttributes, @RequestParam(name = "expense", required = false) int[] expenses) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            Transaction trn = transactionRepository.getTransactionById(transaction.getId());
            if (trn.getBranch().getId() == Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId()){ trn.setApprove(true);
                trn.setApproveDate(new Date());
                trn.setPrice(transaction.getPrice());
                trn.setCurrency(transaction.getCurrency());
                trn.setRate(getRate(transaction.getCurrency()));
                double sumPrice = trn.getAmount() * transaction.getPrice() * trn.getRate();
                trn.setSumPrice(sumPrice);
                trn.setAccount(transaction.getAccount());
                transactionRepository.save(trn);

                if (expenses != null) {
                    for (int expense : expenses) {
                        Dictionary action = dictionaryRepository.getDictionaryById(expense);
                        String description = action.getName() + ", " + trn.getDescription();
                        Transaction transaction1 = new Transaction(trn.getBranch(), trn.getInventory(), action, description, false, trn);
                        transactionRepository.save(transaction1);
                    }
                }

                Financing financing = financingRepository.getFinancingByActiveTrueAndInventory(trn.getInventory());

                Double financingPrice = calculateFinancing(trn, inventoryRepository);
                if (financing != null) {
                    financing.setPrice(financingPrice);
                } else {
                    financing = new Financing(trn.getInventory(), financingPrice);
                }
                financingRepository.save(financing);
            } else {
                List<String> messages = new ArrayList<>();
                messages.add("Təsdiqləmə əməliyyatı " + Util.getUserBranch(trn.getBranch()).getName() + " tərəfindən edilməlidir!");
                redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, new Response(Constants.STATUS.ERROR, messages));
            }
        }
        return mapPost(transaction, binding, redirectAttributes, "/accounting/transaction");
    }

    private static Double calculateFinancing(Transaction transaction, InventoryRepository inventoryRepository){
        Inventory inventory = inventoryRepository.getInventoryById(transaction.getInventory().getId());
        int amount=0;
        for(Action action: inventory.getActions()){
            amount+=action.getAmount();
        }

        Transaction parent = transaction.getTransaction()==null?transaction:transaction.getTransaction();
        double sumPrice = parent.getSumPrice();
        for(Transaction trn: parent.getChildren()){
            sumPrice+=trn.getPrice();
        }
        return Double.parseDouble(Util.format(sumPrice/amount));
    }
}
