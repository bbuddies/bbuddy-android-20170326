package com.odde.bbuddy.budget.viewmodel;

import com.odde.bbuddy.budget.model.Budgets;
import com.odde.bbuddy.budget.view.ShowAllBudgetsNavigation;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    ShowAllBudgetsNavigation mockShowAllBudgetsNavigation = mock(ShowAllBudgetsNavigation.class);
    Budgets mockBudgets = mock(Budgets.class);
    EditableBudget editableBudget = new EditableBudget(mockBudgets, mockShowAllBudgetsNavigation);

    @Test
    public void add_should_show_all_budgets() {
        addBudget("2017-01", "1000");

        verify(mockShowAllBudgetsNavigation).navigate();
    }

    @Test
    public void add_should_correctly_add_budget() {
        addBudget("2017-01", "1000");

        verifyBudgetsAddWithBudget(budget("2017-01", 1000));
    }

    private void verifyBudgetsAddWithBudget(Budget budget) {
        ArgumentCaptor<Budget> captor = forClass(Budget.class);
        verify(mockBudgets).add(captor.capture());
        assertThat(captor.getValue()).isEqualToComparingFieldByField(budget);
    }

    private Budget budget(String month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }

    private void addBudget(String month, String amount) {
        editableBudget.setMonth(month);
        editableBudget.setAmount(amount);
        editableBudget.add();
    }

}