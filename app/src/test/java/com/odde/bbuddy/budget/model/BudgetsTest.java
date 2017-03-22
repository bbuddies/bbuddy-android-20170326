package com.odde.bbuddy.budget.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.odde.bbuddy.budget.viewmodel.Budget;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonBackendMock;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONException;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class BudgetsTest {

    JsonBackend mockJsonBackend = mock(JsonBackend.class);
    JsonMapper<Budget> jsonMapper = new JsonMapper<>(Budget.class);
    JsonBackendMock<Budget> jsonBackendMock = new JsonBackendMock<>(mockJsonBackend, Budget.class);
    Budgets budgets = new Budgets(mockJsonBackend, jsonMapper);
    Consumer mockConsumer = mock(Consumer.class);

    @Test
    public void add_budget_with_month_and_amount() throws JSONException {
        budgets.add(budget("2017-02", 2000));

        jsonBackendMock.verifyPostWith("/budgets", budget("2017-02", 2000));
    }

    @Test
    public void get_all_budgets_call_backend_correctly() {
        budgets.processAllBudgets(mockConsumer);

        jsonBackendMock.verifyGetWith("/budgets");
    }

    @Test
    public void get_all_budgets_return_data_correctly() throws JSONException, JsonProcessingException {
        jsonBackendMock.givenGetWillReturnList(asList(budget("2017-02", 2000)));

        budgets.processAllBudgets(mockConsumer);

        verifyBudgetConsumed(budget("2017-02", 2000));
    }

    private void verifyBudgetConsumed(Budget budget) {
        ArgumentCaptor<List<Budget>> captor = ArgumentCaptor.forClass(List.class);
        verify(mockConsumer).accept(captor.capture());
        assertThat(captor.getValue()).usingFieldByFieldElementComparator().isEqualTo(asList(budget));
    }

    private Budget budget(String month, int amount) {
        Budget budget = new Budget();
        budget.setMonth(month);
        budget.setAmount(amount);
        return budget;
    }

}