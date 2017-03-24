package com.odde.bbuddy.common;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static com.odde.bbuddy.common.CallbackInvoker.callConsumerArgumentAtIndexWith;
import static com.odde.bbuddy.common.CallbackInvoker.callRunnableAtIndex;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

public class JsonBackendMock<T> {

    private final JsonBackend mockJsonBackend;
    private final JsonMapper<T> jsonMapper;

    public JsonBackendMock(JsonBackend mockJsonBackend, Class<T> clazz) {
        this.mockJsonBackend = mockJsonBackend;
        this.jsonMapper = new JsonMapper<>(clazz);
    }

    public void verifyPostWith(String path, T t) throws JSONException {
        ArgumentCaptor<JSONObject> captor = ArgumentCaptor.forClass(JSONObject.class);
        verify(mockJsonBackend).postRequestForJson(eq(path), captor.capture(), any(Consumer.class), any(Runnable.class));
        assertEquals(jsonMapper.jsonOf(t), captor.getValue(), true);
    }

    public void verifyGetWith(String path) {
        verify(mockJsonBackend).getRequestForJsonArray(eq(path), any(Consumer.class));
    }

    public void verifyPutWith(String path, T t) throws JSONException {
        ArgumentCaptor<JSONObject> captor = ArgumentCaptor.forClass(JSONObject.class);
        verify(mockJsonBackend).putRequestForJson(eq(path), captor.capture(), any(Consumer.class), any(Runnable.class));
        assertEquals(jsonMapper.jsonOf(t), captor.getValue(), true);
    }

    public void verifyDeleteWith(String path) {
        verify(mockJsonBackend).deleteRequestForJson(eq(path), any(Consumer.class), any(Runnable.class));
    }

    public void givenPostWillSuccess() {
        callConsumerArgumentAtIndexWith(2, new JSONObject()).when(mockJsonBackend).postRequestForJson(anyString(), any(JSONObject.class), any(Consumer.class), any(Runnable.class));
    }

    public void givenPostWillFail() {
        callRunnableAtIndex(3).when(mockJsonBackend).postRequestForJson(anyString(), any(JSONObject.class), any(Consumer.class), any(Runnable.class));
    }

    public void givenGetWillReturnList(final List<T> listOfT) throws JSONException, JsonProcessingException {
        callConsumerArgumentAtIndexWith(1, jsonMapper.jsonArrayOf(listOfT)).when(mockJsonBackend).getRequestForJsonArray(anyString(), any(Consumer.class));
    }

    public void givenPutWillSuccess() {
        callConsumerArgumentAtIndexWith(2, new JSONObject()).when(mockJsonBackend).putRequestForJson(anyString(), any(JSONObject.class), any(Consumer.class), any(Runnable.class));
    }

    public void givenDeleteWillSuccess() {
        callConsumerArgumentAtIndexWith(1, new JSONObject()).when(mockJsonBackend).deleteRequestForJson(anyString(), any(Consumer.class), any(Runnable.class));
    }

}
