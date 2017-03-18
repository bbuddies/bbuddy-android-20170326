package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.util.List;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class PresentableBudgets implements HasPresentationModelChangeSupport {

    private List<Budget> allBudgets;
    private final PresentationModelChangeSupport presentationModelChangeSupport = new PresentationModelChangeSupport(this);

    @Inject
    public PresentableBudgets(Budgets budgets) {
        allBudgets = budgets.getAllBudgets();
    }

    @ItemPresentationModel(value = PresentableBudget.class)
    public List<Budget> getBudgets() {
        return allBudgets;
    }

    public void refresh() {
        presentationModelChangeSupport.refreshPresentationModel();
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return presentationModelChangeSupport;
    }
}