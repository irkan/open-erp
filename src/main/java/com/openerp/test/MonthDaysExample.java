package com.openerp.test;

import java.util.Calendar;

public class MonthDaysExample {
    public static void main(String[] args) {
        // First get an instance of calendar object.
        Calendar calendar = Calendar.getInstance();

        // We'll set the date of the calendar to the following
        // date. We can use constant variable in the calendar
        // for months value (JANUARY - DECEMBER). Be informed that
        // month in Java started from 0 instead of 1.
        int year = 2017;
        int month = Calendar.FEBRUARY;
        int date = 1;
        // We have a new date of 2017-02-01
        calendar.set(year, month, date);

        // Here we get the maximum days for the date specified
        // in the calendar. In this case we want to get the number
        // of days for february 2017
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("Max Day: " + maxDay);

        // Here we want to see what is the days for february on
        // a leap year.
        calendar.set(2016, Calendar.FEBRUARY, 1);
        maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("Max Day: " + maxDay);
    }
}