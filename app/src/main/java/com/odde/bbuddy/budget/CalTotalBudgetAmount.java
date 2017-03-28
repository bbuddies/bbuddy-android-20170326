package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sharp on 2017/3/28.
 */


@ActivityScope
public class CalTotalBudgetAmount {

    @Inject
    public CalTotalBudgetAmount(){

    }

    public double calOneMonthAmount(Budget budeget, long calStart, long calEnd){
        String month = budeget.getMonth();
        double amount = budeget.getAmount();
        int days = getTotalDays(month);
        return (amount/days)*getDaysBy2Date(calStart,calEnd);
    }

    public double getMonthAmount(Budget budeget,Date startDate, Date endDate){
        long start = startDate.getTime();
        long end = endDate.getTime();

        if (start > end){
            return 0;
        }

        String month = budeget.getMonth();
        long firstDayOfMonth = getFirstDateOfMonth(month);
        long lastDayOfMonth = getLastDateOfMonth(month);
        double totalAmount = 0;

        if (firstDayOfMonth < start) {
            if (end < start){
                return 0;
            } else if (lastDayOfMonth >= start && lastDayOfMonth <= end){
                totalAmount = calOneMonthAmount(budeget,start,lastDayOfMonth);
            } else  {
                totalAmount = calOneMonthAmount(budeget,start,end);
            }
        } else if (firstDayOfMonth >= start && firstDayOfMonth <= end) {
            if (lastDayOfMonth <= end){
                totalAmount = calOneMonthAmount(budeget,firstDayOfMonth,lastDayOfMonth);
            } else {
                totalAmount = calOneMonthAmount(budeget,firstDayOfMonth,end);
            }
        }
        return totalAmount;
    }

    public  double getTotalBudget(List<Budget> budgets, Date startDate, Date endDate){

        if (budgets == null){
            return 0;
        }

        double totalAmount = 0;

        for(Budget budeget : budgets) {
            totalAmount += getMonthAmount(budeget,startDate,endDate);
        }
        return totalAmount;
    }

    private long getDaysBy2Date (long startDate, long endDate){
        return (endDate - startDate)/1000/60/60/24 + 1;
    }

    private int getTotalDays(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private long getFirstDateOfMonth(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(month.substring(0,4)),Integer.parseInt(month.substring(5,7))-1,1,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTimeInMillis();
    }

    private long getLastDateOfMonth(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(month.substring(0,4)),Integer.parseInt(month.substring(5,7)),1,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(calendar.DATE,-1);
        return calendar.getTimeInMillis();
    }



}
