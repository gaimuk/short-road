package com.gaimuk.shortroad.data.mongodb.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UrlInfo {

    @Id
    private String id;

    @Indexed
    private Long urlSeq;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getUrlSeq() {
        return urlSeq;
    }

    public void setUrlSeq(Long urlSeq) {
        this.urlSeq = urlSeq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
