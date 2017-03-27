package com.odde.bbuddy.budget;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RawBudgetsApi {

    @POST("/budgets")
    Call<Budget> addBudget(@Body Budget budget);

    @GET("/budgets")
    Call<List<Budget>> getAllBudgets();

    @PUT("/budgets/{id}")
    Call<Budget> updateBudget(@Path("id") long id, @Body Budget budget);
}
