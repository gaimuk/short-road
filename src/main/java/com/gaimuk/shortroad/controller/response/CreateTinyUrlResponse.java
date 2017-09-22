package com.gaimuk.shortroad.controller.response;

public class CreateTinyUrlResponse {

    private String bigUrl;

    private String tinyUrl;

    private String tinyUrlToken;

    public CreateTinyUrlResponse() {

    }

    public CreateTinyUrlResponse(String bigUrl, String tinyUrl, String tinyUrlToken) {
        this.bigUrl = bigUrl;
        this.tinyUrl = tinyUrl;
        this.tinyUrlToken = tinyUrlToken;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }

    public String getTinyUrl() {
        return tinyUrl;
    }

    public void setTinyUrl(String tinyUrl) {
        this.tinyUrl = tinyUrl;
    }

    public String getTinyUrlToken() {
        return tinyUrlToken;
    }

    public void setTinyUrlToken(String tinyUrlToken) {
        this.tinyUrlToken = tinyUrlToken;
    }
}
