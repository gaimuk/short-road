package com.gaimuk.shortroad.data.mongodb.repository;

import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlInfoRepositoryImpl implements UrlInfoRepositoryCustom {

    @Autowired
    private UrlInfoRepository urlInfoRepository;

    @Autowired
    private UrlSeqRepository urlSeqRepository;

    @Override
    public UrlInfo saveAndGenerateSeq(UrlInfo urlInfo) {
        final long seq = urlSeqRepository.next();
        urlInfo.setUrlSeq(seq);
        return urlInfoRepository.save(urlInfo);
    }
}
