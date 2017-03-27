package com.odde.bbuddy.budget;

import android.app.Activity;
import android.widget.Toast;

import com.odde.bbuddy.di.scope.ActivityScope;

import javax.inject.Inject;

@ActivityScope
public class BudgetsActivityNavigation {


    private final Activity activity;

    @Inject
    public BudgetsActivityNavigation(Activity activity) {
        this.activity = activity;
    }

    public void navigate() {
        activity.finish();
    }

    public void showSum(final double sum) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity.getApplication(),
                        String.valueOf(sum), Toast.LENGTH_SHORT).show();
            }
        });
        activity.finish();
    }
}
