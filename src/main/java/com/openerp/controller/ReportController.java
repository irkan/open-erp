package com.openerp.controller;

import com.openerp.domain.Report;
import com.openerp.domain.Schedule;
import com.openerp.entity.*;
import com.openerp.util.Constants;
import com.openerp.util.DateUtility;
import com.openerp.util.Util;
import com.openerp.util.UtilJson;
import lombok.extern.slf4j.Slf4j;
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

import javax.security.auth.login.Configuration;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Slf4j
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
            GlobalConfiguration troubledCustomer = configurationRepository.getGlobalConfigurationByKeyAndActiveTrue("troubled_customer");
            int latencyDay = Util.parseInt(troubledCustomer.getAttribute(), "60");
            List<JSONObject> jsonObjects = reportingDao.reportCollectVolume(report);
            jsonObjects = getCurrentLatencyPosition(jsonObjects, latencyDay, report);
            returnedReport.setString1(Util.checkNull(jsonObjects));
            report.setInteger1(2);
            returnedReport.setString2(Util.checkNull(reportingDao.reportPaymentLatencyPeriodly(report)));
            report.setString7(" and pl1.latency_day<="+latencyDay+" ");
            returnedReport.setString3(Util.checkNull(reportingDao.reportPaymentLatencyPeriodly(report)));
            report.setString7(" and pl1.latency_day>"+latencyDay+" "); // troubled customer
            returnedReport.setString4(Util.checkNull(reportingDao.reportPaymentLatencyPeriodly(report)));
            returnedReport.setString5(Util.checkNull(reportingDao.reportSales(report)));
        }
        return returnedReport;
    }

    public List<JSONObject> getCurrentLatencyPosition(List<JSONObject> jsonObjects, int latencyDay, Report report) throws Exception {
        int latencySum=0,latencyCount=0,troubledSum=0,troubledCount=0;
        List<Organization> organizations = new ArrayList<>();

        if(report.getString1()!=null){
            String[] orgIds = report.getString1().split(Pattern.quote("o.id="));
            if(orgIds.length>1){
                if(!orgIds[1].trim().equalsIgnoreCase("0")){
                    organizations.add(organizationRepository.getOrganizationByIdAndActiveTrue(Util.parseInt(orgIds[1].trim())));
                } else {
                    organizations.addAll(organizationRepository.getOrganizationsByActiveTrue());
                }
            } else {
                organizations.addAll(organizationRepository.getOrganizationsByActiveTrue());
            }
        }
        for(Organization organization: organizations){
            try{
                Sales salesObject = new Sales(null, organization);
                salesObject.setService(null);
                salesObject.setApprove(false);
                salesObject.setSaled(false);
                salesObject.setReturned(false);
                Payment paymentObject = new Payment();
                salesObject.setPayment(paymentObject);
                salesObject.setSaleDate(null);

                Page<Sales> sales = salesService.findAll(salesObject, PageRequest.of(0, 100000, Sort.by("id").descending()));
                for(Sales sale: sales.getContent()){
                    try{
                        double sumOfInvoices = Util.calculateInvoice(sale.getInvoices());
                        List<Schedule> schedules = new ArrayList<>();
                        if(sale.getPayment()!=null && !sale.getPayment().getCash()){
                            schedules = Util.getSchedulePayment(DateUtility.getFormattedDate(sale.getSaleDate()), sale.getPayment().getSchedule(), sale.getPayment().getPeriod(), sale.getPayment().getLastPrice(), sale.getPayment().getDown(), Util.parseInt(sale.getPayment().getGracePeriod()));
                        }
                        double plannedPayment = Util.calculatePlannedPayment(sale, schedules);
                        if(sale.getPayment().getLastPrice()>sumOfInvoices && sumOfInvoices<plannedPayment){
                            schedules = Util.calculateSchedule(sale, schedules, sumOfInvoices);
                            int latency = Util.calculateLatency(schedules, sumOfInvoices, sale);
                            sale.getPayment().setLatency(latency);
                            sale.getPayment().setSumOfInvoice(sumOfInvoices);
                            sale.getPayment().setUnpaid(plannedPayment-sumOfInvoices);
                            sale.getPayment().setLastPaid(Util.getLastPaid(sale.getInvoices()));
                            if(latency>0){
                                if(latency>latencyDay){
                                    troubledSum += sale.getPayment().getUnpaid();
                                    troubledCount += 1;
                                } else {
                                    latencySum += sale.getPayment().getUnpaid();
                                    latencyCount += 1;
                                }
                            }
                        }
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
            } catch (Exception e){
                log.error(e.getMessage(), e);
            }
        }
        if(jsonObjects.size()>0){
            JSONObject jsonObject = jsonObjects.get(0);
            jsonObject.put("PROBLEMLI_MUSTERI_CEM", troubledSum);
            jsonObject.put("PROBLEMLI_MUSTERI_SAY", troubledCount);
            jsonObject.put("GECIKDIRMIS_MUSTERI_CEM", latencySum);
            jsonObject.put("GECIKDIRMIS_MUSTERI_SAY", latencyCount);
        }

        return jsonObjects;
    }
}
