package com.odde.bbuddy.di.component;

import com.odde.bbuddy.AddAccountActivity;
import com.odde.bbuddy.TabAccountsActivity;
import com.odde.bbuddy.di.module.ActivityModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(AddAccountActivity addAccountActivity);
    void inject(TabAccountsActivity tabAccountsActivity);
}
