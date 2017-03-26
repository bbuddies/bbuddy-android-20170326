package com.odde.bbuddy.budget;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

public class PresentableBudget implements ItemPresentationModel<Budget> {
    private Budget budget;

    @Override
    public void updateData(Budget budget, ItemContext itemContext) {
        this.budget = budget;
    }

    public String getDisplayOfBudget() {
        return budget.getMonth() + " " + budget.getAmount();
    }
}
