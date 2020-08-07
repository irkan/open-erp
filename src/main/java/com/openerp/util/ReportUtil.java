package com.openerp.util;

import com.openerp.domain.Report;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReportUtil {
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
}
