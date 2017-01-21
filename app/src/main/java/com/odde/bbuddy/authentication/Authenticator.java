package com.odde.bbuddy.authentication;

import com.odde.bbuddy.common.Backend;

public class Authenticator {

    private final Backend backend;

    public Authenticator(Backend backend) {
        this.backend = backend;
    }

    public void authenticate(Credentials credentials) {
        backend.authenticate(credentials);
    }
}
