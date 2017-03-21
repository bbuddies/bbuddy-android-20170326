package com.odde.bbuddy.account.viewmodel;

import android.support.annotation.NonNull;

import com.odde.bbuddy.account.model.Accounts;
import com.odde.bbuddy.account.view.EditDeleteAccountNavigation;
import com.odde.bbuddy.common.Consumer;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelChangeSupport;
import org.robobinding.widget.adapterview.ItemClickEvent;

import java.util.List;

import dagger.Lazy;

import static com.odde.bbuddy.common.CallbackInvoker.callConsumerArgumentAtIndexWith;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PresentableAccountsTest {

    Accounts mockAccounts = mock(Accounts.class);
    EditDeleteAccountNavigation mockEditDeleteAccountNavigation = mock(EditDeleteAccountNavigation.class);
    Lazy<PresentationModelChangeSupport> mockChangeSupportLazyLoader = mock(Lazy.class);
    PresentationModelChangeSupport mockPresentationModelChangeSupport = mock(PresentationModelChangeSupport.class);
    private final Account account = new Account() {{ setId(1); setName("name"); setBalanceBroughtForward(100); }};
    private PresentableAccounts presentableAccounts = createPresentableAccounts();

    @Before
    public void given_lazy_loader_will_return_change_support() {
        when(mockChangeSupportLazyLoader.get()).thenReturn(mockPresentationModelChangeSupport);
    }

    @Test
    public void get_all_accounts() {
        given_accounts_will_return(asList(account));

        assertThat(createPresentableAccounts().getAccounts()).isEqualTo(asList(account));
    }

    @Test
    public void refresh() {
        given_accounts_will_return(asList(account));

        presentableAccounts.refresh();

        verify(mockPresentationModelChangeSupport).refreshPresentationModel();
        assertThat(presentableAccounts.getAccounts()).isEqualTo(asList(account));
    }
    
    @Test
    public void update_account_should_navigate_to_edit_delete_view() {
        given_accounts_will_return(asList(account));

        createPresentableAccounts().updateAccount(stubItemClickEventAtPosition(0));

        verify(mockEditDeleteAccountNavigation).navigate(account);
    }

    @NonNull
    private ItemClickEvent stubItemClickEventAtPosition(int position) {
        ItemClickEvent stubItemContext = mock(ItemClickEvent.class);
        when(stubItemContext.getPosition()).thenReturn(position);
        return stubItemContext;
    }

    private void given_accounts_will_return(final List<Account> allAccounts) {
        callConsumerArgumentAtIndexWith(0, allAccounts).when(mockAccounts).processAllAccounts(any(Consumer.class));
    }

    private PresentableAccounts createPresentableAccounts() {
        return new PresentableAccounts(mockAccounts, mockEditDeleteAccountNavigation, mockChangeSupportLazyLoader);
    }

}