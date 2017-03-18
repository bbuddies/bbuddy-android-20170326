package com.odde.bbuddy.budget;

import java.util.ArrayList;
import java.util.List;

public class Budgets {

    private final List<Budget> allBudgets = new ArrayList<>();

    public void add(Budget budget) {
        allBudgets.add(budget);
    }

    public List<Budget> getAllBudgets() {
        return allBudgets;
    }
}
