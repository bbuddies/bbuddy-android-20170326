package com.odde.bbuddy.budget;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lizz on 2017/3/28.
 */

public final class BudgetSumUtil {
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

    public static float getBudgetsSum(String start_date, String end_date, List<Budget> budgets) {
        Date startDate = parseDateFromString(start_date), endDate = parseDateFromString(end_date);
        String startMonthStr = formatMonthStringFromDate(startDate), endMonthStr = formatMonthStringFromDate(endDate);

        float sum = 0;

        for (Budget budget : budgets) {
            String month = budget.getMonth();
            float amount = Float.valueOf(budget.getAmount());
            if (startMonthStr.equals(month)) {
                sum += (1 - getMonthPercent(startDate, true)) * amount;
            } else if (endMonthStr.equals(month)){
                sum += getMonthPercent(endDate, false) * amount;
            } else if (isAgtB(month, startMonthStr) && isAgtB(endMonthStr, month)){
                sum += amount;
            }
        }

        return sum;
    }


    private static float getMonthPercent(Date date, boolean isStartDate)  {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayCount = cal.get(Calendar.DATE);
        if(isStartDate){
            dayCount--;
        }
        return (float)dayCount / (float)cal.getActualMaximum(Calendar.DATE);
    }

    private static boolean isAgtB(String a, String b){
        return a.compareTo(b) > 0 ;
    }

    private static Date parseDateFromString(String dateString){
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e){
            throw new IllegalArgumentException();
        }
    }

    private static String formatMonthStringFromDate(Date date){
        return monthFormat.format(date);
    }

}
