package com.gaimuk.shortroad.controller.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateTinyUrlRequest {

    @NotNull
    @Size(min = 1, max = 4096)
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
