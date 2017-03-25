package com.odde.bbuddy.account.model;

import com.odde.bbuddy.BuildConfig;
import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.authentication.AuthenticationToken;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Accounts {

    private final JsonBackend jsonBackend;
    private final JsonMapper<Account> jsonMapper;
    private final AuthenticationToken authenticationToken;

    public Accounts(JsonBackend jsonBackend, JsonMapper<Account> jsonMapper, AuthenticationToken authenticationToken) {
        this.jsonBackend = jsonBackend;
        this.jsonMapper = jsonMapper;
        this.authenticationToken = authenticationToken;
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
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
                                      @Override
                                      public Response intercept(Interceptor.Chain chain) throws IOException {
                                          Request original = chain.request();

                                          Request.Builder requestBuilder = original.newBuilder();
                                          for (String header: authenticationToken.getHeaders().keySet()) {
                                              requestBuilder.header(header, authenticationToken.getHeaders().get(header));
                                          }
                                          Request request = requestBuilder
                                                  .method(original.method(), original.body())
                                                  .build();

                                          return chain.proceed(request);
                                      }
                                  });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();

        AccountsApi service = retrofit.create(AccountsApi.class);
        service.addAccount(account)
                .enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, retrofit2.Response<Account> response) {
                afterSuccess.run();
                Map<String, String> authenticationHeaders = new HashMap<>();
                for (String name: response.headers().names()) {
                    authenticationHeaders.put(name, response.headers().get(name));
                }
                authenticationToken.updateByHeaders(authenticationHeaders);
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
