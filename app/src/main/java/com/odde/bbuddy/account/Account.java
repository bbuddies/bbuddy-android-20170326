package com.odde.bbuddy.account;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.odde.bbuddy.common.JsonBackend;

import org.robobinding.annotation.PresentationModel;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@PresentationModel
public class Account implements Serializable {

    private AddAccountView view;
    private String name;

    @JsonProperty("balance")
    private int balanceBroughtForward;
    private int id;

    @JsonCreator
    public Account(@JsonProperty("name") String name, @JsonProperty("balance") int balanceBroughtForward) {
        this.name = name;
        this.balanceBroughtForward = balanceBroughtForward;
    }

    public Account(AddAccountView view) {
        this.view = view;
    }

    public String getName() {
        return name;
    }

    public int getBalanceBroughtForward() {
        return balanceBroughtForward;
    }

    @Override
    public String toString() {
        return name + " " + balanceBroughtForward;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalanceBroughtForward(int balanceBroughtForward) {
        this.balanceBroughtForward = balanceBroughtForward;
    }

    public void setBalanceBroughtForwardForView(String balanceBroughtForward) {
        try {
            this.balanceBroughtForward = Integer.parseInt(balanceBroughtForward);
        } catch (NumberFormatException e) {

        }
    }

    @JsonIgnore
    public String getBalanceBroughtForwardForView() {
        return String.valueOf(balanceBroughtForward);
    }

    public void add() {
        new Accounts(new JsonBackend(view.getApplicationContext())).addAccount(this, new Runnable() {
            @Override
            public void run() {
                view.showAllAccounts();
            }
        });
    }
}
