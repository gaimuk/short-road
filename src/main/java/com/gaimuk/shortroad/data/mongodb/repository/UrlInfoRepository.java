package com.gaimuk.shortroad.data.mongodb.repository;

import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface UrlInfoRepository extends CrudRepository<UrlInfo, String>, UrlInfoRepositoryCustom {

    UrlInfo findByUrlSeq(Long urlSeq);
}
