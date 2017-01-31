package com.odde.bbuddy.account;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.authentication.AuthenticationToken;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Accounts {

    private final JsonBackend jsonBackend;
    private final AuthenticationToken token;

    public Accounts(JsonBackend jsonBackend, AuthenticationToken token) {
        this.jsonBackend = jsonBackend;
        this.token = token;
    }

    public void processAllAccounts(final Consumer<List<Account>> consumer) {
        jsonBackend.getRequestForJsonArray("/accounts", token.getHeaders(), new Consumer<JSONArray>() {
            @Override
            public void accept(JSONArray response) {
                consumer.accept(accountsFromJson(response));
            }
        }, new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> responseHeaders) {
                token.updateByHeaders(responseHeaders);
            }
        });
    }

    private List<Account> accountsFromJson(JSONArray response) {
        try {
            return new ObjectMapper().readValue(response.toString(), new TypeReference<List<Account>>(){});
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }

    public void addAccount(Account account, final Runnable afterSuccess) {
        jsonBackend.postRequestForJson("/accounts", jsonOf(account), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                afterSuccess.run();
            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        }, new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> stringStringMap) {

            }
        });
    }

    @NonNull
    private JSONObject jsonOf(Account account) {
        try {
            return new JSONObject(new ObjectMapper().writeValueAsString(account));
        } catch (JSONException | JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }
}
