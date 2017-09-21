package com.gaimuk.shortroad.data.mongodb.entity;

import org.springframework.data.annotation.Id;

public class UrlPair {

    @Id
    private String id;

    private String bigUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigUrl() {
        return bigUrl;
    }

    public void setBigUrl(String bigUrl) {
        this.bigUrl = bigUrl;
    }
}
