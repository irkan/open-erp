package com.openerp.util;

import org.apache.log4j.Logger;

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
}
