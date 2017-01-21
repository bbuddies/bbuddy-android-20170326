package com.odde.bbuddy.authentication;

import com.odde.bbuddy.common.Backend;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthenticatorTest {

    @Test
    public void authenticate_with_user_name_and_password() throws Exception {
        Backend mockBackend = mock(Backend.class);
        Authenticator authenticator = new Authenticator(mockBackend);

        Credentials credentials = new Credentials("userName", "password");
        authenticator.authenticate(credentials);

        verify(mockBackend).authenticate(credentials);
    }

}