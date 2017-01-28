package com.odde.bbuddy.common;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.authentication.Credentials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class Backend {

    private final RequestQueue requestQueue;

    public Backend(Context context) {
        requestQueue = newRequestQueue(context);
    }

    public void authenticate(Credentials credentials, final Consumer<String> afterSuccess) {
        requestQueue.add(new JsonObjectRequest(
                Request.Method.POST, "http://10.0.3.2:3000/auth/sign_in", jsonOf(credentials),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        afterSuccess.accept("success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        afterSuccess.accept("failed");
                    }
                }));
    }

    private JSONObject jsonOf(Credentials credentials) {
        try {
            return new JSONObject(new ObjectMapper().writeValueAsString(credentials));
        } catch (JsonProcessingException | JSONException e) {
            throw new IllegalStateException(e);
        }
    }

    public void processAllAccounts(final Consumer<JSONArray> consumer) {
        try {
            consumer.accept(new JSONArray("[{\"id\":1,\"name\":\"CMB\",\"balance\":1000,\"created_at\":\"2017-01-26T11:59:32.576Z\",\"updated_at\":\"2017-01-26T11:59:32.576Z\"},{\"id\":2,\"name\":\"HSBC\",\"balance\":0,\"created_at\":\"2017-01-26T13:26:58.159Z\",\"updated_at\":\"2017-01-26T13:26:58.159Z\"}]"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
