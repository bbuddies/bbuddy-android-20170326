package com.odde.bbuddy.account.view;

import android.app.Activity;

import com.odde.bbuddy.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class ShowAllAccountsNavigation {

    private final Activity activity;

    @Inject
    public ShowAllAccountsNavigation(Activity activity) {
        this.activity = activity;
    }

    public void navigate() {
        activity.finish();
    }
}
