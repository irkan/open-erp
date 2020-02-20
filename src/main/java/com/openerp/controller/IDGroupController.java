package com.openerp.controller;

import com.openerp.entity.Configuration;
import com.openerp.entity.IDDiscount;
import com.openerp.util.Constants;
import com.openerp.util.ReadWriteExcelFile;
import com.openerp.util.Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

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
            log("idgroup_id_discount", "create/edit", idDiscount.getId(), idDiscount.toString());
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
                    log("idgroup_id_discount", "create/edit", idDiscount.getId(), idDiscount.toString());
                } else {
                    item.setActive(true);
                    item.setDescription((item.getDescription()!=null && item.getDescription().length()>0)?item.getDescription():"");
                    item.setDiscount((item.getDiscount()!=null && item.getDiscount()>0)?item.getDiscount():Double.parseDouble(configuration.getAttribute()));
                    iDDiscountRepository.save(item);
                    log("idgroup_id_discount", "create/edit", item.getId(), item.toString());
                }
            }
        }
        return mapPost(redirectAttributes, "/idgroup/iddiscount");
    }
}
