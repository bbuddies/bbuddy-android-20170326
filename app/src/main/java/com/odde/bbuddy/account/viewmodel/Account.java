package com.odde.bbuddy.account.viewmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Account implements Serializable {

    private String name;
    @JsonProperty("balance")
    private int balanceBroughtForward;
    private int id;

}
