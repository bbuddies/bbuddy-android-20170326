package com.odde.bbuddy.budget;

import android.support.annotation.NonNull;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizz on 2017/3/28.
 */

public class EditableSumBudgetTest {
    private static final double DELTA = Math.pow(10, -2);

    @Test
    public void test_getSumBudgets() {
        List<Budget> budgets = new ArrayList<>();
        budgets.add(createBudget(1, "2017-02", 1000));
        budgets.add(createBudget(2, "2017-03", 1000));
        budgets.add(createBudget(3, "2017-04", 1000));

        float actResult = BudgetSumUtil.getBudgetsSum("2017-02-15", "2017-04-16", budgets);
        Assert.assertEquals(2033.33, actResult, DELTA);
    }

    @Test
    public void test_getSumBudgets_ListEmpty() {
        List<Budget> budgets = new ArrayList<>();

        float actResult = BudgetSumUtil.getBudgetsSum("2017-02-15", "2017-04-16", budgets);
        Assert.assertEquals(0, actResult, DELTA);
    }

    @Test
    public void test_getSumBudgets_SameMonth() {
        List<Budget> budgets = new ArrayList<>();
        budgets.add(createBudget(1, "2017-02", 1000));

        float actResult = BudgetSumUtil.getBudgetsSum("2017-02-15", "2017-02-28", budgets);
        Assert.assertEquals(14 / 28.0f * 1000, actResult, DELTA);
    }

    @Test
    public void test_getSumBudgets_OverYear() {
        List<Budget> budgets = new ArrayList<>();
        budgets.add(createBudget(1, "2016-12", 1000));
        budgets.add(createBudget(2, "2017-01", 1000));
        budgets.add(createBudget(3, "2017-02", 1000));

        float actResult = BudgetSumUtil.getBudgetsSum("2016-12-15", "2017-02-15", budgets);
        Assert.assertEquals((17 / 31.0f + 15 / 28.0f)* 1000 + 1000, actResult, DELTA);
    }

    @Test
    public void test_getSumBudgets_TwoMonths() {
        List<Budget> budgets = new ArrayList<>();
        budgets.add(createBudget(1, "2016-12", 1000));
        budgets.add(createBudget(2, "2017-01", 1000));

        float actResult = BudgetSumUtil.getBudgetsSum("2016-12-15", "2017-01-11", budgets);
        Assert.assertEquals((17 / 31.0f + 11 / 31.0f)* 1000, actResult, DELTA);
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
