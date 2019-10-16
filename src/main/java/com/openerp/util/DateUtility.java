package com.openerp.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
    private static final Logger log = Logger.getLogger(DateUtility.class);

    public static Date getUtilDate(String date) {
        Date utilDate= null;
        try {
            utilDate = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e) {
            log.error(e);
        }
        return utilDate;
    }

    public static String getYearMonth(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }

    public static String getFormattedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static String getFormattedDateddMMyy(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
        return dateFormat.format(date);
    }

    public static String getFormattedDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return dateFormat.format(date);
    }

    public static Date generate(int day, int month, int year){
        String date = (day>9?day:"0"+day) + "."+(month>9?month:"0"+month)+"." + year;
        Date utilDate= null;
        try {
            utilDate = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e) {
            log.error(e);
        }
        return utilDate;
    }

    public static String weekDay(int day, int month, int year) {
        String date = (day > 9 ? day : "0" + day) + "." + (month > 9 ? month : "0" + month) + "." + year;
        Date utilDate = null;
        try {
            utilDate = new SimpleDateFormat("dd.MM.yyyy").parse(date);
            String weekDay = "";
            if(utilDate.getDay()==1){
                weekDay="b.e";
            } else if(utilDate.getDay()==2){
                weekDay="ç.a";
            } else if(utilDate.getDay()==3){
                weekDay="ç";
            } else if(utilDate.getDay()==4){
                weekDay="c.a";
            } else if(utilDate.getDay()==5){
                weekDay="c";
            } else if(utilDate.getDay()==6){
                weekDay="ş";
            } else if(utilDate.getDay()==0){
                weekDay="b";
            }
            return weekDay;
        } catch (ParseException e) {
            log.error(e);
        }
        return null;
    }

    public static String generateStringDate(int day, int month, int year){
        return (day > 9 ? day : "0" + day) + "." + (month > 9 ? month : "0" + month) + "." + year;
    }
}
