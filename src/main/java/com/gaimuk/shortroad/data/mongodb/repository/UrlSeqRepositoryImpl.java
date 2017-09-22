package com.gaimuk.shortroad.data.mongodb.repository;

import com.gaimuk.shortroad.data.mongodb.document.UrlSeq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class UrlSeqRepositoryImpl implements UrlSeqRepositoryCustom {

    @Autowired
    private MongoOperations operation;

    @Override
    public Long next() {
        final Query query = new Query().addCriteria(Criteria.where("_id").is(UrlSeq.class.getSimpleName()));
        final Update update = new Update().inc("seq", 1);
        final FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);

        return operation.findAndModify(query, update, options, UrlSeq.class).getSeq();
    }
}
