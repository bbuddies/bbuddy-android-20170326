package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class EditableBudgetTest {

    BudgetsActivityNavigation mockBudgetsActivityNavigation = mock(BudgetsActivityNavigation.class);
    BudgetsApi mockBudgetsApi = mock(BudgetsApi.class);
    EditableBudget editableBudget = new EditableBudget(mockBudgetsActivityNavigation, mockBudgetsApi);

    @Test
    public void add_should_navigate_to_budgets_activity() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                consumer.accept(asList());
                return null;
            }
        }).when(mockBudgetsApi).getAllBudgets(any(Consumer.class));

        addBudget("2017-03", "1000");

        verify(mockBudgetsActivityNavigation).navigate();
    }

    @Test
    public void add_should_call_budgets_api() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                consumer.accept(asList());
                return null;
            }
        }).when(mockBudgetsApi).getAllBudgets(any(Consumer.class));

        addBudget("2017-03", "1000");

        verifyAddBudgetWith("2017-03", 1000);
    }

    @Test
    public void add_exists_should_call_budgets_api() {
        final Budget budget = new Budget();
        budget.setAmount(2000);
        budget.setMonth("2017-03");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                consumer.accept(asList(budget));
                return null;
            }
        }).when(mockBudgetsApi).getAllBudgets(any(Consumer.class));

        addBudget("2017-03", "1000");
        verifyUpdateBudgetWith("2017-03", 1000);
    }

    private void verifyAddBudgetWith(String month, int amount) {
        ArgumentCaptor<Budget> captor = ArgumentCaptor.forClass(Budget.class);
        verify(mockBudgetsApi).addBudget(captor.capture());
        assertThat(captor.getValue().getMonth()).isEqualTo(month);
        assertThat(captor.getValue().getAmount()).isEqualTo(amount);
    }

    private void verifyUpdateBudgetWith(String month, int amount) {
        ArgumentCaptor<Budget> captor = ArgumentCaptor.forClass(Budget.class);
        verify(mockBudgetsApi).updateBudget(captor.capture(), any(Runnable.class));
        assertThat(captor.getValue().getMonth()).isEqualTo(month);
        assertThat(captor.getValue().getAmount()).isEqualTo(amount);
    }



    private void addBudget(String month, String amount) {
        editableBudget.setMonth(month);
        editableBudget.setAmount(amount);
        editableBudget.add();
    }

}