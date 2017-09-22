package com.gaimuk.shortroad.controller;

import com.gaimuk.shortroad.common.exception.UrlNotFoundException;
import com.gaimuk.shortroad.controller.request.CreateTinyUrlRequest;
import com.gaimuk.shortroad.controller.response.CreateTinyUrlResponse;
import com.gaimuk.shortroad.controller.response.GetBigUrlResponse;
import com.gaimuk.shortroad.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @RequestMapping(method = RequestMethod.POST, path = "/tinyUrl")
    public CreateTinyUrlResponse createTinyUrl(@RequestBody @Valid CreateTinyUrlRequest request) throws IOException, UrlNotFoundException {

        final String tinyUrlToken = urlService.shorten(request.getUrl());
        final String tinyUrl = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UrlController.class).getBigUrl(tinyUrlToken)).toString();

        return new CreateTinyUrlResponse(request.getUrl(), tinyUrl, tinyUrlToken);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/bigUrl/{tinyUrlToken}")
    public GetBigUrlResponse getBigUrl(@PathVariable final String tinyUrlToken) throws UrlNotFoundException, IOException {

        return new GetBigUrlResponse(urlService.lengthen(tinyUrlToken));
    }
}
