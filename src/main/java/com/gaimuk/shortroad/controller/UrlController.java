package com.gaimuk.shortroad.controller;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.controller.request.CreateShortUrlRequest;
import com.gaimuk.shortroad.controller.response.CreateShortUrlResponse;
import com.gaimuk.shortroad.controller.response.GetLongUrlResponse;
import com.gaimuk.shortroad.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.io.IOException;

@RestController
@Validated
public class UrlController {

    @Autowired
    private UrlService urlService;

    @RequestMapping(method = RequestMethod.POST, path = "/shortUrl")
    public CreateShortUrlResponse createShortUrl(@RequestBody @Valid CreateShortUrlRequest request) {
        final String shortUrlToken = urlService.shorten(request.getUrl());
        final String redirectUrl = ControllerLinkBuilder.linkTo(UrlController.class).toUri().toString() +
                "/" + shortUrlToken;
        return new CreateShortUrlResponse(request.getUrl(), redirectUrl, shortUrlToken);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/longUrl/{shortUrlToken}")
    public GetLongUrlResponse getLongUrl(@PathVariable @Size(min = 1, max = 10) final String shortUrlToken)
            throws UrlNotFoundException {
        return new GetLongUrlResponse(urlService.lengthen(shortUrlToken));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{shortUrlToken}")
    public void redirectToLongUrl(HttpServletResponse response, @PathVariable @Size(min = 1, max = 10) final String shortUrlToken)
            throws UrlNotFoundException, IOException {
        response.sendRedirect(urlService.lengthen(shortUrlToken));
    }
}
