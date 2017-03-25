package com.odde.bbuddy.account.viewmodel;

import com.nitorcreations.junit.runners.NestedRunner;
import com.odde.bbuddy.account.api.AccountsApi;
import com.odde.bbuddy.account.view.ShowAllAccountsNavigation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import static com.odde.bbuddy.common.CallbackInvoker.callRunnableAtIndex;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(NestedRunner.class)
public class EditableAccountTest {

    AccountsApi mockAccountsApi = mock(AccountsApi.class);
    ShowAllAccountsNavigation mockShowAllAccountsNavigation = mock(ShowAllAccountsNavigation.class);
    EditableAccount editableAccount = new EditableAccount(mockAccountsApi, mockShowAllAccountsNavigation);

    public class Add {

        @Test
        public void add_should_correctly_add_account() {
            addAccount("name", 100);

            verifyAccountsAddWithAccount(account("name", 100));
        }

        @Test
        public void add_should_show_all_accounts_after_add_account_success() {
            given_add_account_will_success();

            addAccount("name", 100);

            verify(mockShowAllAccountsNavigation).navigate();
        }

        private void given_add_account_will_success() {
            callRunnableAtIndex(1).when(mockAccountsApi).addAccount(any(Account.class), any(Runnable.class));
        }

        private void verifyAccountsAddWithAccount(Account account) {
            ArgumentCaptor<Account> captor = forClass(Account.class);
            verify(mockAccountsApi).addAccount(captor.capture(), any(Runnable.class));
            assertThat(captor.getValue()).isEqualToComparingFieldByField(account);
        }

        private void addAccount(String name, int balanceBroughtForward) {
            editableAccount.setName(name);
            editableAccount.setBalanceBroughtForward(balanceBroughtForward);
            editableAccount.add();
        }
    }

    public class Edit {

        @Test
        public void edit_should_correctly_edit_account() {
            given_account_id_is(1);

            editAccount("name", 100);

            verifyAccountsEditWithAccount(account(1, "name", 100));
        }

        @Test
        public void edit_should_show_all_accounts_after_success() {
            given_account_id_is(1);
            given_edit_account_will_success();

            editAccount("name", 100);

            verify(mockShowAllAccountsNavigation).navigate();
        }

        private void editAccount(String name, int balanceBroughtForward) {
            editableAccount.setName(name);
            editableAccount.setBalanceBroughtForward(balanceBroughtForward);
            editableAccount.update();
        }

        private void verifyAccountsEditWithAccount(Account account) {
            ArgumentCaptor<Account> captor = forClass(Account.class);
            verify(mockAccountsApi).editAccount(captor.capture(), any(Runnable.class));
            assertThat(captor.getValue()).isEqualToComparingFieldByField(account);
        }

        private void given_edit_account_will_success() {
            callRunnableAtIndex(1).when(mockAccountsApi).editAccount(any(Account.class), any(Runnable.class));
        }
    }

    public class Delete {

        @Test
        public void delete_should_correctly_delete_account() {
            given_account_id_is(1);

            editableAccount.delete();

            verifyAccountsDeleteWithAccount(account(1));
        }

        @Test
        public void delete_should_show_all_accounts_after_success() {
            given_account_id_is(1);
            given_account_delete_will_success();

            editableAccount.delete();

            verify(mockShowAllAccountsNavigation).navigate();
        }

        private void verifyAccountsDeleteWithAccount(Account account) {
            ArgumentCaptor<Account> captor = forClass(Account.class);
            verify(mockAccountsApi).deleteAccount(captor.capture(), any(Runnable.class));
            assertThat(captor.getValue()).isEqualToComparingFieldByField(account);
        }

        private void given_account_delete_will_success() {
            callRunnableAtIndex(1).when(mockAccountsApi).deleteAccount(any(Account.class), any(Runnable.class));
        }

    }

    public class ShowBalance {

        @Test
        public void display_balance_brought_forward_for_view() {
            editableAccount.setBalanceBroughtForward(100);

            assertEquals("100", editableAccount.getBalanceBroughtForwardForView());
        }

        @Test
        public void get_balance_brought_forward_from_view() {
            editableAccount.setBalanceBroughtForwardForView("100");

            assertEquals(100, editableAccount.getBalanceBroughtForward());
        }

        @Test
        public void value_not_changed_if_balance_brought_forward_from_view_is_not_a_number() {
            editableAccount.setBalanceBroughtForward(50);

            editableAccount.setBalanceBroughtForwardForView("not a number");

            assertEquals(50, editableAccount.getBalanceBroughtForward());
        }

    }

    private void given_account_id_is(int id) {
        editableAccount.setId(id);
    }

    private Account account(String name, int balanceBroughtForward) {
        return account(0, name, balanceBroughtForward);
    }

    private Account account(int id) {
        Account account = new Account();
        account.setId(id);
        return account;
    }

    private Account account(int id, String name, int balanceBroughtForward) {
        Account account = new Account();
        account.setId(id);
        account.setName(name);
        account.setBalanceBroughtForward(balanceBroughtForward);
        return account;
    }
}
