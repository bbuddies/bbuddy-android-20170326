package com.odde.bbuddy.budget;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    @Test
    public void add_should_navigate_to_budgets_activity() {
        BudgetsActivityNavigation mockBudgetsActivityNavigation = mock(BudgetsActivityNavigation.class);
        EditableBudget editableBudget = new EditableBudget(mockBudgetsActivityNavigation);

        editableBudget.add();

        verify(mockBudgetsActivityNavigation).navigate();
    }

}