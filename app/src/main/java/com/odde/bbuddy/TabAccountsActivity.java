package com.odde.bbuddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.odde.bbuddy.account.viewmodel.PresentableAccounts;
import com.odde.bbuddy.di.component.DaggerActivityComponent;
import com.odde.bbuddy.di.module.ActivityModule;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;

import javax.inject.Inject;


public class TabAccountsActivity extends Fragment {

    @Inject
    PresentableAccounts presentableAccounts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder().activityModule(new ActivityModule(getActivity())).build().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewBinder viewBinder = createViewBinder();
        return viewBinder.inflateAndBindWithoutAttachingToRoot(R.layout.activity_adapter_view, presentableAccounts, container);
    }

    private ViewBinder createViewBinder() {
        BinderFactory reusableBinderFactory = new BinderFactoryBuilder().build();
        return reusableBinderFactory.createViewBinder(getActivity());
    }

}
