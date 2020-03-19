package com.openerp.controller;

import com.openerp.entity.Configuration;
import com.openerp.entity.EmailAnalyzer;
import com.openerp.entity.IDDiscount;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.ReadWriteExcelFile;
import com.openerp.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/idgroup")
public class IDGroupController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {

        if(page.equalsIgnoreCase(Constants.ROUTE.IDDISCOUNT)){
            model.addAttribute(Constants.LIST, iDDiscountRepository.getIDDiscountsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new IDDiscount());
            }

            if(!data.equals(Optional.empty()) && data.get().equalsIgnoreCase(Constants.ROUTE.EXPORT)){
                return exportExcel(iDDiscountRepository.findAll(), redirectAttributes, page);
            }
        }
        return "layout";
    }

    @PostMapping(value = "/iddiscount")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated IDDiscount idDiscount, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            iDDiscountRepository.save(idDiscount);
            log(idDiscount, "idgroup_id_discount", "create/edit", idDiscount.getId(), idDiscount.toString());
        }
        return mapPost(idDiscount, binding, redirectAttributes);
    }

    @PostMapping(value = "/iddiscount/upload", consumes = {"multipart/form-data"})
    public String postShortenedWorkingDayUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        Configuration configuration = configurationRepository.getConfigurationByKey("id_discount");
        List<IDDiscount> items = ReadWriteExcelFile.readXLSXFileItems(file.getInputStream());
        for(IDDiscount item: items){
            if(item.getCode()!=null && item.getCode().length()>0){
                List<IDDiscount> idDiscounts = iDDiscountRepository.getIDDiscountByCode(item.getCode());
                if(idDiscounts!=null && idDiscounts.size()>0){
                    IDDiscount idDiscount = idDiscounts.get(0);
                    idDiscount.setActive(true);
                    idDiscount.setDescription((item.getDescription()!=null && item.getDescription().length()>0)?item.getDescription():idDiscount.getDescription());
                    idDiscount.setDiscount((item.getDiscount()!=null && item.getDiscount()>0)?item.getDiscount():Double.parseDouble(configuration.getAttribute()));
                    iDDiscountRepository.save(idDiscount);
                    log(idDiscount, "idgroup_id_discount", "create/edit", idDiscount.getId(), idDiscount.toString());
                } else {
                    item.setActive(true);
                    item.setDescription((item.getDescription()!=null && item.getDescription().length()>0)?item.getDescription():"");
                    item.setDiscount((item.getDiscount()!=null && item.getDiscount()>0)?item.getDiscount():Double.parseDouble(configuration.getAttribute()));
                    iDDiscountRepository.save(item);
                    log(item, "idgroup_id_discount", "create/edit", item.getId(), item.toString());
                }
            }
        }
        return mapPost(redirectAttributes, "/idgroup/iddiscount");
    }

    @PostMapping(value = "/email-analyzer/upload", consumes = {"multipart/form-data"})
    public String postEmailAnalyzerUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<EmailAnalyzer> emailAnalyzers = new ArrayList<>();
            String line;
            EmailAnalyzer ea;
            while ((line = br.readLine()) != null) {
                ea = new EmailAnalyzer();
                ea.setContent(line);
                ea.setOperationDate(DateUtility.getUtilDateFromEmailAnalyzer(line.split(Pattern.quote("["))[1].split(Pattern.quote("]"))[0].trim()));
                ea.setOperation(line.split(Pattern.quote("]"))[1].split(Pattern.quote("Queue-ID"))[0].trim().split(Pattern.quote(":"))[0]);
                ea.setQueueID(getValue(line, "Queue-ID:"));
                ea.setService(getValue(line, "Service:"));
                ea.setRecipient(getValue(line, "Recipient:"));
                ea.setFrom(getValue(line, "From:"));
                ea.setTo(getValue(line, "To:"));
                ea.setSize(getValue(line, "Size:"));
                ea.setSenderHost(getValue(line, "Sender-Host:"));
                ea.setRemoteHost(getValue(line, "Remote-Host:"));
                ea.setUser(getValue(line, "User:"));
                ea.setSubject(getValue(line, "Subject:"));
                ea.setFw(getValue(line, "FW:"));
                ea.setRe(getValue(line, "Re:"));
                ea.setR(getValue(line, "R:"));
                ea.setMsgId(getValue(line, "Msg-Id:"));
                ea.setResult(getValue(line, "Result:"));
                ea.setStatus(getValue(line, "Status:"));
                emailAnalyzers.add(ea);
            }
            emailAnalyzerRepository.saveAll(emailAnalyzers);
        }
        //log(nonWorkingDays, "hr_non_working_day", "create/edit", null, nonWorkingDays.toString(), "NonWorkingDay upload edildi");
        return mapPost(redirectAttributes, "/idgroup/email-analyzer");
    }

    String getValue(String row, String key){
        String[] rows = row.split(Pattern.quote(key));
        if(rows.length>1){
            String[] values = rows[1].split(Pattern.quote(","));
            if(values.length>1){
                return values[0].trim();
            } else {
                return rows[1].trim();
            }
        }
        return "";
    }
}
