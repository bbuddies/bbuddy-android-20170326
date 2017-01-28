package com.odde.bbuddy.common;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.authentication.Credentials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.toolbox.Volley.newRequestQueue;

public class Backend {

    private final RequestQueue requestQueue;
    private final static Map<String, String> authenticationHeaders = new HashMap<>();

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
                }){
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                updateAuthenticationHeaders(response.headers);
                return super.parseNetworkResponse(response);
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

    public void processAllAccounts(final Consumer<JSONArray> consumer) {
        requestQueue.add(new JsonArrayRequest(
                Request.Method.GET, "http://10.0.3.2:3000/accounts", null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        consumer.accept(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        try {
                            consumer.accept(new JSONArray("[{\"name\":\"error\",\"balance\":0}]"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return authenticationHeaders();
            }
        });
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
