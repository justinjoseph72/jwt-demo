package com.yoti.connections.api.controllers;

import com.yoti.connections.api.security.model.YotiPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FetchItemController {

    private static final String FETCH_ENDPOINT = "/person";

    @Value("${justin.key}")
    private String key;

    @GetMapping(value = FETCH_ENDPOINT,produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPerson(@AuthenticationPrincipal YotiPrincipal principal){
        log.info("logged in user is {}",principal.getIdAsBigInteger());
        log.info("the key is {}",key);
        return "hjk";
    }
}
