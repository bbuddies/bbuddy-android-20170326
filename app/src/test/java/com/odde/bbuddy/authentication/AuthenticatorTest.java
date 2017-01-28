package com.odde.bbuddy.authentication;

import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AuthenticatorTest {

    JsonBackend mockBackend = mock(JsonBackend.class);
    AuthenticationToken mockAuthenticationToken = mock(AuthenticationToken.class);
    Authenticator authenticator = new Authenticator(mockBackend, mockAuthenticationToken);
    Credentials credentials = new Credentials("abc@gmail.com", "password");
    Consumer afterSuccess = mock(Consumer.class);

    @Test
    public void authenticate_with_user_name_and_password() throws JSONException {
        authenticator.authenticate(credentials, afterSuccess);

        ArgumentCaptor<JSONObject> captor = ArgumentCaptor.forClass(JSONObject.class);
        verify(mockBackend).postRequestForJson(eq("/auth/sign_in"), captor.capture(), any(Consumer.class), any(Runnable.class), any(Consumer.class));
        assertEquals("abc@gmail.com", captor.getValue().getString("email"));
        assertEquals("password", captor.getValue().getString("password"));
    }

    @Test
    public void authenticate_successful() {
        given_jsonbackend_will_successfully_response();

        authenticator.authenticate(credentials, afterSuccess);

        verify(afterSuccess).accept("success");
    }

    private void given_jsonbackend_will_successfully_response() {
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(2);
                consumer.accept(new JSONObject());
                return null;
            }
        }).when(mockBackend).postRequestForJson(anyString(), any(JSONObject.class), any(Consumer.class), any(Runnable.class), any(Consumer.class));
    }

}