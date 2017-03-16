package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudget {

    private final ShowAllBudgetsNavigation showAllBudgetsNavigation;
    private String month;
    private String amount;

    @Inject
    public EditableBudget(ShowAllBudgetsNavigation showAllBudgetsNavigation) {
        this.showAllBudgetsNavigation = showAllBudgetsNavigation;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getMonth() {
        return month;
    }

    public String getAmount() {
        return amount;
    }

    public void add(){
        showAllBudgetsNavigation.navigate();
    }
}
