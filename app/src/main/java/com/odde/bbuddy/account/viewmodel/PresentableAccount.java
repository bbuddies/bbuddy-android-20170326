package com.odde.bbuddy.account.viewmodel;

import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.ItemPresentationModel;

public class PresentableAccount implements ItemPresentationModel<Account> {

    private Account account;

    @Override
    public void updateData(Account account, ItemContext itemContext) {
        this.account = account;
    }

    public String getDisplayOfAccount() {
        return account.getDisplayOfAccount();
    }
}
