package com.openerp.controller;

import com.openerp.domain.Response;
import com.openerp.entity.*;
import com.openerp.repository.ActionRepository;
import com.openerp.repository.InventoryRepository;
import com.openerp.repository.TransactionRepository;
import com.openerp.util.Constants;
import com.openerp.util.Util;
import org.springframework.data.domain.Page;
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
@RequestMapping("/accounting")
public class AccountingController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        if (page.equalsIgnoreCase(Constants.ROUTE.TRANSACTION)) {
            model.addAttribute(Constants.CURRENCIES,  dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("currency"));
            model.addAttribute(Constants.ACCOUNTS, accountRepository.getAccountsByActiveTrueAndOrganization(getSessionOrganization()));
            model.addAttribute(Constants.EXPENSES, dictionaryRepository.getDictionariesByActiveTrueAndAttr2AndDictionaryType_Attr1("expense", "action"));
            model.addAttribute(Constants.ACTIONS, dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("action"));
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Transaction(getSessionOrganization(),
                        dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("other", "action"),
                        false
                        ));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Transaction(!canViewAll()?getSessionOrganization():null, null));
            }
            Page<Transaction> transactions = transactionService.findAll((Transaction) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, transactions);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(transactions, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.ACCOUNT)) {
            model.addAttribute(Constants.CURRENCIES,  dictionaryRepository.getDictionariesByActiveTrueAndDictionaryType_Attr1("currency"));
            List<Account> accounts;
            if(canViewAll()){
                accounts = accountRepository.getAccountsByActiveTrue();
            } else {
                accounts = accountRepository.getAccountsByActiveTrueAndOrganization(getSessionOrganization());
            }
            model.addAttribute(Constants.LIST, accounts);
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Account(getSessionOrganization()));
            }
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(accounts, redirectAttributes, page);
            }
        } else if (page.equalsIgnoreCase(Constants.ROUTE.FINANCING)) {
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Financing(getSessionOrganization()));
            }
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Financing(!canViewAll()?getSessionOrganization():null, null, null));
            }
            Page<Financing> financings = financingService.findAll((Financing) model.asMap().get(Constants.FILTER), PageRequest.of(0, paginationSize(), Sort.by("id").descending()));
            model.addAttribute(Constants.LIST, financings);
            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(financings, redirectAttributes, page);
            }
        }
        return "layout";
    }

    @PostMapping(value = "/account")
    public String postAccount(@ModelAttribute(Constants.FORM) @Validated Account account, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            accountRepository.save(account);
            log("accounting_account", "create/edit", account.getId(), account.toString());
        }
        return mapPost(account, binding, redirectAttributes);
    }

    @PostMapping(value = "/account/transfer")
    public String postAccountTransfer(@ModelAttribute(Constants.FORM) @Validated Account account, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        Account to = accountRepository.getAccountByAccountNumberAndActiveTrue(account.getToAccountNumber().trim());
        Account from = accountRepository.getAccountById(account.getId());
        if(to==null){
            FieldError fieldError = new FieldError("toAccountNumber", "toAccountNumber", account.getToAccountNumber() + " hesab tapılmadı!");
            binding.addError(fieldError);
        }
        if(from!=null && account.getBalance()>from.getBalance()){
            FieldError fieldError = new FieldError("balance", "balance", "Hesabda kifayyət qədər məbləğ yoxdur!");
            binding.addError(fieldError);
        }
        if(from!=null && to!=null && from.getId().intValue()==to.getId().intValue()){
            FieldError fieldError = new FieldError("accountNumber", "accountNumber", "Eyni hesab olmamalıdır!");
            binding.addError(fieldError);
        }
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            Transaction fromTransaction = new Transaction();
            fromTransaction.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("send", "action"));
            fromTransaction.setOrganization(from.getOrganization());
            fromTransaction.setApproveDate(new Date());
            fromTransaction.setPrice(account.getBalance());
            fromTransaction.setCurrency(from.getCurrency());
            fromTransaction.setRate(getRate(fromTransaction.getCurrency()));
            double sumPrice1 = Util.amountChecker(fromTransaction.getAmount()) * fromTransaction.getPrice() * fromTransaction.getRate();
            fromTransaction.setSumPrice(sumPrice1);
            fromTransaction.setAccount(from);
            fromTransaction.setDescription(to.getOrganization().getName() + " : " + to.getAccountNumber() + " hesabına göndərildi. " + account.getDescription());
            transactionRepository.save(fromTransaction);
            log("accounting_transaction", "create/edit", fromTransaction.getId(), fromTransaction.toString());
            balance(fromTransaction);

            Transaction toTransaction = new Transaction();
            toTransaction.setAction(dictionaryRepository.getDictionaryByAttr1AndActiveTrueAndDictionaryType_Attr1("accept", "action"));
            toTransaction.setOrganization(to.getOrganization());
            toTransaction.setApproveDate(new Date());
            toTransaction.setCurrency(to.getCurrency());
            toTransaction.setRate(getRate(toTransaction.getCurrency()));
            toTransaction.setPrice(fromTransaction.getPrice()*Util.exchangeRate(fromTransaction.getRate(), toTransaction.getRate()));
            double sumPrice2 = Util.amountChecker(toTransaction.getAmount()) * toTransaction.getPrice() * toTransaction.getRate();
            toTransaction.setSumPrice(sumPrice2);
            toTransaction.setAccount(to);
            toTransaction.setDebt(true);
            toTransaction.setDescription(from.getOrganization().getName() + " : " + from.getAccountNumber() + " hesabından köçürmə ilə qəbul edilib. " + account.getDescription());
            transactionRepository.save(toTransaction);
            log("accounting_transaction", "create/edit", toTransaction.getId(), toTransaction.toString());
            balance(toTransaction);
        }
        return mapPost(account, binding, redirectAttributes, "/accounting/account");
    }

    @PostMapping(value = "/financing")
    public String postFinancing(@ModelAttribute(Constants.FORM) @Validated Financing financing, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            financingRepository.save(financing);
            log("accounting_financing", "create/edit", financing.getId(), financing.toString());
        }
        return mapPost(financing, binding, redirectAttributes);
    }

    @PostMapping(value = "/financing/filter")
    public String postFinancingFilter(@ModelAttribute(Constants.FILTER) @Validated Financing financing, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(financing, binding, redirectAttributes, "/accounting/financing");
    }

    @PostMapping(value = "/transaction")
    public String postTransaction(@ModelAttribute(Constants.FORM) @Validated Transaction transaction, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            transactionRepository.save(transaction);
            log("accounting_transaction", "create/edit", transaction.getId(), transaction.toString());
            balance(transaction);
        }
        return mapPost(transaction, binding, redirectAttributes);
    }

    @PostMapping(value = "/transaction/filter")
    public String postTransactionFilter(@ModelAttribute(Constants.FILTER) @Validated Transaction transaction, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        return mapFilter(transaction, binding, redirectAttributes, "/accounting/transaction");
    }

    @PostMapping(value = "/transaction/approve")
    public String postTransactionApprove(@ModelAttribute(Constants.FORM) @Validated Transaction transaction, BindingResult binding, RedirectAttributes redirectAttributes, @RequestParam(name = "expense", required = false) int[] expenses) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()) {
            Transaction trn = transactionRepository.getTransactionById(transaction.getId());
            if (trn.getOrganization().getId() == Util.getUserBranch(getSessionUser().getEmployee().getOrganization()).getId()){ trn.setApprove(true);
                trn.setApproveDate(new Date());
                trn.setPrice(transaction.getPrice());
                trn.setCurrency(transaction.getCurrency());
                trn.setRate(getRate(transaction.getCurrency()));
                double sumPrice = Util.amountChecker(trn.getAmount()) * transaction.getPrice() * trn.getRate();
                trn.setSumPrice(sumPrice);
                trn.setAccount(transaction.getAccount());
                transactionRepository.save(trn);
                log("accounting_transaction", "create/edit", trn.getId(), trn.toString());
                balance(trn);

                if (expenses != null) {
                    for (int expense : expenses) {
                        Dictionary action = dictionaryRepository.getDictionaryById(expense);
                        String description = action.getName() + ", " + trn.getDescription();
                        Transaction transaction1 = new Transaction(trn.getOrganization(), trn.getInventory(), action, description, false, trn);
                        transactionRepository.save(transaction1);
                        log("admin_dictionary", "create/edit", transaction1.getId(), transaction1.toString());
                    }
                }

                if(trn.getInventory()!=null){
                    Financing financing = financingRepository.getFinancingByActiveTrueAndInventoryAndOrganization(trn.getInventory(), trn.getOrganization());
                    Double financingPrice = calculateFinancing(trn, transactionRepository);
                    if (financing != null) {
                        financing.setPrice(financingPrice);
                        financing.setFinancingDate(new Date());
                    } else {
                        financing = new Financing(trn.getInventory(), financingPrice, trn.getOrganization());
                    }
                    financingRepository.save(financing);
                    log("accounting_financing", "approve", financing.getId(), financing.toString());
                }
            } else {
                List<String> messages = new ArrayList<>();
                messages.add("Təsdiqləmə əməliyyatı " + Util.getUserBranch(trn.getOrganization()).getName() + " tərəfindən edilməlidir!");
                redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, new Response(Constants.STATUS.ERROR, messages));
            }
        }
        return mapPost(transaction, binding, redirectAttributes, "/accounting/transaction");
    }

    @ResponseBody
    @GetMapping(value = "/api/account/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Account findAccount(@PathVariable("accountNumber") String accountNumber){
        try {
            return accountRepository.getAccountByAccountNumberAndActiveTrue(accountNumber.trim());
        } catch (Exception e){
            log.error(e);
        }
        return null;
    }

    private static Double calculateFinancing(Transaction transaction, TransactionRepository transactionRepository){
        int amount=0;
        List<Transaction> transactions = transactionRepository.getTransactionsByInventoryAndApproveTrueAndOrganization(transaction.getInventory(), transaction.getOrganization());
        for(Transaction trn: transactions){
            if(trn.getApprove() &&
                    (trn.getAction().getAttr1().equalsIgnoreCase("accept") ||
                            trn.getAction().getAttr1().equalsIgnoreCase("buy"))){
                amount+=Util.amountChecker(trn.getAmount());
            }
        }
        double sumPrice = 0;
        for(Transaction trn: transactions){
            sumPrice+=trn.getSumPrice();
        }
        amount = amount>0?amount:1;
        return Double.parseDouble(Util.format(sumPrice/amount));
    }
}
