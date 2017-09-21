package com.gaimuk.shortroad.service;

import com.gaimuk.shortroad.common.pojo.UrlPairNotFoundException;
import com.gaimuk.shortroad.data.mongodb.entity.UrlPair;
import com.gaimuk.shortroad.data.mongodb.repository.UrlPairRepository;
import com.gaimuk.shortroad.common.util.Base62Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    private UrlPairRepository urlPairRepository;

    /**
     * Persists the big URL and use the base62-encoded database ID as tiny URL
     *
     * @param bigUrl
     * @return
     */
    public String shorten(final String bigUrl) {
        UrlPair inputUrlPair = new UrlPair();
        inputUrlPair.setBigUrl(bigUrl);

        final UrlPair savedUrlPair = urlPairRepository.save(inputUrlPair);

        return Base62Util.encode(savedUrlPair.getId());
    }


    /**
     * Retrieve the URL pair record from DB using base62-decoded tiny URL as database ID,
     * then return the original big URL
     *
     * @param tinyUrl
     * @return Big URL paired with input tinyUrl
     */
    public String lengthen(final String tinyUrl) throws UrlPairNotFoundException {
        final UrlPair urlPair = urlPairRepository.findOne(Base62Util.decode(tinyUrl));

        if (urlPair == null) {
            throw new UrlPairNotFoundException();
        }

        return urlPair.getBigUrl();
    }

}
