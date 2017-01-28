package com.odde.bbuddy.common;

import android.content.Context;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.account.Account;
import com.odde.bbuddy.authentication.Credentials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backend {

    private final static Map<String, String> authenticationHeaders = new HashMap<>();
    private final JsonBackend jsonBackend;

    public Backend(Context context) {
        jsonBackend = new JsonBackend(context);
    }

    public void authenticate(Credentials credentials, final Consumer<String> afterSuccess) {
        jsonBackend.postRequestForJson("/auth/sign_in", jsonOf(credentials), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                afterSuccess.accept("success");
            }
        }, new Runnable() {
            @Override
            public void run() {
                afterSuccess.accept("failed");
            }
        }, new Consumer<Map<String, String>>() {
            @Override
            public void accept(Map<String, String> headers) {
                updateAuthenticationHeaders(headers);
            }
        });
    }

    private void updateAuthenticationHeaders(Map<String, String> responseHeaders) {
        authenticationHeaders.put("access-token", responseHeaders.get("access-token"));
        authenticationHeaders.put("token-type", responseHeaders.get("token-type"));
        authenticationHeaders.put("uid", responseHeaders.get("uid"));
        authenticationHeaders.put("client", responseHeaders.get("client"));
        authenticationHeaders.put("expiry", responseHeaders.get("expiry"));
    }

    private JSONObject jsonOf(Credentials credentials) {
        try {
            return new JSONObject(new ObjectMapper().writeValueAsString(credentials));
        } catch (JsonProcessingException | JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    public void processAllAccounts(final Consumer<List<Account>> consumer) {
        jsonBackend.getRequestForJsonArray("/accounts", authenticationHeaders(), new Consumer<JSONArray>() {
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

    private Map<String, String> authenticationHeaders() {
        Map<String,String> params = new HashMap<>();
        params.put("access-token", authenticationHeaders.get("access-token"));
        params.put("token-type", authenticationHeaders.get("token-type"));
        params.put("uid", authenticationHeaders.get("uid"));
        params.put("client", authenticationHeaders.get("client"));
        params.put("expiry", authenticationHeaders.get("expiry"));
        return params;
    }
}
