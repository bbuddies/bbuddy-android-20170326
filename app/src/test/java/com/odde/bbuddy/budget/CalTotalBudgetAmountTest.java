package com.odde.bbuddy.budget;

import com.odde.bbuddy.TimeFormat;

import org.junit.Before;
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

    List<Budget> budgets = new ArrayList<Budget>();

    public void addBudget(String month, int amount){
        Budget budget = new Budget();
        budget.setAmount(amount);
        budget.setMonth(month);
        budgets.add(budget);
    }

    public double calTotalBudget(String start, String end){
        Date startDate = StrToDate(start);
        Date endDate = StrToDate(end);
        return calTotalBudgetAmount.getTotalBudget(budgets, startDate, endDate);
    }

    @Test
    public void calTotalBudget_In_Month() throws Exception {
        addBudget("2017-03",3100);
        //addBudget("2017-04",3000);

        double amount = calTotalBudget("2017-03-01","2017-03-15");
        assertThat(amount).isEqualTo(1500);

    }

    @Test
    public void calTotalBudget_Befor_Month() throws Exception {
        addBudget("2017-03",3100);
        //addBudget("2017-04",3000);

        double amount = calTotalBudget("2017-02-01","2017-03-15");
        assertThat(amount).isEqualTo(1500);

    }

    @Test
    public void calTotalBudget_After_Month() throws Exception {
        addBudget("2017-03",3100);
        //addBudget("2017-04",3000);

        double amount = calTotalBudget("2017-03-16","2017-04-15");
        assertThat(amount).isEqualTo(1600);

    }
    @Test
    public void calTotalBudget_Around_Month() throws Exception {
        addBudget("2017-03",3100);
        //addBudget("2017-04",3000);

        double amount = calTotalBudget("2017-02-01","2017-04-15");
        assertThat(amount).isEqualTo(3100);

    }

    @Test
    public void calTotalBudget_Two_Month_In() throws Exception {
        addBudget("2017-03",3100);
        addBudget("2017-05",3100);

        double amount = calTotalBudget("2017-03-01","2017-05-15");
        assertThat(amount).isEqualTo(4600);

    }

    @Test
    public void calTotalBudget_Two_Month_Around() throws Exception {
        addBudget("2017-03",3100);
        addBudget("2017-05",3100);

        double amount = calTotalBudget("2017-02-01","2017-06-15");
        assertThat(amount).isEqualTo(6200);

    }

    @Test
    public void calTotalBudget_Two_Month_Before() throws Exception {
        addBudget("2017-03",3100);
        addBudget("2017-05",3100);

        double amount = calTotalBudget("2017-02-01","2017-03-15");
        assertThat(amount).isEqualTo(1500);

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