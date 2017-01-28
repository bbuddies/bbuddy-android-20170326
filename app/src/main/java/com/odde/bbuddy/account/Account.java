package com.odde.bbuddy.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private String name;

    @JsonProperty("balance")
    private int balanceBroughtForward;

    public String getName() {
        return name;
    }

    public int getBalanceBroughtForward() {
        return balanceBroughtForward;
    }

}
