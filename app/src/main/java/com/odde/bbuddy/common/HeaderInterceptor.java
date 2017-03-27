package com.odde.bbuddy.common;

import com.odde.bbuddy.authentication.AuthenticationToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private final AuthenticationToken authenticationToken;

    public HeaderInterceptor(AuthenticationToken authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        for (String header: authenticationToken.getHeaders().keySet()) {
            requestBuilder.header(header, authenticationToken.getHeaders().get(header));
        }
        Request request = requestBuilder
                .method(original.method(), original.body())
                .build();

        Response response = chain.proceed(request);
        Map<String, String> authenticationHeaders = new HashMap<>();
        for (String name: response.headers().names()) {
            authenticationHeaders.put(name, response.headers().get(name));
        }
        authenticationToken.updateByHeaders(authenticationHeaders);
        return response;
    }


}
