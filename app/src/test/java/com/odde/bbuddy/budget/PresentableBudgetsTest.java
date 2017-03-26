package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class PresentableBudgetsTest {

    @Test
    public void get_budgets_will_call_budgets_api() {
        BudgetsApi mockBudgetsApi = mock(BudgetsApi.class);
        final Budget budget = new Budget();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(0);
                consumer.accept(asList(budget));
                return null;
            }
        }).when(mockBudgetsApi).getAllBudgets(any(Consumer.class));
        PresentableBudgets presentableBudgets = new PresentableBudgets(mockBudgetsApi);

        List<Budget> actual = presentableBudgets.getBudgets();

        assertThat(actual).isEqualTo(asList(budget));
        verify(mockBudgetsApi).getAllBudgets(any(Consumer.class));
    }

}