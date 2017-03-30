package com.odde.bbuddy.budget;

import org.joda.time.LocalDate;

import java.util.List;

/**
 * Created by lizz on 2017/3/28.
 */

public final class BudgetSumUtil {

    public static float getBudgetsSum(String startDateString, String endDateString, List<Budget> budgets) {
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        Budgets plan = new Budgets(budgets);
        return plan.query(startDate, endDate, null);
    }
}
