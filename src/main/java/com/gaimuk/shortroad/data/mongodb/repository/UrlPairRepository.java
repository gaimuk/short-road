package com.gaimuk.shortroad.data.mongodb.repository;

import com.gaimuk.shortroad.data.mongodb.entity.UrlPair;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlPairRepository extends MongoRepository<UrlPair, String> {
}
