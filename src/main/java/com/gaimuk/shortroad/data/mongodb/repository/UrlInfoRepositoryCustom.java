package com.gaimuk.shortroad.data.mongodb.repository;

import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;

public interface UrlInfoRepositoryCustom {

    UrlInfo saveAndGenerateSeq(UrlInfo urlInfo);
}
