package com.odde.bbuddy.authentication;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONObject;

public class Authenticator {

    private final JsonBackend backend;
    private final JsonMapper<Credentials> jsonMapper;

    public Authenticator(JsonBackend backend, JsonMapper<Credentials> jsonMapper) {
        this.backend = backend;
        this.jsonMapper = jsonMapper;
    }

    public void authenticate(Credentials credentials, final Consumer afterSuccess) {
        backend.postRequestForJson("/auth/sign_in", jsonMapper.jsonOf(credentials), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {
                afterSuccess.accept("success");
            }
        }, new Runnable() {
            @Override
            public void run() {
                afterSuccess.accept("failed");
            }
        });
    }

}
