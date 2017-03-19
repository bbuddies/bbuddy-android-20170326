package com.odde.bbuddy.budget.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.budget.viewmodel.Budget;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BudgetsTest {

    JsonBackend mockJsonBackend = mock(JsonBackend.class);
    JsonMapper<Budget> jsonMapper = new JsonMapper<>(Budget.class);
    Budgets budgets = new Budgets(mockJsonBackend, jsonMapper);
    Consumer mockConsumer = mock(Consumer.class);

    @Test
    public void add_budget_with_month_and_amount() throws JSONException {
        budgets.add(budget("2017-02", 2000));

        verifyPostWith("/budgets", budget("2017-02", 2000));
    }

    @Test
    public void get_all_budgets_call_backend_correctly() {
        budgets.processAllBudgets(mockConsumer);

        verifyGetWith("/budgets");
    }

    @Test
    public void get_all_budgets_return_data_correctly() {
        given_json_backend_will_return_with_month_and_amount(budget("2017-02", 2000));

        budgets.processAllBudgets(mockConsumer);

        verifyBudgetConsumed("2017-02", 2000);
    }

    private void verifyGetWith(String path) {
        verify(mockJsonBackend).getRequestForJsonArray(eq(path), any(Consumer.class));
    }

    private void verifyBudgetConsumed(String month, int amount) {
        ArgumentCaptor<List<Budget>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockConsumer).accept(captor.capture());
        assertEquals(1, captor.getValue().size());
        assertEquals(month, captor.getValue().get(0).getMonth());
        assertEquals(amount, captor.getValue().get(0).getAmount());
    }

    private void given_json_backend_will_return_with_month_and_amount(final Budget budget) {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(1);
                consumer.accept(new JSONArray(new ObjectMapper().writeValueAsString(asList(budget))));
                return null;
            }
        }).when(mockJsonBackend).getRequestForJsonArray(anyString(), any(Consumer.class));
    }

    private void verifyPostWith(String path, Budget budget) throws JSONException {
        ArgumentCaptor<JSONObject> captor = ArgumentCaptor.forClass(JSONObject.class);
        verify(mockJsonBackend).postRequestForJson(eq(path), captor.capture(), any(Consumer.class), any(Runnable.class));
        JSONAssert.assertEquals(jsonMapper.jsonOf(budget), captor.getValue(), true);
    }

    private Budget budget(String month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }

}