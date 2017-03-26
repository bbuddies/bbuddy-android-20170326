package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

import java.util.List;

import javax.inject.Inject;

import static java.util.Arrays.asList;

@PresentationModel
@ActivityScope
public class PresentableBudgets {

    @Inject
    public PresentableBudgets() {

    }

    @ItemPresentationModel(PresentableBudget.class)
    public List<Budget> getBudgets() {
        Budget budget = new Budget();
        budget.setMonth("2017-03");
        budget.setAmount(1000);
        return asList(budget);
    }
}
