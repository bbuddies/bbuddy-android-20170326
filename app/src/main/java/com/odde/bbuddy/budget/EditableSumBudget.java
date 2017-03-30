package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.Singleton;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.joda.time.LocalDate;
import org.robobinding.annotation.PresentationModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static java.util.Arrays.asList;

/**
 * Created by lizz on 2017/3/27.
 */

@PresentationModel
@ActivityScope
public class EditableSumBudget {

    private final BudgetsActivityNavigation budgetsActivityNavigation;
    private final BudgetsApi budgetsApi;
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
        Budgets budgets = new Budgets(budgetsApi);
        LocalDate startDate = LocalDate.parse(start_date);
        LocalDate endDate = LocalDate.parse(end_date);
        budgets.query(startDate, endDate, new Consumer<Float>(){

            @Override
            public void accept(Float sum) {
                Singleton.singleton.getSumBudgets().setSumAmount(String.valueOf(sum));
                budgetsActivityNavigation.navigate();
            }
        });
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
