package com.odde.bbuddy.budget;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PresentableBudgetsTest {

    @Test
    public void get_all_budgets() {
        Budget budget = new Budget();
        budget.setMonth("2017-01");
        budget.setAmount(1000);
        Budgets stubBudgets = mock(Budgets.class);
        when(stubBudgets.getAllBudgets()).thenReturn(asList(budget));
        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgets);

        List<Budget> actual = presentableBudgets.getBudgets();

        assertEquals(asList(budget), actual);
    }

}