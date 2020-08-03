package com.openerp.dao;

import com.openerp.controller.SkeletonController;
import com.openerp.domain.Report;
import com.openerp.util.DateUtility;
import com.openerp.util.ReportUtil;
import com.openerp.util.Util;
import org.apache.log4j.Logger;
import org.json.JSONObject;
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
            String sql = "select SUM(payed) PAYED, MONTH(advance_date) MONTH, YEAR(advance_date) YEAR from advance where is_approve=1 and is_active=1 and approve_date>DATE_SUB(CURDATE(), INTERVAL 12 MONTH )";
            if(organizationId!=null){
                sql+=" and organization_id="+organizationId.intValue();
            }
            if(employeeId!=null){
                sql+=" and employee_id="+employeeId.intValue();
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
            String sql = "select SUM(payed) PAYED, MONTH(advance_date) MONTH, YEAR(advance_date) YEAR from advance where is_approve=1 and is_active=1 and is_transaction=0 and approve_date>DATE_SUB(CURDATE(), INTERVAL 12 MONTH )";
            if(organizationId!=null){
                sql+=" and organization_id="+organizationId.intValue();
            }
            if(employeeId!=null){
                sql+=" and employee_id="+employeeId.intValue();
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
    public List<JSONObject> salesDetail() throws Exception {
        List<JSONObject> jsonObjects = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql = "select o.id flial_id,\n" +
                    " o.name flial,\n" +
                    " (select count(*) from sales s1 where s1.organization_id=o.id) melumat_sayi,\n" +
                    " (select count(*) from sales s1 where  s1.is_service=0 and s1.organization_id=o.id) satis_melumat_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_service=1 and s1.organization_id=o.id) sevis_melumat_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and s1.organization_id=o.id) son_1_gunluk_aktiv_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and s1.is_service=0 and s1.organization_id=o.id) son_1_gunluk_aktiv_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and s1.is_service=1 and s1.organization_id=o.id) son_1_gunluk_aktiv_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and i1.organization_id=o.id) son_1_gunluk_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=0 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and i1.organization_id=o.id) son_1_gunluk_satis_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=1 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 DAY) AND NOW() and i1.organization_id=o.id) son_1_gunluk_servis_yigimlarin_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and s1.organization_id=o.id) son_1_heftelik_aktiv_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and s1.is_service=0 and s1.organization_id=o.id) son_1_heftelik_aktiv_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and s1.is_service=1 and s1.organization_id=o.id) son_1_heftelik_aktiv_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and i1.organization_id=o.id) son_1_heftelik_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=0 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and i1.organization_id=o.id) son_1_heftelik_satis_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=1 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() and i1.organization_id=o.id) son_1_heftelik_servis_yigimlarin_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and s1.organization_id=o.id) son_1_ayliq_aktiv_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and s1.is_service=0 and s1.organization_id=o.id) son_1_ayliq_aktiv_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and s1.is_service=1 and s1.organization_id=o.id) son_1_ayliq_aktiv_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and i1.organization_id=o.id) son_1_ayliq_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=0 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and i1.organization_id=o.id) son_1_ayliq_satis_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=1 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 MONTH) AND NOW() and i1.organization_id=o.id) son_1_ayliq_servis_yigimlarin_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and s1.organization_id=o.id) son_1_illik_aktiv_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and s1.is_service=0 and s1.organization_id=o.id) son_1_illik_aktiv_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and s1.is_service=1 and s1.organization_id=o.id) son_1_illik_aktiv_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and i1.organization_id=o.id) son_1_illik_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=0 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and i1.organization_id=o.id) son_1_illik_satis_yigimlarin_cemi,\n" +
                    " (select IFNULL(sum(i1.price), 0) from invoice i1, sales s1 where s1.id=i1.sales_id and s1.is_service=1 and i1.is_active=1 and i1.is_approve=1 and i1.approve_date BETWEEN DATE_SUB(NOW(), INTERVAL 1 YEAR) AND NOW() and i1.organization_id=o.id) son_1_illik_servis_yigimlarin_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.organization_id=o.id) aktiv_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id) aktiv_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id) aktiv_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.organization_id=o.id) aktiv_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id) aktiv_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id) aktiv_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.organization_id=o.id) qaytarilmis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.is_service=0 and s1.organization_id=o.id) qaytarilmis_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.is_service=1 and s1.organization_id=o.id) qaytarilmis_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.organization_id=o.id) qaytarilmis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.is_service=0 and s1.organization_id=o.id) qaytarilmis_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=1 and s1.is_service=1 and s1.organization_id=o.id) qaytarilmis_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=0 and s1.organization_id=o.id) silinmis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=0 and s1.is_service=0 and s1.organization_id=o.id) silinmis_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=0 and s1.is_service=1 and s1.organization_id=o.id) silinmis_servis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.organization_id=o.id) tesdiq_gozleyen_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id) tesdiq_gozleyen_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id) tesdiq_gozleyen_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.organization_id=o.id) tesdiq_gozleyen_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id) tesdiq_gozleyen_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=0 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id) tesdiq_gozleyen_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.organization_id=o.id) nagd_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.is_service=0 and s1.organization_id=o.id) nagd_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.is_service=1 and s1.organization_id=o.id) nagd_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.organization_id=o.id) nagd_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.is_service=0 and s1.organization_id=o.id) nagd_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=1 and s1.is_service=1 and s1.organization_id=o.id) nagd_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.organization_id=o.id) kredit_muqavilelerinin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.is_service=0 and s1.organization_id=o.id) kredit_satis_muqavilelerinin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.is_service=1 and s1.organization_id=o.id) kredit_servis_muqavilelerinin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.organization_id=o.id) kredit_muqavilelerinin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.is_service=0 and s1.organization_id=o.id) kredit_satis_muqavilelerinin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.is_cash=0 and s1.is_service=1 and s1.organization_id=o.id) kredit_servis_muqavilelerinin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) endirimli_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=0 and s1.organization_id=o.id) endirimli_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=1 and s1.organization_id=o.id) endirimli_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) endirimli_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=0 and s1.organization_id=o.id) endirimli_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=1 and s1.organization_id=o.id) endirimli_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) endirimli_olmayan_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=0 and s1.organization_id=o.id) endirimli_olmayan_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=1 and s1.organization_id=o.id) endirimli_olmayan_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) endirimli_olmayan_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=0 and s1.organization_id=o.id) endirimli_olmayan_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=1 and s1.organization_id=o.id) endirimli_olmayan_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.organization_id=o.id) bitmemiw_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id) bitmemis_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id) bitmemis_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.organization_id=o.id) bitmemis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id) bitmemis_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id) bitmemis_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.organization_id=o.id)  bitmis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id)  bitmis_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1 where s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id)  bitmis_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.organization_id=o.id)  bitmis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.is_service=0 and s1.organization_id=o.id)  bitmis_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=1 and s1.is_returned=0 and s1.is_service=1 and s1.organization_id=o.id)  bitmis_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) bitmemis_endirimli_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=0 and s1.organization_id=o.id) bitmemis_endirimli_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=1 and s1.organization_id=o.id) bitmemis_endirimli_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.organization_id=o.id) bitmemis_endirimli_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=0 and s1.organization_id=o.id) bitmemis_endirimli_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price<p1.price and s1.is_service=1 and s1.organization_id=o.id) bitmemis_endirimli_servis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) bitmemis_endirimli_olmayan_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=0 and s1.organization_id=o.id) bitmemis_endirimli_olmayan_satis_muqavilelerin_sayi,\n" +
                    " (select count(*) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=1 and s1.organization_id=o.id) bitmemis_endirimli_olmayan_servis_muqavilelerin_sayi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.organization_id=o.id) bitmemis_endirimli_olmayan_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=0 and s1.organization_id=o.id) bitmemis_endirimli_olmayan_satis_muqavilelerin_meblegleri_cemi,\n" +
                    " (select IFNULL(sum(IFNULL(p1.last_price,0)),0) from sales s1, payment p1 where s1.payment_id=p1.id and s1.is_active=1 and s1.is_approve=1 and s1.is_saled=0 and s1.is_returned=0 and p1.last_price>=p1.price and s1.is_service=1 and s1.organization_id=o.id) bitmemis_endirimli_olmayan_servis_muqavilelerin_meblegleri_cemi \n" +
                    " from organization o where o.is_active=1 ";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    jsonObjects = Util.getFormattedResult(resultSet);
                }
            }
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return jsonObjects;
    }

    @Override
    public List<JSONObject> reportCollectPeriodly(Report report) throws Exception {
        List<JSONObject> jsonObjects = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String orderby = "";
            String groupby = "";
            String condition = "";
            String select = "";
            switch (Util.parseInt(report.getInteger1())){
                case 1:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY MONTH(i1.approve_date), DAY(i1.approve_date) ";
                    condition = " and i1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 1 MONTH) ";
                    select = " i1.approve_date xaxis, DAY(i1.approve_date) zaxis ";
                    break;
                case 2:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY YEAR(i1.approve_date), MONTH(i1.approve_date) ";
                    condition = " and i1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 13 MONTH) ";
                    select = " i1.approve_date xaxis, CONCAT_WS('.', MONTH(s1.approve_date), YEAR(s1.approve_date)) zaxis ";
                    break;
                case 3:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY YEAR(i1.approve_date) ";
                    condition = " and i1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 10 YEAR) ";
                    select = " YEAR(i1.approve_date) xaxis, YEAR(i1.approve_date) zaxis ";
                    break;
                default:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY MONTH(i1.approve_date), DAY(i1.approve_date) ";
                    condition = " and i1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 8 DAY) ";
                    select = " DAY(i1.approve_date) xaxis, DAY(i1.approve_date) zaxis ";
                    break;
            }
            String sql = "select " + select + ", FLOOR(IFNULL(sum(i1.price), 0)) yaxis " +
                    " from invoice i1, sales s1, payment p1, organization o " +
                    " where i1.sales_id=s1.id and i1.organization_id=o.id " +
                    " and s1.payment_id=p1.id and i1.is_active=1 and i1.is_approve=1 " +
                     condition + Util.checkNull(report.getString1()) + Util.checkNull(report.getString2()) +
                    Util.checkNull(report.getString3()) + groupby + orderby;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    jsonObjects = Util.getFormattedResult(resultSet);
                }
            }
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return ReportUtil.correct(jsonObjects, report);
    }

    @Override
    public List<JSONObject> reportCountApprovedPeriodly(Report report) throws Exception {
        List<JSONObject> jsonObjects = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String orderby = "";
            String groupby = "";
            String condition = "";
            String select = "";
            switch (Util.parseInt(report.getInteger1())){
                case 1:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY MONTH(s1.approve_date), DAY(s1.approve_date) ";
                    condition = " and s1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 1 MONTH) ";
                    select = " s1.approve_date xaxis, DAY(s1.approve_date) zaxis ";
                    break;
                case 2:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY YEAR(s1.approve_date), MONTH(s1.approve_date) ";
                    condition = " and s1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 13 MONTH) ";
                    select = " s1.approve_date xaxis, CONCAT_WS('.', MONTH(s1.approve_date), YEAR(s1.approve_date)) zaxis ";
                    break;
                case 3:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY YEAR(s1.approve_date) ";
                    condition = " and s1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 10 YEAR) ";
                    select = " YEAR(s1.approve_date) xaxis, YEAR(s1.approve_date) zaxis ";
                    break;
                default:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY MONTH(s1.approve_date), DAY(s1.approve_date) ";
                    condition = " and s1.approve_date>=DATE_SUB(CURDATE(), INTERVAL 8 DAY) ";
                    select = " DAY(s1.approve_date) xaxis, DAY(s1.approve_date) zaxis ";
                    break;
            }
            String sql = "select " + select + ", FLOOR(IFNULL(count(*), 0)) yaxis " +
                    " from sales s1, payment p1, organization o " +
                    " where s1.organization_id=o.id and s1.payment_id=p1.id " +
                    " and s1.is_approve=1 " +
                    condition + Util.checkNull(report.getString1()) + Util.checkNull(report.getString2()) +
                    Util.checkNull(report.getString3()) + Util.checkNull(report.getString4())
                    + Util.checkNull(report.getString5()) + Util.checkNull(report.getString6()) + groupby + orderby;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    jsonObjects = Util.getFormattedResult(resultSet);
                }
            }
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return ReportUtil.correct(jsonObjects, report);
    }

    @Override
    public List<JSONObject> reportCountNotApprovedPeriodly(Report report) throws Exception {
        List<JSONObject> jsonObjects = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String orderby = "";
            String groupby = "";
            String condition = "";
            String select = "";
            switch (Util.parseInt(report.getInteger1())){
                case 1:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY MONTH(s1.date), DAY(s1.date) ";
                    condition = " and s1.date>=DATE_SUB(CURDATE(), INTERVAL 1 MONTH) ";
                    select = " s1.date xaxis, DAY(s1.date) zaxis ";
                    break;
                case 2:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY YEAR(s1.date), MONTH(s1.date) ";
                    condition = " and s1.date>=DATE_SUB(CURDATE(), INTERVAL 13 MONTH) ";
                    select = " s1.date xaxis, CONCAT_WS('.', MONTH(s1.date), YEAR(s1.date)) zaxis ";
                    break;
                case 3:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY YEAR(s1.date) ";
                    condition = " and s1.date>=DATE_SUB(CURDATE(), INTERVAL 10 YEAR) ";
                    select = " YEAR(s1.date) xaxis, YEAR(s1.date) zaxis ";
                    break;
                default:
                    orderby = "  order by xaxis ";
                    groupby = " GROUP BY MONTH(s1.date), DAY(s1.date) ";
                    condition = " and s1.date>=DATE_SUB(CURDATE(), INTERVAL 8 DAY) ";
                    select = " DAY(s1.date) xaxis, DAY(s1.date) zaxis ";
                    break;
            }
            String sql = "select " + select + ", FLOOR(IFNULL(count(*), 0)) yaxis " +
                    " from sales s1, payment p1, organization o " +
                    " where s1.organization_id=o.id and s1.payment_id=p1.id " +
                    " and s1.is_approve=0 " +
                    condition + Util.checkNull(report.getString1()) + Util.checkNull(report.getString2()) +
                    Util.checkNull(report.getString3()) + Util.checkNull(report.getString4())
                    + Util.checkNull(report.getString5()) + Util.checkNull(report.getString6()) + groupby + orderby;
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    jsonObjects = Util.getFormattedResult(resultSet);
                }
            }
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return ReportUtil.correct(jsonObjects, report);
    }

    @Override
    public List<JSONObject> reportCollectVolume(Report report) throws Exception {
        List<JSONObject> jsonObjects = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            String sql = " select IFNULL(sum(IFNULL(k1.SALES_VOLUME, 0)),0) UMUMI_SATIS_HECMI, " +
                    "       IFNULL(sum(IFNULL(k1.MONTHLY_VOLUME, 0)), 0) AYLIQ_YIGIM_HECMI, " +
                    "       IFNULL(sum(IF(IFNULL(k1.ALL_BALANCE, 0)>0,IFNULL(k1.ALL_BALANCE, 0),0)),0) UMUMI_QALIQ_BORC, " +
                    "    (IFNULL(sum(IFNULL(k1.SALES_VOLUME, 0)),0)-IFNULL(sum(IF(IFNULL(k1.ALL_BALANCE, 0)>0,IFNULL(k1.ALL_BALANCE, 0),0)),0)) UMUMI_YIGILMIS_MEBLEG " +
                    " from ( " +
                    "    select p1.last_price SALES_VOLUME, p1.schedule_price MONTHLY_VOLUME, p1.last_price - (select sum(i1.price) " +
                    "    from invoice i1 where i1.is_active=1 and i1.is_approve=1 and i1.sales_id=s1.id) ALL_BALANCE " +
                    " from sales s1, payment p1, organization o " +
                    " where s1.payment_id=p1.id and s1.organization_id=o.id " +
                    "  and s1.is_approve=1 " +
                    Util.checkNull(report.getString1()) + Util.checkNull(report.getString2()) +
                    Util.checkNull(report.getString3()) + Util.checkNull(report.getString4())
                    + Util.checkNull(report.getString5()) + Util.checkNull(report.getString6()) +
                    " ) k1 ";

            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    jsonObjects = Util.getFormattedResult(resultSet);
                }
            }
        } catch(Exception e){
            log.error(e.getMessage(), e);
        }
        return jsonObjects;
    }
}
