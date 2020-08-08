package com.openerp.controller;

import com.openerp.domain.Report;
import com.openerp.entity.Customer;
import com.openerp.entity.Invoice;
import com.openerp.entity.NonWorkingDay;
import com.openerp.entity.Sales;
import com.openerp.util.Constants;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
import org.json.JSONObject;
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

import java.sql.ResultSet;
import java.util.List;
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

        } else if(page.equalsIgnoreCase(Constants.ROUTE.DASHBOARD)){
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.FILTER, new Report());
        } else if(page.equalsIgnoreCase(Constants.ROUTE.REPORT_SALES)){
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.FILTER, new Report());
        } else if(page.equalsIgnoreCase(Constants.ROUTE.REPORT_COLLECT)){
            model.addAttribute(Constants.ORGANIZATIONS, organizationRepository.getOrganizationsByActiveTrue());
            model.addAttribute(Constants.FILTER, new Report());
        }
        return "layout";
    }

    @ResponseBody
    @GetMapping(value = "/api/sales-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getSalesDetail() throws Exception {
        return Util.checkNull(reportingDao.salesDetail());
    }

    @ResponseBody
    @PostMapping(value = "/api/report-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public Report postReportSales(@ModelAttribute(Constants.FILTER) @Validated Report report, BindingResult binding) throws Exception {
        Report returnedReport = new Report();
        if(!binding.hasErrors()){
            returnedReport.setString1(Util.checkNull(reportingDao.reportCountApprovedPeriodly(report)));
            returnedReport.setString2(Util.checkNull(reportingDao.reportCountNotApprovedPeriodly(report)));
            returnedReport.setString3(Util.checkNull(reportingDao.reportCollectPeriodly(report)));
        }
        return returnedReport;
    }

    @ResponseBody
    @PostMapping(value = "/api/report-collect", produces = MediaType.APPLICATION_JSON_VALUE)
    public Report postReportCollectVolume(@ModelAttribute(Constants.FILTER) @Validated Report report, BindingResult binding) throws Exception {
        Report returnedReport = new Report();
        if(!binding.hasErrors()){
            returnedReport.setString1(Util.checkNull(reportingDao.reportCollectVolume(report)));
            report.setInteger1(2);
            returnedReport.setString2(Util.checkNull(reportingDao.reportPaymentLatencyPeriodly(report)));
            report.setString7(" and pl1.latency_day<=60 ");
            returnedReport.setString3(Util.checkNull(reportingDao.reportPaymentLatencyPeriodly(report)));
            report.setString7(" and pl1.latency_day>60 "); // troubled customer
            returnedReport.setString4(Util.checkNull(reportingDao.reportPaymentLatencyPeriodly(report)));
        }
        return returnedReport;
    }
}
