package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.PresentationModel;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class EditableBudget {

    private final BudgetsActivityNavigation budgetsActivityNavigation;

    @Inject
    public EditableBudget(BudgetsActivityNavigation budgetsActivityNavigation) {
        this.budgetsActivityNavigation = budgetsActivityNavigation;
    }

    public void add() {
        budgetsActivityNavigation.navigate();
    }
}
