package com.odde.bbuddy.account.model;

import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class Accounts {

    private final JsonBackend jsonBackend;
    private final JsonMapper<Account> jsonMapper;
    private final AccountsApi accountsApi;

    public Accounts(JsonBackend jsonBackend, JsonMapper<Account> jsonMapper, AccountsApi accountsApi) {
        this.jsonBackend = jsonBackend;
        this.jsonMapper = jsonMapper;
        this.accountsApi = accountsApi;
    }

    public void processAllAccounts(final Consumer<List<Account>> consumer) {
        jsonBackend.getRequestForJsonArray("/accounts", new Consumer<JSONArray>() {
            @Override
            public void accept(JSONArray response) {
                consumer.accept(jsonMapper.fromJsonArray(response));
            }
        });
    }

    public void addAccount(Account account, final Runnable afterSuccess) {
        accountsApi.addAccount(account)
                .enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, retrofit2.Response<Account> response) {
                afterSuccess.run();
            }
            @Override
            public void onFailure(Call<Account> call, Throwable t) {

            }
        });

    }

    public void editAccount(Account account, final Runnable afterSuccess) {
        jsonBackend.putRequestForJson("/accounts/" + account.getId(), jsonMapper.jsonOf(account), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                afterSuccess.run();
            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    public void deleteAccount(Account account, final Runnable afterSuccess) {
        jsonBackend.deleteRequestForJson("/accounts/" + account.getId(), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                afterSuccess.run();
            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        });
    }

}
