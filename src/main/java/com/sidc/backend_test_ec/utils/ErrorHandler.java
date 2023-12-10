package com.sidc.backend_test_ec.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

public class ErrorHandler {

    public static ResponseEntity<?> handleValidationErrors(String classAndMethod, Errors errors) {
        StringBuilder errorMessage = new StringBuilder();
        for (ObjectError objectError : errors.getAllErrors()) {
            errorMessage.append(objectError.getDefaultMessage()).append(" ");
        }
        logEvent("handleValidationErrors: " + classAndMethod, errorMessage.toString());
        return new ResponseEntity<>("Invalid Request.", HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ResponseEntity<?> handleConflictErrors(String classAndMethod, String msg, String item) {
        logEvent("handleConflictErrors: " + classAndMethod, msg);
        return new ResponseEntity<>(item + " already exists.", HttpStatus.CONFLICT);
    }

    public static ResponseEntity<?> handleEntityNotFoundErrors(String classAndMethod, String msg, String item) {
        logEvent("handleEntityNotFoundErrors: " + classAndMethod, msg);
        return new ResponseEntity<>(item + " does not exist.", HttpStatus.NOT_FOUND);

    }

    public static ResponseEntity<?> handleExternalAPIError(String classAndMethod, String msg) {
        logEvent("handleExternalAPIError: " + classAndMethod, msg);
        return new ResponseEntity<>(msg, HttpStatus.SERVICE_UNAVAILABLE);
    }

    // -----------------------------------------
    // -------------HELPER METHODS--------------
    // -----------------------------------------
    private static void logEvent(String source, String msg) {
        System.err.println("ErrorHandler:" + source);
        System.err.println("Error: " + msg);
    }

}
