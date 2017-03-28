package com.odde.bbuddy.budget;

import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * Created by lizz on 2017/3/28.
 */

public class EditableSumBudgetTest {
    BudgetsActivityNavigation mockBudgetsActivityNavigation = mock(BudgetsActivityNavigation.class);
    BudgetsApi mockBudgetsApi = mock(BudgetsApi.class);
    EditableSumBudget editableBudget = new EditableSumBudget(mockBudgetsActivityNavigation, mockBudgetsApi);

    @Test
    public void test_getMonthPersent() {
        List<Budget> budgets = new ArrayList<>();

        budgets.add(createBudget(1, "2", 1000));
        budgets.add(createBudget(2, "3", 1000));
        budgets.add(createBudget(3, "4", 1000));

        float actResult = BudgetSumUtil.getBudgetsSum("2017-2-15", "2017-4-16", budgets);

        Assert.assertEquals(1000, actResult);
    }

    @NonNull
    private Budget createBudget(int id, String month, int amount) {
        Budget budget = new Budget();
        budget.setId(id);
        budget.setMonth(month);
        budget.setAmount(amount);

        return budget;
    }
}
