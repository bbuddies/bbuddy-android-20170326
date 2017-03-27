package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import java.util.List;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudget {

    private final BudgetsActivityNavigation budgetsActivityNavigation;
    private final BudgetsApi budgetsApi;

    private String month;
    private String amount;

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
    public EditableBudget(BudgetsActivityNavigation budgetsActivityNavigation, BudgetsApi budgetsApi) {
        this.budgetsActivityNavigation = budgetsActivityNavigation;
        this.budgetsApi = budgetsApi;
    }

    public void add() {
        budgetsApi.getAllBudgets(new Consumer<List<Budget>>() {
            @Override
            public void accept(List<Budget> budgets) {
                Budget budgetTobeAdded = new Budget();
                budgetTobeAdded.setMonth(month);
                budgetTobeAdded.setAmount(Integer.parseInt(amount));
                for (Budget budget : budgets) {
                    if (budget.getMonth().equals(month)) {
                        budgetTobeAdded.setId(budget.getId());
                        budgetsApi.updateBudget(budgetTobeAdded, refreshRunnable());

                        return;
                    }
                }
                budgetsApi.addBudget(budgetTobeAdded, refreshRunnable());
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
