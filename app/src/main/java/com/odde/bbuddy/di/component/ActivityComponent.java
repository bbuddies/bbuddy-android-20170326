package com.odde.bbuddy.di.component;

import com.odde.bbuddy.LoginActivity;
import com.odde.bbuddy.account.view.AddAccountActivity;
import com.odde.bbuddy.account.view.EditDeleteAccountActivity;
import com.odde.bbuddy.account.view.ShowAllAccountsActivity;
import com.odde.bbuddy.budget.AddBudgetActivity;
import com.odde.bbuddy.budget.BudgetsActivity;
import com.odde.bbuddy.di.module.ActivityModule;
import com.odde.bbuddy.di.scope.ActivityScope;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(AddAccountActivity addAccountActivity);
    void inject(ShowAllAccountsActivity showAllAccountsActivity);
    void inject(EditDeleteAccountActivity editDeleteAccountActivity);
    void inject(LoginActivity loginActivity);
    void inject(AddBudgetActivity addBudgetActivity);
    void inject(BudgetsActivity budgetsActivity);
}
