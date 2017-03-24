package com.odde.bbuddy.budget.viewmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Budget {
    private String month;
    private int amount;

}
