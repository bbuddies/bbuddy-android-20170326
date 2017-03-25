package com.odde.bbuddy.common;

import com.odde.bbuddy.authentication.AuthenticationToken;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.odde.bbuddy.BuildConfig.SERVER_URL;

public class ApiFactory {

    private final Retrofit retrofit;

    public ApiFactory(AuthenticationToken authenticationToken) {
        retrofit = retrofit(authenticationToken);
    }

    public <T> T create(Class<T> api) {
        return retrofit.create(api);
    }

    private Retrofit retrofit(AuthenticationToken authenticationToken) {
        return new Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(clientWithInterceptor(authenticationToken))
                .build();
    }

    private OkHttpClient clientWithInterceptor(AuthenticationToken authenticationToken) {
        return new OkHttpClient.Builder()
                .addInterceptor(new HeaderInterceptor(authenticationToken))
                .build();
    }

}
