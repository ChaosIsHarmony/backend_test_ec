package com.sidc.backend_test_ec.utils.validators;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseServiceValidator {
    String classAndMethod;

    Map<String, Object> payloads = new HashMap<>();
    ResponseEntity<?> error;
    boolean hasError = false;

    public BaseServiceValidator(String classAndMethod) {
        this.classAndMethod = classAndMethod;
    }

    public Object getPayload(String key) {
        return this.payloads.get(key);
    }

    public ResponseEntity<?> getError() {
        return this.error;
    }

    protected void setError(ResponseEntity<?> error) {
        this.hasError = true;
        this.error = error;
    }

    public boolean hasError() {
        return this.hasError;
    }

    protected void addPayload(String key, Object payload) {
        this.payloads.put(key, payload);
    }
}
