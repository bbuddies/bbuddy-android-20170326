package com.odde.bbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.di.component.DaggerActivityComponent;
import com.odde.bbuddy.di.module.ActivityModule;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

import javax.inject.Inject;

public class AddAccountActivity extends AppCompatActivity {

    @Inject
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerActivityComponent.builder().activityModule(new ActivityModule(this)).build().inject(this);

        ViewBinder viewBinder = createViewBinder();
        View rootView = viewBinder.inflateAndBind(R.layout.activity_add_account, account);
        setContentView(rootView);
    }

    private ViewBinder createViewBinder() {
        BinderFactory reusableBinderFactory = new BinderFactoryBuilder().build();
        return reusableBinderFactory.createViewBinder(this);
    }

}
