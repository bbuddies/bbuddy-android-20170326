package com.odde.bbuddy.account.model;

import com.odde.bbuddy.account.viewmodel.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AccountsApi {

    @POST("/accounts")
    Call<Account> addAccount(@Body Account account);
}
