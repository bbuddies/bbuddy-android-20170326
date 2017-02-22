package com.odde.bbuddy.account.viewmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.odde.bbuddy.account.model.Accounts;
import com.odde.bbuddy.account.view.Navigation;
import com.odde.bbuddy.account.view.ShowAllAccounts;

import org.robobinding.annotation.PresentationModel;

import java.io.Serializable;

import javax.inject.Inject;
import javax.inject.Singleton;

@JsonIgnoreProperties(ignoreUnknown = true)
@PresentationModel
@Singleton
public class Account implements Serializable {

    private Accounts accounts;
    private Navigation showAllAccounts;
    private String name;

    @JsonProperty("balance")
    private int balanceBroughtForward;
    private int id;

    public Account() {}

    @Inject
    public Account(Accounts accounts, ShowAllAccounts showAllAccounts) {
        this.accounts = accounts;
        this.showAllAccounts = showAllAccounts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBalanceBroughtForward() {
        return balanceBroughtForward;
    }

    public void setBalanceBroughtForward(int balanceBroughtForward) {
        this.balanceBroughtForward = balanceBroughtForward;
    }

    @JsonIgnore
    public String getDisplayOfAccount() {
        return name + " " + balanceBroughtForward;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonIgnore
    public String getBalanceBroughtForwardForView() {
        return String.valueOf(balanceBroughtForward);
    }

    public void setBalanceBroughtForwardForView(String balanceBroughtForward) {
        try {
            this.balanceBroughtForward = Integer.parseInt(balanceBroughtForward);
        } catch (NumberFormatException e) {

        }
    }

    public void add() {
        accounts.addAccount(this, new Runnable() {
            @Override
            public void run() {
                showAllAccounts.navigate();
            }
        });
    }

    public void update() {
        accounts.editAccount(this, new Runnable() {
            @Override
            public void run() {
                showAllAccounts.navigate();
            }
        });
    }

    public void delete() {
        accounts.deleteAccount(this, new Runnable() {
            @Override
            public void run() {
                showAllAccounts.navigate();
            }
        });
    }

}
