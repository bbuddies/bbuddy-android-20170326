package com.odde.bbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.di.component.DaggerActivityComponent;
import com.odde.bbuddy.di.module.ActivityModule;

import org.robobinding.ViewBinder;

import javax.inject.Inject;

public class AddAccountActivity extends AppCompatActivity {

    @Inject
    Account account;

    @Inject
    ViewBinder viewBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);

        setContentView(viewBinder.inflateAndBind(R.layout.activity_add_account, account));
    }

}
