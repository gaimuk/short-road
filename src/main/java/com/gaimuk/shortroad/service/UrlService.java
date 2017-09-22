package com.gaimuk.shortroad.service;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;
import com.gaimuk.shortroad.data.mongodb.repository.UrlSeqRepository;
import com.gaimuk.shortroad.data.mongodb.repository.UrlInfoRepository;
import com.gaimuk.shortroad.common.util.Base58Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    private UrlInfoRepository urlInfoRepository;

    @Autowired
    private UrlSeqRepository urlSeqRepository;

    /**
     * Persists the big URL and return the base62-encoded seq number
     *
     * @param bigUrl
     * @return
     */
    public String shorten(final String bigUrl) {
        // Construct UrlInfo with the generated TinyUrl ID
        UrlInfo inputUrlInfo = new UrlInfo();
        inputUrlInfo.setUrl(bigUrl);
        inputUrlInfo.setUrlSeq(urlSeqRepository.next());

        final Long urlSeq = urlInfoRepository.save(inputUrlInfo).getUrlSeq();
        return Base58Util.encode(urlSeq);
    }

    /**
     * Retrieve the URL pair record from DB using base62-decoded tiny URL as seq,
     * then return the original big URL
     *
     * @param tinyUrl
     * @return
     * @throws UrlNotFoundException
     */
    public String lengthen(final String tinyUrl) throws UrlNotFoundException {
        final UrlInfo urlInfo = urlInfoRepository.findByUrlSeq(Base58Util.decode(tinyUrl));

        if (urlInfo == null) {
            throw new UrlNotFoundException();
        }

        return urlInfo.getUrl();
    }

}
