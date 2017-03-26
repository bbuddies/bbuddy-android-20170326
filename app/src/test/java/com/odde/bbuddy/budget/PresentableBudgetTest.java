package com.odde.bbuddy.budget;

import org.junit.Test;
import org.robobinding.itempresentationmodel.ItemContext;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PresentableBudgetTest {

    @Test
    public void display_of_budget() {
        PresentableBudget presentableBudget = new PresentableBudget();

        Budget budget = new Budget();
        budget.setMonth("2017-03");
        budget.setAmount(1000);
        ItemContext stubItemContext = mock(ItemContext.class);
        presentableBudget.updateData(budget, stubItemContext);

        assertThat(presentableBudget.getDisplayOfBudget()).isEqualTo("2017-03 1000");
    }

}