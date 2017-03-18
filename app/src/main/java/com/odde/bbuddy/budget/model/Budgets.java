package com.odde.bbuddy.budget.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.budget.viewmodel.Budget;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Budgets {

    private final JsonBackend jsonBackend;

    public Budgets(JsonBackend jsonBackend) {
        this.jsonBackend = jsonBackend;
    }

    public void add(Budget budget) {
        jsonBackend.postRequestForJson("/budgets", jsonOf(budget), new Consumer<JSONObject>() {
            @Override
            public void accept(JSONObject jsonObject) {

            }
        }, new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    private JSONObject jsonOf(Budget budget) {
        try {
            return new JSONObject(new ObjectMapper().writeValueAsString(budget));
        } catch (JSONException e) {
            throw new IllegalStateException();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }

    public void processAllBudgets(final Consumer<List<Budget>> consumer) {
        jsonBackend.getRequestForJsonArray("/budgets", new Consumer<JSONArray>() {
            @Override
            public void accept(JSONArray response) {
                consumer.accept(budgetsFrom(response));
            }
        });
    }

    private List<Budget> budgetsFrom(JSONArray response) {
        try {
            return new ObjectMapper().readValue(response.toString(), new TypeReference<List<Budget>>(){});
        } catch (IOException e) {
            throw new IllegalStateException();
        }
    }
}
