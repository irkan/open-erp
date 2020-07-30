package com.openerp.controller;

import com.openerp.domain.Report;
import com.openerp.entity.Customer;
import com.openerp.util.Constants;
import com.openerp.util.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/report")
public class ReportController extends SkeletonController {

    @GetMapping(value = {"/{page}", "/{page}/{data}"})
    public String route(Model model, @PathVariable("page") String page, @PathVariable("data") Optional<String> data, RedirectAttributes redirectAttributes) throws Exception {
        if(page.equalsIgnoreCase(Constants.ROUTE.REPORT_ACCOUNTING)){

        } else if(page.equalsIgnoreCase(Constants.ROUTE.SALES_BALANCE)){
            if(!model.containsAttribute(Constants.FILTER)){
                model.addAttribute(Constants.FILTER, new Report());
            }
        } else if(page.equalsIgnoreCase(Constants.ROUTE.SERVICE_BALANCE)){

        }
        return "layout";
    }
}
