package com.gaimuk.shortroad.controller.response;

public class UrlNotFoundErrorResponse extends ErrorResponse {

    public UrlNotFoundErrorResponse() {
        super();
        this.setCode("E002");
        this.setMessage("URL not found");
    }
}
