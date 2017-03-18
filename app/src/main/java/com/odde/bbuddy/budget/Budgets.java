package com.odde.bbuddy.budget;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.odde.bbuddy.common.Consumer;
import com.odde.bbuddy.common.JsonBackend;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Budgets {

    private final List<Budget> allBudgets = new ArrayList<>();
    private final JsonBackend jsonBackend;

    public Budgets(JsonBackend jsonBackend) {
        this.jsonBackend = jsonBackend;
    }

    public void add(Budget budget) {
        allBudgets.add(budget);
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

    @NonNull
    private JSONObject jsonOf(Budget budget) {
        try {
            return new JSONObject(new ObjectMapper().writeValueAsString(budget));
        } catch (JSONException e) {
            throw new IllegalStateException();
        } catch (JsonProcessingException e) {
            throw new IllegalStateException();
        }
    }

    public List<Budget> getAllBudgets() {
        return allBudgets;
    }
}
