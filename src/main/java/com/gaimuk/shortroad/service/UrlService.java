package com.gaimuk.shortroad.service;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.common.util.Base58Util;
import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;
import com.gaimuk.shortroad.data.mongodb.repository.UrlInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    private UrlInfoRepository urlInfoRepository;

    @Autowired
    private Base58Util base58Util;

    /**
     * Persists the long URL and return the base62-encoded seq number
     *
     * @param longUrl
     * @return
     */
    public String shorten(final String longUrl) {
        UrlInfo inputUrlInfo = new UrlInfo();
        inputUrlInfo.setUrl(longUrl);

        final Long savedUrlSeq = urlInfoRepository.saveAndGenerateSeq(inputUrlInfo).getUrlSeq();
        return base58Util.encode(savedUrlSeq);
    }

    /**
     * Retrieve the URLInfo record from DB using base62-decoded short URL token as seq num,
     * then return the original long URL
     *
     * @param shortUrlToken
     * @return
     * @throws UrlNotFoundException
     */
    public String lengthen(final String shortUrlToken) throws UrlNotFoundException {
        final UrlInfo urlInfo = urlInfoRepository.findByUrlSeq(base58Util.decode(shortUrlToken));

        if (urlInfo == null) {
            throw new UrlNotFoundException();
        }

        return urlInfo.getUrl();
    }

}
