package com.gaimuk.shortroad.data.mongodb.repository;

import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlInfoRepository extends MongoRepository<UrlInfo, String> {

    UrlInfo findByUrlSeq(Long urlSeq);
}
