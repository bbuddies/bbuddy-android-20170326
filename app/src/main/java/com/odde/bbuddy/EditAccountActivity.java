package com.odde.bbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.odde.bbuddy.account.viewmodel.Account;

import org.robobinding.ViewBinder;

import javax.inject.Inject;

import static com.odde.bbuddy.di.component.ActivityComponentFactory.createActivityComponentBy;

public class EditAccountActivity extends AppCompatActivity {

    @Inject
    ViewBinder viewBinder;

    @Inject
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createActivityComponentBy(this).inject(this);

        setContentView(viewBinder.inflateAndBind(R.layout.activity_edit_account, accountWithDataFromIntent()));
    }

    private Account accountWithDataFromIntent() {
        Account accountFromIntent = (Account) getIntent().getSerializableExtra("account");
        account.setId(accountFromIntent.getId());
        account.setName(accountFromIntent.getName());
        account.setBalanceBroughtForward(accountFromIntent.getBalanceBroughtForward());
        return account;
    }
}
