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
     * Persists the long URL and return the base62-encoded seq number
     *
     * @param longUrl
     * @return
     */
    public String shorten(final String longUrl) {
        // Construct UrlInfo with the generated shortUrl ID
        UrlInfo inputUrlInfo = new UrlInfo();
        inputUrlInfo.setUrl(longUrl);
        inputUrlInfo.setUrlSeq(urlSeqRepository.next());

        final Long urlSeq = urlInfoRepository.save(inputUrlInfo).getUrlSeq();
        return Base58Util.encode(urlSeq);
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
        final UrlInfo urlInfo = urlInfoRepository.findByUrlSeq(Base58Util.decode(shortUrlToken));

        if (urlInfo == null) {
            throw new UrlNotFoundException();
        }

        return urlInfo.getUrl();
    }

}
