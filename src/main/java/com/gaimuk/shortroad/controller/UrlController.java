package com.gaimuk.shortroad.controller;

import com.gaimuk.shortroad.common.pojo.UrlPairNotFoundException;
import com.gaimuk.shortroad.controller.request.CreateTinyUrlRequest;
import com.gaimuk.shortroad.controller.response.CreateTinyUrlResponse;
import com.gaimuk.shortroad.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UrlController {

    @Autowired
    private UrlService urlService;

    @RequestMapping(method = RequestMethod.POST, path = "/tinyUrl")
    public CreateTinyUrlResponse createTinyUrl(@RequestBody @Valid CreateTinyUrlRequest request) {
        CreateTinyUrlResponse response = new CreateTinyUrlResponse();
        response.setUrl(urlService.shorten(request.getUrl()));

        return response;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{tinyUrl}")
    public void getBigUrl(HttpServletResponse response,
                          @PathVariable final String tinyUrl) throws UrlPairNotFoundException, IOException {
        response.sendRedirect(urlService.lengthen(tinyUrl));
    }
}
