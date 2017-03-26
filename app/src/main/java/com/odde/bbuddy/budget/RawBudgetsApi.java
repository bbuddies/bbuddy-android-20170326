package com.odde.bbuddy.budget;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RawBudgetsApi {

    @POST("/budgets")
    Call<Budget> addBudget(@Body Budget budget);
}
