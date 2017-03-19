package com.odde.bbuddy.account.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nitorcreations.junit.runners.NestedRunner;
import com.odde.bbuddy.account.viewmodel.Account;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;
import com.odde.bbuddy.common.JsonMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(NestedRunner.class)
public class AccountsTest {

    JsonBackend mockJsonBackend = mock(JsonBackend.class);
    JsonMapper<Account> jsonMapper = new JsonMapper<>(Account.class);
    Accounts accounts = new Accounts(mockJsonBackend, jsonMapper);
    Runnable mockRunnable = mock(Runnable.class);
    private static final int ID = 1;
    Account account = account(ID, "name", 1000);

    public class AddAccount {

        @Test
        public void add_account_with_name_and_balance_brought_forward() throws JSONException {
            accounts.addAccount(account("name", 1000), mockRunnable);

            verifyPostWith("/accounts", account("name", 1000));
        }

        @Test
        public void add_account_successfully() {
            given_backend_will_success();

            accounts.addAccount(account, mockRunnable);

            verify(mockRunnable).run();
        }

        private void verifyPostWith(String path, Account account) throws JSONException {
            ArgumentCaptor<JSONObject> captor = ArgumentCaptor.forClass(JSONObject.class);
            verify(mockJsonBackend).postRequestForJson(eq(path), captor.capture(), any(Consumer.class), any(Runnable.class));
            JSONAssert.assertEquals(jsonMapper.jsonOf(account), captor.getValue(), true);
        }

        private void given_backend_will_success() {
            callJsonConsumerArgumentAtIndex(2, new JSONObject()).when(mockJsonBackend).postRequestForJson(anyString(), any(JSONObject.class), any(Consumer.class), any(Runnable.class));
        }
    }

    public class EditAccount {

        @Test
        public void edit_account_with_id_name_and_balance_brought_forward() throws JSONException {
            accounts.editAccount(account(ID, "name", 1000), mockRunnable);

            verifyPutWith("/accounts/" + ID, account(ID, "name", 1000));
        }

        @Test
        public void edit_account_successfully() {
            given_backend_will_success();

            accounts.editAccount(account, mockRunnable);

            verify(mockRunnable).run();
        }

        private void verifyPutWith(String path, Account account) throws JSONException {
            ArgumentCaptor<JSONObject> captor = ArgumentCaptor.forClass(JSONObject.class);
            verify(mockJsonBackend).putRequestForJson(eq(path), captor.capture(), any(Consumer.class), any(Runnable.class));
            JSONAssert.assertEquals(jsonMapper.jsonOf(account), captor.getValue(), true);
        }

        private void given_backend_will_success() {
            callJsonConsumerArgumentAtIndex(2, new JSONObject()).when(mockJsonBackend).putRequestForJson(anyString(), any(JSONObject.class), any(Consumer.class), any(Runnable.class));
        }

    }

    public class DeleteAccount {

        @Test
        public void delete_account_with_id() {
            accounts.deleteAccount(account(ID), mockRunnable);

            verifyDeleteWith("/accounts/" + ID);
        }

        @Test
        public void delete_account_successfully() {
            given_backend_will_success();

            accounts.deleteAccount(account(ID), mockRunnable);

            verify(mockRunnable).run();
        }

        private void verifyDeleteWith(String path) {
            verify(mockJsonBackend).deleteRequestForJson(eq(path), any(Consumer.class), any(Runnable.class));
        }

        private void given_backend_will_success() {
            callJsonConsumerArgumentAtIndex(1, new JSONObject()).when(mockJsonBackend).deleteRequestForJson(anyString(), any(Consumer.class), any(Runnable.class));
        }

    }

    public class ProcessAllAccounts {

        Consumer mockConsumer = mock(Consumer.class);

        @Test
        public void all_accounts_with_authentication_headers() {
            processAllAccounts();

            verifyGetWith("/accounts");
        }

        @Test
        public void all_accounts_with_some_data_from_backend() throws JSONException, JsonProcessingException {
            given_backend_return_json_with_account(account("name", 1000));

            processAllAccounts();

            verifyAccountConsumed("name", 1000);
        }

        private void verifyGetWith(String path) {
            verify(mockJsonBackend).getRequestForJsonArray(eq(path), any(Consumer.class));
        }

        private void verifyAccountConsumed(String name, int balance) {
            ArgumentCaptor<List<Account>> captor = ArgumentCaptor.forClass(List.class);
            verify(mockConsumer).accept(captor.capture());
            assertEquals(1, captor.getValue().size());
            assertEquals(name, captor.getValue().get(0).getName());
            assertEquals(balance, captor.getValue().get(0).getBalanceBroughtForward());
        }

        private void given_backend_return_json_with_account(final Account account) throws JSONException, JsonProcessingException {
            callJsonConsumerArgumentAtIndexWithJsonArray(1, jsonArrayOf(account)).when(mockJsonBackend).getRequestForJsonArray(anyString(), any(Consumer.class));
        }

        private void processAllAccounts() {
            accounts.processAllAccounts(mockConsumer);
        }
    }

    private Stubber callJsonConsumerArgumentAtIndex(final int index, final JSONObject data) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(index);
                consumer.accept(data);
                return null;
            }
        });
    }

    private Stubber callJsonConsumerArgumentAtIndexWithJsonArray(final int index, final JSONArray data) {
        return doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Consumer consumer = invocation.getArgument(index);
                consumer.accept(data);
                return null;
            }
        });
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

    private JSONArray jsonArrayOf(Account account) throws JSONException, JsonProcessingException {
        return new JSONArray(new ObjectMapper().writeValueAsString(asList(account)));
    }

}
