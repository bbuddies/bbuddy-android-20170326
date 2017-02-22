package com.odde.bbuddy.account.view;

import android.content.Context;
import android.content.Intent;

import com.odde.bbuddy.EditAccountActivity;
import com.odde.bbuddy.account.viewmodel.Account;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class EditAccount {

    private final Context context;

    @Inject
    public EditAccount(Context context) {
        this.context = context;
    }

    public void navigate(Account account) {
        Intent intent = new Intent(context, EditAccountActivity.class);
        intent.putExtra("account", account);
        context.startActivity(intent);
    }
}
