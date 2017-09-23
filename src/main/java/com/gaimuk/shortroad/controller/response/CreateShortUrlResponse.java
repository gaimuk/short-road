package com.gaimuk.shortroad.controller.response;

public class CreateShortUrlResponse {

    private String longUrl;

    private String shortUrlToken;

    private String redirectUrl;

    public CreateShortUrlResponse() {

    }

    public CreateShortUrlResponse(String longUrl, String redirectUrl, String shortUrlToken) {
        this.longUrl = longUrl;
        this.redirectUrl = redirectUrl;
        this.shortUrlToken = shortUrlToken;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getShortUrlToken() {
        return shortUrlToken;
    }

    public void setShortUrlToken(String shortUrlToken) {
        this.shortUrlToken = shortUrlToken;
    }
}
