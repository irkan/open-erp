package com.openerp.controller;

import com.openerp.entity.Item;
import com.openerp.entity.Module;
import com.openerp.entity.Schedule;
import com.openerp.entity.ShortenedWorkingDay;
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

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/idgroup")
public class IDGroupController extends SkeletonController {

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

        if(page.equalsIgnoreCase(Constants.ROUTE.ITEM)){
            model.addAttribute(Constants.LIST, itemRepository.getItemsByActiveTrue());
            if(!model.containsAttribute(Constants.FORM)){
                model.addAttribute(Constants.FORM, new Item());
            }
        }
        return "layout";
    }

    @PostMapping(value = "/item")
    public String postShortenedWorkingDay(@ModelAttribute(Constants.FORM) @Validated Item item, BindingResult binding, RedirectAttributes redirectAttributes) throws Exception {
        redirectAttributes.addFlashAttribute(Constants.STATUS.RESPONSE, Util.response(binding,Constants.TEXT.SUCCESS));
        if(!binding.hasErrors()){
            itemRepository.save(item);
        }
        return mapPost(item, binding, redirectAttributes);
    }

    @PostMapping(value = "/item/upload", consumes = {"multipart/form-data"})
    public String postShortenedWorkingDayUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws Exception {
        List<Item> items = ReadWriteExcelFile.readXLSXFileItems(file.getInputStream());
        for(Item item: items){
            List<Item> itms = itemRepository.getItemsByActiveTrueAndBarcode(item.getBarcode());
            if(itms!=null && itms.size()==0){
                itemRepository.save(item);
            }
        }
        return mapPost(redirectAttributes, "/idgroup/item");
    }
}
