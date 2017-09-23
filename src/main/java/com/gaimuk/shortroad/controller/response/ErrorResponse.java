package com.gaimuk.shortroad.controller.response;

import java.util.Map;

/**
 * Abstract class representing a generic error response
 */
public abstract class ErrorResponse {

    private String code;

    private String message;

    private Map<String, String> details;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
}
