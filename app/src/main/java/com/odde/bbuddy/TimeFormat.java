package com.odde.bbuddy;

import com.odde.bbuddy.budget.Budget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by sharp on 2017/3/27.
 */

public class TimeFormat {

    public static String getFormatTime(Date date){
        //Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        return simpleDateFormat.format(date);
    }

    public static double getTotalBudget(List<Budget> budgets, String startDate, String endDate){
        double totalAmount = 0;
        for(Budget budeget : budgets) {
            String month = budeget.getMonth();
            double amount = budeget.getAmount();
            long start = getDate(startDate);
            long end = getDate(endDate);
            long first = getFirstDate(month);
            long last = getLastDate(month);
            int days = getTotalDays(month);
            if (start > last){
                continue;
            } else if ( end < first){
                continue;
            } else if (first <= start){

                if(end < last){
                    totalAmount += (amount/days)*getDaysBy2Date(start,end);
                } else {
                    totalAmount += (amount / days) * getDaysBy2Date(start, last);
                }
            } else if (last < end) {

                totalAmount += (amount/days)*getDaysBy2Date(first,last);
            } else if (first < end) {

                totalAmount += (amount/days)*getDaysBy2Date(first,end);
            } else {
                totalAmount += (amount/days)*getDaysBy2Date(start,end);
            }

        }
        return totalAmount;
    }

    private static long getDaysBy2Date (long date1, long date2){
        return (date2 - date1)/1000/60/60/24 + 1;
    }

    private static int getTotalDays(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private static long getFirstDate(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTimeInMillis();
    }

    private static long getLastDate(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7)));
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(calendar.DATE,-1);
        return calendar.getTimeInMillis();
    }

    private static long getDate(String date){
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(date.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(8,10)));
        return calendar.getTimeInMillis();
    }

    /**
     * 日期转换成字符串
     * @param date
     * @return str
     */
    public static String DateToStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
             date = format.parse(str);
         } catch (ParseException e) {
             e.printStackTrace();
        }
         return date;
    }



}
