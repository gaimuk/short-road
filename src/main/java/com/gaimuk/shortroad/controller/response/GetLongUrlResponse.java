package com.gaimuk.shortroad.controller.response;

public class GetLongUrlResponse {

    private String url;

    public GetLongUrlResponse() {

    }

    public GetLongUrlResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
