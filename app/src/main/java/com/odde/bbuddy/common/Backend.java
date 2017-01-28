package com.odde.bbuddy.common;

import android.content.Context;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.account.Account;
import com.odde.bbuddy.authentication.AuthenticationToken;

import org.json.JSONArray;

import java.io.IOException;
import java.util.List;

public class Backend {

    private final JsonBackend jsonBackend;
    private final AuthenticationToken token = new AuthenticationToken();

    public Backend(Context context) {
        jsonBackend = new JsonBackend(context);
    }

    public void processAllAccounts(final Consumer<List<Account>> consumer) {
        jsonBackend.getRequestForJsonArray("/accounts", token.getHeaders(), new Consumer<JSONArray>() {
            @Override
            public void accept(JSONArray response) {
                consumer.accept(accountsFromJson(response));
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

}
