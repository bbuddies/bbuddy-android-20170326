package com.odde.bbuddy.authentication;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AuthenticationTokenTest {

    AuthenticationToken token = new AuthenticationToken();
    Map<String, String> headers = new HashMap<String, String>() {{
        put("access-token", "access-token");
        put("token-type", "token-type");
        put("uid", "uid");
        put("client", "client");
        put("expiry", "expiry");
    }};
    Map<String, String> headersWithoutAuthenticationToken = new HashMap<>();

    @Test
    public void update_token_and_get_headers() {
        token.updateByHeaders(headers);

        assertThat(token.getHeaders()).isEqualTo(headers);
    }

    @Test
    public void will_not_update_token_when_authentication_token_headers_not_exist() {
        given_token_already_updated_with_headers();

        token.updateByHeaders(headersWithoutAuthenticationToken);

        assertThat(token.getHeaders()).isEqualTo(headers);
    }

    private void given_token_already_updated_with_headers() {
        token.updateByHeaders(headers);
    }

}