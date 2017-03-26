package com.odde.bbuddy.budget;

import android.util.Log;

import com.odde.bbuddy.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class BudgetsApi {

    @Inject
    public BudgetsApi() {
    }

    public void addBudget(Budget budget) {
        Log.d("month", budget.getMonth());
        Log.d("amount", String.valueOf(budget.getAmount()));
    }
}
