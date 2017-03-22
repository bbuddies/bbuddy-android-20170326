package com.odde.bbuddy.authentication;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonBackendMock;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONException;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthenticatorTest {

    JsonBackend mockBackend = mock(JsonBackend.class);
    JsonMapper<Credentials> jsonMapper = new JsonMapper<>(Credentials.class);
    Authenticator authenticator = new Authenticator(mockBackend, jsonMapper);
    Credentials credentials = credentials("abc@gmail.com", "password");
    Consumer afterSuccess = mock(Consumer.class);
    JsonBackendMock<Credentials> jsonBackendMock = new JsonBackendMock<>(mockBackend, Credentials.class);

    @Test
    public void authenticate_with_user_name_and_password() throws JSONException {
        authenticate(credentials("abc@gmail.com", "password"));

        jsonBackendMock.verifyPostWith("/auth/sign_in", credentials("abc@gmail.com", "password"));
    }

    @Test
    public void authenticate_successful() {
        jsonBackendMock.givenPostWillSuccess();

        authenticate(credentials);

        verify(afterSuccess).accept("success");
    }

    @Test
    public void authenticate_failed() {
        jsonBackendMock.givenPostWillFail();

        authenticate(credentials);

        verify(afterSuccess).accept("failed");
    }

    private void authenticate(Credentials credentials) {
        authenticator.authenticate(credentials, afterSuccess);
    }

    private Credentials credentials(String email, String password) {
        return new Credentials(email, password);
    }

}