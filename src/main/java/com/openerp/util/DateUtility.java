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

    public static String getFormattedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public static String getFormattedDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy hh:mm");
        return dateFormat.format(date);
    }
}
