package com.xpert.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Ulity class to use with java.util.Date and java.util.Calendar
 *
 * @author Ayslan
 */
public class DateUtils {

    /**
     * Return the year of a date. Example: '2014-01-02' (yyyy-MM-dd) returns
     * '2014'
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * Return the month of a date. Example: '2014-01-02' (yyyy-MM-dd) returns
     * '01'
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * Return the day of a date. Example: '2014-01-02' (yyyy-MM-dd) returns '02'
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * Format a date from the parameter pattern
     *
     * @param date
     * @param pattern Date pattern. Example: dd/MM/yyyy
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * Parse a string from the parameter pattern
     *
     * @param date
     * @param pattern Date pattern. Example: dd/MM/yyyy
     * @return
     */
    public static String dateToString(Calendar date, String pattern) {
        return dateToString(date.getTime(), pattern);
    }

    /**
     * Parse a string with the parameter pattern
     *
     * @param dateString
     * @param pattern Date pattern. Example: dd/MM/yyyy
     * @return
     * @throws java.text.ParseException
     */
    public static Date stringToDate(String dateString, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(dateString);
    }

    /**
     * Parse a string with the parameter pattern
     *
     * @param dateString
     * @param pattern Date pattern. Example: dd/MM/yyyy
     * @return
     * @throws java.text.ParseException
     */
    public static Calendar stringToCalendar(String dateString, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = format.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
