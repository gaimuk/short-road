package com.gaimuk.shortroad.service;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.common.util.Base58Util;
import com.gaimuk.shortroad.data.mongodb.document.UrlInfo;
import com.gaimuk.shortroad.data.mongodb.repository.UrlInfoRepository;
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
    private Base58Util base58Util;

    @Test
    public void testShortenNormal() {
        final String inputUrl = "http://www.google.com";
        final UrlInfo savedUrlInfo = buildUrlInfo(inputUrl, Long.MAX_VALUE);
        final String encodedUrlSeq = "abcde";

        when(urlInfoRepository.saveAndGenerateSeq(any(UrlInfo.class))).thenReturn(savedUrlInfo);
        when(base58Util.encode(savedUrlInfo.getUrlSeq())).thenReturn(encodedUrlSeq);

        // Verify the output short URL token match
        assertThat(urlService.shorten(inputUrl))
                .as("Output short URL token is base58-encoded generated seq num")
                .isEqualTo(encodedUrlSeq);
    }

    @Test
    public void testLengthenNormal() throws UrlNotFoundException {
        final String inputShortUrlToken = "abcde";
        UrlInfo retrievedUrlInfo = buildUrlInfo("http://www.google.com?t=33984&r=a%20b", Long.MAX_VALUE);

        when(base58Util.decode(inputShortUrlToken)).thenReturn(Long.MAX_VALUE);
        when(urlInfoRepository.findByUrlSeq(anyLong())).thenReturn(retrievedUrlInfo);

        assertThat(urlService.lengthen(inputShortUrlToken))
                .as("Output long URL is retrieved from DB by using base58-decoded short URL token")
                .isEqualTo(retrievedUrlInfo.getUrl());
    }

    @Test
    public void testLengthenUrlNotFound() throws UrlNotFoundException {
        final String inputShortUrlToken = "abcde";

        when(base58Util.decode(inputShortUrlToken)).thenReturn(Long.MAX_VALUE);
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
