package com.openerp.util;

import com.openerp.domain.Report;
import com.openerp.entity.Advance;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportUtil {
    private static final Logger log = Logger.getLogger(ReportUtil.class);

    public static List<JSONObject> correct(List<JSONObject> objects, Report report){
        List<JSONObject> newList = new ArrayList<>();

        Date startDate;
        Date endDate;
        Calendar start;
        Calendar end;

        switch (Util.parseInt(report.getInteger1())){
            case 1:
                startDate = DateUtility.addDay(-31);
                endDate = DateUtility.addDay(0);
                start = Calendar.getInstance();
                start.setTime(startDate);
                end = Calendar.getInstance();
                end.setTime(endDate);

                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    boolean status = false;
                    for(JSONObject jsonObject: objects){
                        if(date.getDate()==Util.parseInt(jsonObject.getString("ZAXIS"))){
                            newList.add(jsonObject);
                            status=true;
                        }
                    }
                    if(!status){
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("XAXIS", Util.checkNull(date.getDate())+","+DateUtility.findMonthName(date.getMonth()+1));
                        jsonObj.put("ZAXIS", Util.checkNull(date.getDate()));
                        jsonObj.put("YAXIS", "0");
                        newList.add(jsonObj);
                    }
                }
                break;
            case 2:
                startDate = DateUtility.addMonth(-13);
                endDate = DateUtility.addMonth(1);
                start = Calendar.getInstance();
                start.setTime(startDate);
                end = Calendar.getInstance();
                end.setTime(endDate);

                for (Date date = start.getTime(); start.before(end); start.add(Calendar.MONTH, 1), date = start.getTime()) {
                    boolean status = false;
                    for(JSONObject jsonObject: objects){
                        if(date.getMonth()+1==Util.parseInt(jsonObject.getString("ZAXIS")) && date.getYear()+1900==Util.parseInt(jsonObject.getString("XAXIS"))){
                            newList.add(jsonObject);
                            status=true;
                        }
                    }
                    if(!status){
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("ZAXIS", Util.checkNull(DateUtility.findMonthName(date.getMonth()+1)));
                        jsonObj.put("XAXIS", Util.checkNull(date.getYear()+1900));
                        jsonObj.put("YAXIS", "0");
                        jsonObj.put("MAXIS", "0");
                        newList.add(jsonObj);
                    }
                }
                break;
            case 3:
                startDate = DateUtility.addYear(-9);
                endDate = DateUtility.addYear(1);
                start = Calendar.getInstance();
                start.setTime(startDate);
                end = Calendar.getInstance();
                end.setTime(endDate);

                for (Date date = start.getTime(); start.before(end); start.add(Calendar.YEAR, 1), date = start.getTime()) {
                    boolean status = false;
                    for(JSONObject jsonObject: objects){
                        if(date.getYear()+1900==Util.parseInt(jsonObject.getString("XAXIS"))){
                            newList.add(jsonObject);
                            status=true;
                        }
                    }
                    if(!status){
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("XAXIS", Util.checkNull(date.getYear()+1900));
                        jsonObj.put("ZAXIS", Util.checkNull(date.getYear()+1900));
                        jsonObj.put("YAXIS", "0");
                        newList.add(jsonObj);
                    }
                }
                break;
            default:
                startDate = DateUtility.addDay(-8);
                endDate = DateUtility.addDay(0);
                start = Calendar.getInstance();
                start.setTime(startDate);
                end = Calendar.getInstance();
                end.setTime(endDate);

                for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {
                    boolean status = false;
                    for(JSONObject jsonObject: objects){
                        if(date.getDate()==Util.parseInt(jsonObject.getString("XAXIS"))){
                            newList.add(jsonObject);
                            status=true;
                        }
                    }
                    if(!status){
                        JSONObject jsonObj = new JSONObject();
                        jsonObj.put("XAXIS", Util.checkNull(date.getDate()));
                        jsonObj.put("ZAXIS", Util.checkNull(date.getDate()));
                        jsonObj.put("YAXIS", "0");
                        newList.add(jsonObj);
                    }
                }
                break;
        }
        return newList;
    }


    public static Report calculateAdvance(List<Advance> advances){
        Report report = new Report();
        try{
            Date today = new Date();
            Integer notApproveCount = 0;
            Double notApproveAmount = 0d;
            Integer notApproveMonthlyCount = 0;
            Double notApproveMonthlyAmount = 0d;
            Integer approveCount = 0;
            Double approveAmount = 0d;
            Integer approveMonthlyCount = 0;
            Double approveMonthlyAmount = 0d;
            if(advances.size()>0){
                for(Advance advance: advances){
                    try{
                        if(!advance.getApprove()){
                            notApproveCount++;
                            notApproveAmount+=advance.getPayed();
                            if(advance.getAdvanceDate()!=null && advance.getAdvanceDate().getYear()==today.getYear() && advance.getAdvanceDate().getMonth()==today.getMonth()){
                                approveMonthlyCount++;
                                approveMonthlyAmount+=advance.getPayed();
                            }
                        } else {
                            approveCount++;
                            approveAmount+=advance.getPayed();
                            if(advance.getApproveDate()!=null && advance.getApproveDate().getYear()==today.getYear() && advance.getApproveDate().getMonth()==today.getMonth()){
                                approveMonthlyCount++;
                                approveMonthlyAmount+=advance.getPayed();
                            }
                        }
                    } catch (Exception e){
                        log.error(e.getMessage(), e);
                    }
                }
                report.setInteger1(notApproveCount);
                report.setDouble1(notApproveAmount);
                report.setInteger2(notApproveMonthlyCount);
                report.setDouble2(notApproveMonthlyAmount);
                report.setInteger3(approveCount);
                report.setDouble3(approveAmount);
                report.setInteger4(approveMonthlyCount);
                report.setDouble4(approveMonthlyAmount);
            }
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return report;
    }
}
