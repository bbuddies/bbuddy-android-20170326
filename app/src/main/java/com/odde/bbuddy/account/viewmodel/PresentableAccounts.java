package com.odde.bbuddy.account.viewmodel;

import com.odde.bbuddy.account.model.Accounts;
import com.odde.bbuddy.account.view.EditDeleteAccountNavigation;
import com.odde.bbuddy.common.Consumer;

import org.robobinding.annotation.ItemPresentationModel;
import org.robobinding.annotation.PresentationModel;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@PresentationModel
public class PresentableAccounts implements HasPresentationModelChangeSupport {

    private final PresentationModelChangeSupport changeSupport = new PresentationModelChangeSupport(this);
    private final EditDeleteAccountNavigation editDeleteAccountNavigation;
    private final List<Account> allAccounts = new ArrayList<>();

    @Inject
    public PresentableAccounts(Accounts accounts, EditDeleteAccountNavigation editDeleteAccountNavigation) {
        this.editDeleteAccountNavigation = editDeleteAccountNavigation;
        accounts.processAllAccounts(new Consumer<List<Account>>() {
            @Override
            public void accept(List<Account> list) {
                allAccounts.addAll(list);
                changeSupport.refreshPresentationModel();
            }
        });
    }

    @ItemPresentationModel(value = PresentableAccount.class)
    public List<Account> getAccounts() {
        return allAccounts;
    }

    public void updateAccount(ItemClickEvent event) {
        editDeleteAccountNavigation.navigate(accountOf(event));
    }

    private Account accountOf(ItemClickEvent event) {
        return allAccounts.get(event.getPosition());
    }

    @Override
    public PresentationModelChangeSupport getPresentationModelChangeSupport() {
        return changeSupport;
    }
}
