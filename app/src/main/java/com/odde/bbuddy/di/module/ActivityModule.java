package com.odde.bbuddy.di.module;

import android.app.Activity;
import android.content.Context;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactoryBuilder;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Context provideContext() {
        return activity;
    }

    @Provides
    ViewBinder provideViewBinder() {
        return new BinderFactoryBuilder().build().createViewBinder(activity);
    }

}
