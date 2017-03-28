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

    public  double getTotalBudget(List<Budget> budgets, Date startDate, Date endDate){
        double totalAmount = 0;
        if (budgets == null){
            return 0;
        }
        for(Budget budeget : budgets) {
            String month = budeget.getMonth();
            double amount = budeget.getAmount();
            long start = startDate.getTime();
            long end = endDate.getTime();
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

    private long getDaysBy2Date (long date1, long date2){
        return (date2 - date1)/1000/60/60/24 + 1;
    }

    private int getTotalDays(String month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    private long getFirstDate(String month){
        Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        //calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7))-1);
        //calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Integer.parseInt(month.substring(0,4)),Integer.parseInt(month.substring(5,7))-1,1,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);

        return calendar.getTimeInMillis();
    }

    private long getLastDate(String month){
        Calendar calendar = Calendar.getInstance();
        //calendar.set(Calendar.YEAR,Integer.parseInt(month.substring(0,4)));
        //calendar.set(Calendar.MONTH, Integer.parseInt(month.substring(5,7)));
        //calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Integer.parseInt(month.substring(0,4)),Integer.parseInt(month.substring(5,7)),1,0,0,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(calendar.DATE,-1);
        return calendar.getTimeInMillis();
    }

    private long getDate(String date){
        Calendar calendar =  Calendar.getInstance();
        calendar.set(Calendar.YEAR,Integer.parseInt(date.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(date.substring(5,7))-1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(8,10)));
        return calendar.getTimeInMillis();
    }


}
