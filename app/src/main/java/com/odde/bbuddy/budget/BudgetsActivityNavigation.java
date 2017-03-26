package com.odde.bbuddy.budget;

import android.app.Activity;

import javax.inject.Inject;

public class BudgetsActivityNavigation {


    private final Activity activity;

    @Inject
    public BudgetsActivityNavigation(Activity activity) {
        this.activity = activity;
    }

    public void navigate() {
        activity.finish();
    }
}
