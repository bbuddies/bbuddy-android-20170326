package com.odde.bbuddy.budget;

import android.util.Log;

import com.odde.bbuddy.common.Consumer;
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
                    try {
                        String startStr = start_date;
                        String endStr = end_date;
                        Date startDate = dateFormat.parse(startStr), endDate = dateFormat.parse(endStr);

                        float sum = 0;
                        int monthSpace = getMonthSpace(startDate,endDate) -1;
                        int index = 0;
                        boolean startCount = false;
                        Log.d("Count", String.valueOf(budgets.size()));
                        for (Budget budget : budgets) {
                            if (monthFormat.format(startDate).equals(budget.getMonth())){
                                sum += Float.valueOf(budget.getAmount()) * getMonthPersent(startDate);
                            }
                            if(monthFormat.format(endDate).equals(budget.getMonth())){
                                if(monthFormat.format(endDate).equals(monthFormat.format(startDate))){
                                    sum=Float.valueOf(budget.getAmount()) * getMonthPersent(startDate)-sum;
                                }else {
                                    sum+=Float.valueOf(budget.getAmount()) * getMonthPersent(startDate);
                                }
                            }
                            if(budget.getMonth().compareTo(monthFormat.format(startDate))>0&&
                                    budget.getMonth().compareTo(monthFormat.format(endDate))<0){
                                sum += Float.valueOf(budget.getAmount());
                            }
                        }

                        budgetsActivityNavigation.showSum(sum);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
    }



    public float getMonthPersent(Date date)  {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return (float)cal.get(Calendar.DATE) / (float)cal.getActualMaximum(Calendar.DATE);
    }

    public int getMonthSpace(Date startDate, Date endDate){
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int startYear = startCal.get(Calendar.YEAR), endYear = endCal.get(Calendar.YEAR);
        int startMonth = startCal.get(Calendar.MONTH), endMonth = endCal.get(Calendar.MONTH);
        return endYear-startYear >= 0 ? ((endYear -startYear)*12 + (endMonth-startMonth)):0;
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
