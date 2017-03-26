package com.odde.bbuddy.budget;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresentableBudgetsTest {

    @Test
    public void get_budgets_will_call_budgets_api() {
        BudgetsApi mockBudgetsApi = mock(BudgetsApi.class);
        Budget budget = new Budget();
        when(mockBudgetsApi.getAllBudgets()).thenReturn(asList(budget));
        PresentableBudgets presentableBudgets = new PresentableBudgets(mockBudgetsApi);

        List<Budget> actual = presentableBudgets.getBudgets();

        assertThat(actual).isEqualTo(asList(budget));
        verify(mockBudgetsApi).getAllBudgets();
    }

}