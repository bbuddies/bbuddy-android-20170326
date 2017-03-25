package com.odde.bbuddy.account.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nitorcreations.junit.runners.NestedRunner;
import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.authentication.AuthenticationToken;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonBackendMock;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(NestedRunner.class)
public class AccountsTest {

    JsonBackend mockJsonBackend = mock(JsonBackend.class);
    JsonMapper<Account> jsonMapper = new JsonMapper<>(Account.class);
    AuthenticationToken authenticationToken = new AuthenticationToken();
    Accounts accounts = new Accounts(mockJsonBackend, jsonMapper, authenticationToken);
    JsonBackendMock<Account> jsonBackendMock = new JsonBackendMock<>(mockJsonBackend, Account.class);
    Runnable mockRunnable = mock(Runnable.class);
    private static final int ID = 1;
    Account account = account(ID, "name", 1000);

    public class AddAccount {

        @Ignore @Test
        public void add_account_with_name_and_balance_brought_forward() throws JSONException {
            accounts.addAccount(account("name", 1000), mockRunnable);

            jsonBackendMock.verifyPostWith("/accounts", account("name", 1000));
        }

        @Ignore @Test
        public void add_account_successfully() {
            jsonBackendMock.givenPostWillSuccess();

            accounts.addAccount(account, mockRunnable);

            verify(mockRunnable).run();
        }

    }

    public class EditAccount {

        @Test
        public void edit_account_with_id_name_and_balance_brought_forward() throws JSONException {
            accounts.editAccount(account(ID, "name", 1000), mockRunnable);

            jsonBackendMock.verifyPutWith("/accounts/" + ID, account(ID, "name", 1000));
        }

        @Test
        public void edit_account_successfully() {
            jsonBackendMock.givenPutWillSuccess();

            accounts.editAccount(account, mockRunnable);

            verify(mockRunnable).run();
        }

    }

    public class DeleteAccount {

        @Test
        public void delete_account_with_id() {
            accounts.deleteAccount(account(ID), mockRunnable);

            jsonBackendMock.verifyDeleteWith("/accounts/" + ID);
        }

        @Test
        public void delete_account_successfully() {
            jsonBackendMock.givenDeleteWillSuccess();

            accounts.deleteAccount(account(ID), mockRunnable);

            verify(mockRunnable).run();
        }

    }

    public class ProcessAllAccounts {

        Consumer mockConsumer = mock(Consumer.class);

        @Test
        public void all_accounts_with_authentication_headers() {
            processAllAccounts();

            jsonBackendMock.verifyGetWith("/accounts");
        }

        @Test
        public void all_accounts_with_some_data_from_backend() throws JSONException, JsonProcessingException {
            jsonBackendMock.givenGetWillReturnList(asList(account("name", 1000)));

            processAllAccounts();

            verifyAccountConsumed(account("name", 1000));
        }

        private void verifyAccountConsumed(Account account) {
            ArgumentCaptor<List<Account>> captor = ArgumentCaptor.forClass(List.class);
            verify(mockConsumer).accept(captor.capture());
            assertThat(captor.getValue()).usingFieldByFieldElementComparator().isEqualTo(asList(account));
        }

        private void processAllAccounts() {
            accounts.processAllAccounts(mockConsumer);
        }
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
