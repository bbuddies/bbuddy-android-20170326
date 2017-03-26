package com.odde.bbuddy.budget;

import com.odde.bbuddy.common.Consumer;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    public void getAllBudgets(final Consumer<List<Budget>> consumer) {
        rawBudgetsApi.getAllBudgets().enqueue(new Callback<List<Budget>>() {
            @Override
            public void onResponse(Call<List<Budget>> call, Response<List<Budget>> response) {
                consumer.accept(response.body());
            }

            @Override
            public void onFailure(Call<List<Budget>> call, Throwable t) {

            }
        });
    }
}
