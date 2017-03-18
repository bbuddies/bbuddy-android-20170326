package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;

@PresentationModel
@ActivityScope
public class PresentableBudgets implements HasPresentationModelChangeSupport {

    private final Lazy<PresentationModelChangeSupport> presentationModelChangeSupportLoader;
    private final Budgets budgets;

    @Inject
    public PresentableBudgets(Budgets budgets, @Named("budgets") Lazy<PresentationModelChangeSupport> presentationModelChangeSupportLoader) {
        this.budgets = budgets;
        this.presentationModelChangeSupportLoader = presentationModelChangeSupportLoader;
    }

    @ItemPresentationModel(value = PresentableBudget.class)
    public List<Budget> getBudgets() {
        return budgets.getAllBudgets();
    }

    public void refresh() {
        presentationModelChangeSupport().refreshPresentationModel();
    }

    private PresentationModelChangeSupport presentationModelChangeSupport() {
        return presentationModelChangeSupportLoader.get();
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return presentationModelChangeSupport();
    }
}