package com.openerp.dao;

import com.openerp.domain.Report;
import org.json.JSONObject;

import java.util.List;

public interface IReportingDao {
    List<Report> reportLast12MonthAdvance(Integer organizationId, Integer employeeId) throws Exception;

    List<Report> reportLast12MonthNonPayedAdvance(Integer organizationId, Integer employeeId) throws Exception;

    List<JSONObject> salesDetail() throws Exception;

    List<JSONObject> reportCollectPeriodly(Report report) throws Exception;

    List<JSONObject> reportCountApprovedPeriodly(Report report) throws Exception;

    List<JSONObject> reportCountNotApprovedPeriodly(Report report) throws Exception;

    List<JSONObject> reportCollectVolume(Report report) throws Exception;

    List<JSONObject>  reportPaymentLatencyPeriodly(Report report) throws Exception;

    List<Report>  reportCorrectInventory() throws Exception;
}
