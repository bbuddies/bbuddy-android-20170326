package com.odde.bbuddy.budget;

import com.odde.bbuddy.di.scope.ActivityScope;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PresentationModel
@ActivityScope
public class PresentableBudgets {

    private List<Budget> allBudgets = new ArrayList<Budget>() {{
        Budget budget = new Budget();
        budget.setMonth("2017-01");
        budget.setAmount(1000);
        add(budget);
    }};

    @Inject
    public PresentableBudgets() { }

    @ItemPresentationModel(value = PresentableBudget.class)
    public List<Budget> getBudgets() {
        return allBudgets;
    }


}
