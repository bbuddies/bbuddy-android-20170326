package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by zbcjackson on 28/03/2017.
 */

class Budgets {
    private BudgetsApi api;

    public Budgets(BudgetsApi api) {
        this.api = api;
    }

    public void query(final LocalDate startDate, final LocalDate endDate, final Consumer<Float> consumer) {
        api.getAllBudgets(new Consumer<List<Budget>>() {

            @Override
            public void accept(List<Budget> budgets) {
                Period period = new Period(startDate, endDate);

                float sum = 0;

                for (Budget budget : budgets) {
                    sum += budget.getAmountOfOverlappingDays(period);
                }

                consumer.accept(sum);
            }
        });
    }
}
