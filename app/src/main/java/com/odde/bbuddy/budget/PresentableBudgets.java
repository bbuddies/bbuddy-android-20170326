package com.odde.bbuddy.budget;

import android.util.Log;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class PresentableBudgets implements HasPresentationModelChangeSupport {

    private final BudgetsApi budgetsApi;
    private List<Budget> allBudgets;
    private PresentationModelChangeSupport presentationModelChangeSupport = new PresentationModelChangeSupport(this);

    @Inject
    public PresentableBudgets(BudgetsApi budgetsApi) {
        this.budgetsApi = budgetsApi;
        budgetsApi.getAllBudgets(new Consumer<List<Budget>>() {
            @Override
            public void accept(List<Budget> budgets) {
                allBudgets = budgets;
                presentationModelChangeSupport.refreshPresentationModel();
            }
        });
    }

    @ItemPresentationModel(PresentableBudget.class)
    public List<Budget> getBudgets() {
        return allBudgets;
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return presentationModelChangeSupport;
    }

    public void refresh() {
        budgetsApi.getAllBudgets(new Consumer<List<Budget>>() {
            @Override
            public void accept(List<Budget> budgets) {
                allBudgets = budgets;
                presentationModelChangeSupport.refreshPresentationModel();
            }
        });
    }

    public void searchTotalBudgets(Date startDate, Date endDate, Consumer<List<Budget>> consumer) {
        budgetsApi.getAllBudgets(consumer);
    }
}
