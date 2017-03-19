package com.odde.bbuddy.authentication;

import android.support.annotation.NonNull;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class AuthenticatorTest {

    JsonBackend mockBackend = mock(JsonBackend.class);
    Authenticator authenticator = new Authenticator(mockBackend);
    Credentials credentials = new Credentials("abc@gmail.com", "password");
    Consumer afterSuccess = mock(Consumer.class);
    JsonMapper<Credentials> jsonMapper = new JsonMapper<>(Credentials.class);

    @Test
    public void authenticate_with_user_name_and_password() throws JSONException {
        authenticate(credentials("abc@gmail.com", "password"));

        verifyPostWith("/auth/sign_in", credentials("abc@gmail.com", "password"));
    }

    @Test
    public void authenticate_successful() {
        given_jsonbackend_will_response(success());

        authenticate(credentials);

        verify(afterSuccess).accept("success");
    }

    @Test
    public void authenticate_failed() {
        given_jsonbackend_will_response(failed());

        authenticate(credentials);

        verify(afterSuccess).accept("failed");
    }

    private void authenticate(Credentials credentials) {
        authenticator.authenticate(credentials, afterSuccess);
    }

    private Credentials credentials(String email, String password) {
        return new Credentials(email, password);
    }

    private void verifyPostWith(String path, Credentials credentials) throws JSONException {
        ArgumentCaptor<JSONObject> captor = forClass(JSONObject.class);
        verify(mockBackend).postRequestForJson(eq(path), captor.capture(), any(Consumer.class), any(Runnable.class));
        assertEquals(jsonMapper.jsonOf(credentials), captor.getValue(), true);
    }

    private Answer failed() {
        return new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Runnable runnable = invocation.getArgument(3);
                runnable.run();
                return null;
            }
        };
    }

    private void given_jsonbackend_will_response(Answer answer) {
        doAnswer(answer).when(mockBackend).postRequestForJson(anyString(), any(JSONObject.class), any(Consumer.class), any(Runnable.class));
    }

    @NonNull
    private Answer success() {
        return new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(2);
                consumer.accept(new JSONObject());
                return null;
            }
        };
    }

}