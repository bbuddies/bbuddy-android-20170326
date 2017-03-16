package com.odde.bbuddy.budget;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    @Test
    public void add_should_show_all_budgets() {
        ShowAllBudgetsNavigation mockShowAllBudgetsNavigation = mock(ShowAllBudgetsNavigation.class);
        EditableBudget editableBudget = new EditableBudget(mockShowAllBudgetsNavigation);

        editableBudget.add();

        verify(mockShowAllBudgetsNavigation).navigate();
    }

}