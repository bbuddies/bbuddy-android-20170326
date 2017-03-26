package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import static java.util.Arrays.asList;

@ActivityScope
public class BudgetsApi {

    @Inject
    public BudgetsApi() {
    }

    public void addBudget(Budget budget) {
    }

    public List<Budget> getAllBudgets() {
        Budget budget = new Budget();
        budget.setMonth("2017-03");
        budget.setAmount(1000);
        return asList(budget);
    }
}
