package com.gaimuk.shortroad.controller.response;

import java.util.Map;

public class ValidationErrorResponse extends ErrorResponse {

    public ValidationErrorResponse(Map<String, String> details) {
        super();
        this.setCode("E001");
        this.setMessage("Validation error");
        this.setDetails(details);
    }
}
