package com.odde.bbuddy.budget.viewmodel;

import com.odde.bbuddy.budget.model.Budgets;
import com.odde.bbuddy.common.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.util.List;

import dagger.Lazy;

import static com.odde.bbuddy.common.CallbackInvoker.callConsumerArgumentAtIndexWith;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresentableBudgetsTest {

    Budgets stubBudgets = mock(Budgets.class);
    Lazy<PresentationModelChangeSupport> stubLoader = mock(Lazy.class);
    PresentationModelChangeSupport mockPresentationModelChangeSupport = mock(PresentationModelChangeSupport.class);
    PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgets, stubLoader);
    Budget budget = new Budget() {{ setMonth("2017-01"); setAmount(1000); }};

    @Before
    public void given_lazy_loader_will_return_change_support() {
        when(stubLoader.get()).thenReturn(mockPresentationModelChangeSupport);
    }

    @Test
    public void get_all_budgets() {
        given_budgets_will_return(asList(budget));

        PresentableBudgets presentableBudgets = new PresentableBudgets(stubBudgets, stubLoader);

        assertThat(presentableBudgets.getBudgets()).isEqualTo(asList(budget));
    }

    @Test
    public void refresh() {
        given_budgets_will_return(asList(budget));

        presentableBudgets.refresh();

        verify(mockPresentationModelChangeSupport).refreshPresentationModel();
        assertThat(presentableBudgets.getBudgets()).isEqualTo(asList(budget));
    }

    private void given_budgets_will_return(final List<Budget> allBudgets) {
        callConsumerArgumentAtIndexWith(0, allBudgets).when(stubBudgets).processAllBudgets(any(Consumer.class));
    }

}