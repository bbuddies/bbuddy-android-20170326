package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BudgetsTest {

    @Test
    public void add_budget_with_month_and_amount() throws JSONException {
        JsonBackend mockJsonBackend = mock(JsonBackend.class);
        Budgets budgets = new Budgets(mockJsonBackend);

        Budget budget = new Budget();
        budget.setMonth("2017-02");
        budget.setAmount(2000);
        budgets.add(budget);

        ArgumentCaptor<JSONObject> captor = ArgumentCaptor.forClass(JSONObject.class);
        verify(mockJsonBackend).postRequestForJson(eq("/budgets"), captor.capture(), any(Consumer.class), any(Runnable.class));
        assertEquals("2017-02", captor.getValue().getString("month"));
        assertEquals(2000, captor.getValue().getInt("amount"));
    }

}