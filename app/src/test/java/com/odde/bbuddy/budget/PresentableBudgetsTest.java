package com.odde.bbuddy.budget;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;

import java.util.List;

import dagger.Lazy;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
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

        assertEquals(asList(budget), presentableBudgets.getBudgets());
    }

    @Test
    public void refresh() {
        presentableBudgets.refresh();

        verify(mockPresentationModelChangeSupport).refreshPresentationModel();
    }

    private void given_budgets_will_return(List<Budget> allBudgets) {
        when(stubBudgets.getAllBudgets()).thenReturn(allBudgets);
    }

}