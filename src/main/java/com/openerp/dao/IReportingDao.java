package com.openerp.dao;

import com.openerp.domain.Report;

import java.util.List;

public interface IReportingDao {
    List<Report> reportLast12MonthAdvance(Integer organizationId, Integer employeeId) throws Exception;
    List<Report> reportLast12MonthNonPayedAdvance(Integer organizationId, Integer employeeId) throws Exception;
}
