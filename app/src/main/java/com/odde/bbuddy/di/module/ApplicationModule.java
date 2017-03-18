package com.odde.bbuddy.di.module;

import android.app.Application;

import com.odde.bbuddy.account.model.Accounts;
import com.odde.bbuddy.budget.Budgets;
import com.odde.bbuddy.common.JsonBackend;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton
    public JsonBackend provideJsonBackend() {
        return new JsonBackend(application);
    }

    @Provides @Singleton
    public Accounts provideAccounts(JsonBackend jsonBackend) {
        return new Accounts(jsonBackend);
    }

    @Provides @Singleton
    public Budgets provideBudgets() {
        return new Budgets();
    }

}
