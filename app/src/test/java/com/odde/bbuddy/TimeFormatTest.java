package com.odde.bbuddy;

import com.odde.bbuddy.budget.Budget;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;


/**
 * Created by sharp on 2017/3/27.
 */
public class TimeFormatTest {
    //@Test
    public void getFormatTime() throws Exception {

        String time = "2017-03-27 09:36:00.992";
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS");
        format.parse(time);
       // Calendar cal = Calendar.getInstance();
      //  cal.set(2017,02,27,9,36,00);
       // String time = TimeFormat.getFormatTime(cal.getTime());
      //  System.out.print(time);
        assertThat(time).isEqualTo(time);
    }

    @Test
    public void getTotalBudget() throws Exception {
        List<Budget> budgets = new ArrayList<Budget>();
        Budget budget1 = new Budget();
        budget1.setAmount(1000);
        budget1.setMonth("2017-03");
        budgets.add(budget1);
        Budget budget2 = new Budget();
        budget2.setAmount(1000);
        budget2.setMonth("2017-04");
        budgets.add(budget2);
        double amount = TimeFormat.getTotalBudget(budgets, "2017-03-02", "2017-05-05");
        System.out.print(amount);
    }

}