package com.openerp.util;

import com.openerp.entity.Dictionary;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtility {

    private static final Logger log = Logger.getLogger(DateUtility.class);

    public static Date getUtilDateFromEmailAnalyzer(String date) {
        Date utilDate= null;
        try {
            utilDate = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss").parse(date);
        } catch (ParseException e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return utilDate;
    }

    public static Date getUtilDate(String date) {
        Date utilDate= null;
        try {
            utilDate = new SimpleDateFormat("dd.MM.yyyy").parse(date);
        } catch (ParseException e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
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
        date.setYear(date.getYear()-200);
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
        } catch (ParseException e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
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
        } catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static String generateStringDate(int day, int month, int year){
        return (day > 9 ? day : "0" + day) + "." + (month > 9 ? month : "0" + month) + "." + year;
    }

    public static String generateContractDate(Date contractDate, List<Dictionary> months){
        String monthName = "";
        for(Dictionary month: months){
            if(contractDate.getMonth()==Integer.parseInt(month.getAttr2())){
                monthName = month.getName();
                break;
            }
        }
        return "«"+contractDate.getDate()+"» " + monthName + " " + (contractDate.getYear()+1900);

    }

    public static String calculateTime(Date date){
        String returned = "";
        long value = (new Date()).getTime()-date.getTime();
        int a = 0;
        if(value>86400000){
            returned+=Math.round(value/86400000) + " gün ";
            value = value - Math.round(value/86400000)*86400000;
            a++;
        }
        if(value>3600000 && a<3){
            returned+=Math.round(value/3600000) + " saat ";
            value = value - Math.round(value/3600000)*3600000;
            a++;
        }
        if(value>60000 && a<2){
            returned+=Math.round(value/60000) + " dəqiqə ";
            value = value - Math.round(value/60000)*60000;
            a++;
        }
        if(value>1000 && a<1){
            returned+=Math.round(value/1000) + " saniyə ";
        }
        returned = returned.trim().length()>0?returned+"əvvəl":"indi";
        return returned;
    }

    public static Date addYear(int year){
        Calendar cal = Calendar.getInstance();
        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, year);
        return cal.getTime();
    }

    public static Date addMonth(int date, int month, int year, int value){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal = Calendar.getInstance();
        cal.setTime(getUtilDate(generateStringDate(date, month, year)));
        cal.add(Calendar.MONTH, value);
        return cal.getTime();
    }

    public static Date addMonth(Date date, int value){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, value);
        return cal.getTime();
    }

    public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static String getMonthTextAZE(List<Dictionary> months, int value){
        for(Dictionary dictionary: months){
            if(Util.parseInt(dictionary.getAttr2())==value){
                return dictionary.getName();
            }
        }
        return "";
    }

    public static String findMonthShortName(int month){
        switch (month){
            case 1: return "yan";
            case 2: return "fev";
            case 3: return "mar";
            case 4: return "apr";
            case 5: return "may";
            case 6: return "iyn";
            case 7: return "iyl";
            case 8: return "avq";
            case 9: return "sen";
            case 10: return "okt";
            case 11: return "noy";
            case 12: return "dek";
        }
        return "";
    }

    public static String findMonthName(int month){
        switch (month){
            case 1: return "yanvar";
            case 2: return "fevral";
            case 3: return "mart";
            case 4: return "aprel";
            case 5: return "may";
            case 6: return "iyun";
            case 7: return "iyul";
            case 8: return "avqust";
            case 9: return "sentyabr";
            case 10: return "oktyabr";
            case 11: return "noyabr";
            case 12: return "dekabr";
        }
        return "";
    }
}
