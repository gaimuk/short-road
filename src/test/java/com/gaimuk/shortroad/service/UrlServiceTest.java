package com.gaimuk.shortroad.service;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.common.util.Base58Util;
import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;
import com.gaimuk.shortroad.data.mongodb.repository.UrlInfoRepository;
import com.gaimuk.shortroad.data.mongodb.repository.UrlSeqRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ThreadLocalRandom;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
public class UrlServiceTest {

    @InjectMocks
    private UrlService urlService;

    @Mock
    private UrlInfoRepository urlInfoRepository;

    @Mock
    private UrlSeqRepository urlSeqRepository;

    @Test
    public void testShortenNormal() {
        final String inputUrl = "http://www.google.com?t=33984&r=a%20b";

        final long generatedUrlSeq = Long.MAX_VALUE;
        when(urlSeqRepository.next()).thenReturn(generatedUrlSeq);

        final UrlInfo savedUrlInfo = buildUrlInfo(inputUrl, generatedUrlSeq);
        when(urlInfoRepository.save(any(UrlInfo.class))).thenReturn(savedUrlInfo);

        assertThat(urlService.shorten(inputUrl))
                .as("Output short URL token is base58-encoded generated seq num")
                .isEqualTo(Base58Util.encode(generatedUrlSeq));
    }

    @Test
    public void testLengthenNormal() throws UrlNotFoundException {
        UrlInfo retrievedUrlInfo = buildUrlInfo("http://www.google.com?t=33984&r=a%20b", Long.MAX_VALUE);
        when(urlInfoRepository.findByUrlSeq(anyLong())).thenReturn(retrievedUrlInfo);

        final String inputShortUrlToken = Base58Util.encode(Long.MAX_VALUE);
        assertThat(urlService.lengthen(inputShortUrlToken))
                .as("Output long URL is retrieved from DB with Base58 decoded short URL token")
                .isEqualTo(retrievedUrlInfo.getUrl());
    }

    @Test
    public void testLengthenUrlNotFound() throws UrlNotFoundException {
        final String inputShortUrlToken = Base58Util.encode(Long.MAX_VALUE);
        when(urlInfoRepository.findByUrlSeq(anyLong())).thenReturn(null);

        assertThatExceptionOfType(UrlNotFoundException.class)
                .isThrownBy(() -> urlService.lengthen(inputShortUrlToken))
                .as("If no URLInfo is found an URLNotFouncException should be thrown")
                .withMessage(null);
    }

    private UrlInfo buildUrlInfo(String url, long seq) {
        UrlInfo urlInfo = new UrlInfo();
        urlInfo.setId(String.valueOf(ThreadLocalRandom.current().nextLong()));
        urlInfo.setUrl(url);
        urlInfo.setUrlSeq(seq);

        return urlInfo;
    }
}
