package com.openerp.dao;

import com.openerp.controller.SkeletonController;
import com.openerp.domain.Report;
import com.openerp.util.DateUtility;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReportingDao implements IReportingDao {
    protected static final Logger log = Logger.getLogger(SkeletonController.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Report> reportLast12MonthAdvance(Integer organizationId, Integer employeeId) throws Exception {
        List<Report> list = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql = "select SUM(payed) PAYED, MONTH(advance_date) MONTH, YEAR(advance_date) YEAR from payroll_advance where is_approve=1 and is_active=1 and approve_date>DATE_SUB(CURDATE(), INTERVAL 12 MONTH )";
            if(organizationId!=null){
                sql+=" and hr_organization_id="+organizationId.intValue();
            }
            if(employeeId!=null){
                sql+=" and hr_employee_id="+employeeId.intValue();
            }
            sql+=" GROUP BY YEAR(advance_date), MONTH(advance_date)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    Report report = null;
                    while(resultSet.next()) {
                        report = new Report();
                        report.setInteger1(resultSet.getInt("MONTH"));
                        report.setInteger2(resultSet.getInt("YEAR"));
                        report.setString1(DateUtility.findMonthShortName(report.getInteger1())+", "+report.getInteger2());
                        report.setDouble1(resultSet.getDouble("PAYED"));
                        list.add(report);
                    }
                }
            }
            return list;
        } catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Report> reportLast12MonthNonPayedAdvance(Integer organizationId, Integer employeeId) throws Exception {
        List<Report> list = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql = "select SUM(payed) PAYED, MONTH(advance_date) MONTH, YEAR(advance_date) YEAR from payroll_advance where is_approve=1 and is_active=1 and is_transaction=0 and approve_date>DATE_SUB(CURDATE(), INTERVAL 12 MONTH )";
            if(organizationId!=null){
                sql+=" and hr_organization_id="+organizationId.intValue();
            }
            if(employeeId!=null){
                sql+=" and hr_employee_id="+employeeId.intValue();
            }
            sql+=" GROUP BY YEAR(advance_date), MONTH(advance_date)";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    Report report = null;
                    while(resultSet.next()) {
                        report = new Report();
                        report.setInteger1(resultSet.getInt("MONTH"));
                        report.setInteger2(resultSet.getInt("YEAR"));
                        report.setString1(DateUtility.findMonthShortName(report.getInteger1())+", "+report.getInteger2());
                        report.setDouble1(resultSet.getDouble("PAYED"));
                        list.add(report);
                    }
                }
            }
            return list;
        } catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
