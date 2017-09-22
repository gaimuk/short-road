package com.gaimuk.shortroad.data.mongodb.repository;

import com.gaimuk.shortroad.data.mongodb.document.UrlSeq;
import org.springframework.data.repository.CrudRepository;

public interface UrlSeqRepository extends CrudRepository<UrlSeq, String>, UrlSeqRepositoryCustom {

}
