package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudgetCopy {

    private final BudgetsActivityNavigation budgetsActivityNavigation;
    private final BudgetsApi budgetsApi;

    private String month;
    private String amount;

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateFormat monthFormat = new SimpleDateFormat("yyyy-MM");


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Inject
    public EditableBudgetCopy(BudgetsActivityNavigation budgetsActivityNavigation, BudgetsApi budgetsApi) {
        this.budgetsActivityNavigation = budgetsActivityNavigation;
        this.budgetsApi = budgetsApi;
    }

    public void add() {
        budgetsApi.getAllBudgets(new Consumer<List<Budget>>() {
            String startStr = "2017-01-02";
            String endStr = "2017-03-04";
            Date startDate = dateFormat.parse(startStr), endDate = dateFormat.parse(endStr);
            @Override
            public void accept(List<Budget> budgets) {
                float sum = 0;
                int monthSpace = getMonthSpace(startDate,endDate) -1;
                int index = 0;
                boolean startCount = false;
                for (Budget budget : budgets) {
                    if(startCount){
                        index++;
                        if (index < monthSpace) {
                            sum += Float.valueOf(budget.getAmount());
                        }else{
                            sum += Float.valueOf(budget.getAmount()) * getMonthPersent(endDate);
                        }
                    }
                    if (monthFormat.format(startDate).equals(budget.getMonth())){
                        sum += Float.valueOf(budget.getAmount()) * getMonthPersent(startDate);
                        startCount = true;
                    }
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
