package com.odde.bbuddy.budget;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.util.Arrays.asList;

public class BudgetsApi {

    private final RawBudgetsApi rawBudgetsApi;

    @Inject
    public BudgetsApi(RawBudgetsApi rawBudgetsApi) {
        this.rawBudgetsApi = rawBudgetsApi;
    }

    public void addBudget(Budget budget) {
        rawBudgetsApi.addBudget(budget).enqueue(new Callback<Budget>() {
            @Override
            public void onResponse(Call<Budget> call, Response<Budget> response) {

            }

            @Override
            public void onFailure(Call<Budget> call, Throwable t) {

            }
        });
    }

    public List<Budget> getAllBudgets() {
        Budget budget = new Budget();
        budget.setMonth("2017-03");
        budget.setAmount(1000);
        return asList(budget);
    }
}
