package com.odde.bbuddy.account.api;

import com.odde.bbuddy.account.viewmodel.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RawAccountsApi {

    @POST("/accounts")
    Call<Account> addAccount(@Body Account account);
}
