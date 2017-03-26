package com.odde.bbuddy.budget;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    BudgetsActivityNavigation mockBudgetsActivityNavigation = mock(BudgetsActivityNavigation.class);
    BudgetsApi mockBudgetsApi = mock(BudgetsApi.class);
    EditableBudget editableBudget = new EditableBudget(mockBudgetsActivityNavigation, mockBudgetsApi);

    @Test
    public void add_should_navigate_to_budgets_activity() {
        addBudget("2017-03", "1000");

        verify(mockBudgetsActivityNavigation).navigate();
    }

    @Test
    public void add_should_call_budgets_api() {
        addBudget("2017-03", "1000");

        verifyAddBudgetWith("2017-03", 1000);
    }

    private void verifyAddBudgetWith(String month, int amount) {
        ArgumentCaptor<Budget> captor = ArgumentCaptor.forClass(Budget.class);
        verify(mockBudgetsApi).addBudget(captor.capture());
        assertThat(captor.getValue().getMonth()).isEqualTo(month);
        assertThat(captor.getValue().getAmount()).isEqualTo(amount);
    }

    private void addBudget(String month, String amount) {
        editableBudget.setMonth(month);
        editableBudget.setAmount(amount);
        editableBudget.add();
    }

}