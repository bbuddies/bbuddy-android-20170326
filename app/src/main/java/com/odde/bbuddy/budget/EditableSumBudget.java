package com.odde.bbuddy.budget;

import android.util.Log;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.Singleton;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by lizz on 2017/3/27.
 */

@PresentationModel
@ActivityScope
public class EditableSumBudget {

    private final BudgetsActivityNavigation budgetsActivityNavigation;
    private final BudgetsApi budgetsApi;
    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
    private String start_date;
    private String end_date;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Inject
    public EditableSumBudget(BudgetsActivityNavigation budgetsActivityNavigation, BudgetsApi budgetsApi) {
        this.budgetsActivityNavigation = budgetsActivityNavigation;
        this.budgetsApi = budgetsApi;
    }

    public void sum() {
            budgetsApi.getAllBudgets(new Consumer<List<Budget>>() {

                @Override
                public void accept(List<Budget> budgets) {
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
                        Singleton.singleton.getSumBudgets().setSumAmount(String.valueOf(sum));
                        budgetsActivityNavigation.navigate();
                }
            });
    }



    private float getMonthPercent(Date date, boolean isStartDate)  {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayCount = cal.get(Calendar.DATE);
        if(isStartDate){
            dayCount--;
        }
        return (float)dayCount / (float)cal.getActualMaximum(Calendar.DATE);
    }

    private boolean isAgtB(String a, String b){
        return a.compareTo(b) > 0 ;
    }

    private Date parseDateFromString(String dateString){
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e){
            throw new IllegalArgumentException();
        }
    }

    private String formatMonthStringFromDate(Date date){
        return monthFormat.format(date);
    }




    public Runnable refreshRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                budgetsActivityNavigation.navigate();
            }
        };
    }
}
