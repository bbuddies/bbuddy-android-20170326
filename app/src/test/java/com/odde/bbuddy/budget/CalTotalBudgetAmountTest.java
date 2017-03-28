package com.odde.bbuddy.budget;

import com.odde.bbuddy.TimeFormat;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by sharp on 2017/3/28.
 */
public class CalTotalBudgetAmountTest {


    CalTotalBudgetAmount calTotalBudgetAmount = new CalTotalBudgetAmount();

    @Test
    public void getTotalBudget() throws Exception {
        List<Budget> budgets = new ArrayList<Budget>();
        Budget budget1 = new Budget();
        budget1.setAmount(1000);
        budget1.setMonth("2017-03");
        budgets.add(budget1);
        Budget budget2 = new Budget();
        budget2.setAmount(2000);
        budget2.setMonth("2017-04");
        budgets.add(budget2);

        //test1
        Date date1_1 = StrToDate("2017-03-01");
        Date date1_2 = StrToDate("2017-03-15");
        double amount = calTotalBudgetAmount.getTotalBudget(budgets, date1_1, date1_2);
        assertThat((int)amount).isEqualTo(451);

        //test2
        Date date2_1 = StrToDate("2017-02-01");
        Date date2_2 = StrToDate("2017-04-01");
        double amount2 = calTotalBudgetAmount.getTotalBudget(budgets, date2_1, date2_2);
        assertThat((int)amount2).isEqualTo(1000);

        //test3
        Date date3_1 = StrToDate("2017-03-15");
        Date date3_2 = StrToDate("2017-05-01");
        double amount3 = calTotalBudgetAmount.getTotalBudget(budgets, date3_1, date3_2);
        assertThat((int)amount3).isEqualTo(2548);
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return date
     */
    public Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}