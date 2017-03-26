package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

import java.util.List;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class PresentableBudgets {

    private final BudgetsApi budgetsApi;

    @Inject
    public PresentableBudgets(BudgetsApi budgetsApi) {
        this.budgetsApi = budgetsApi;
    }

    @ItemPresentationModel(PresentableBudget.class)
    public List<Budget> getBudgets() {
        return budgetsApi.getAllBudgets();
    }
}
