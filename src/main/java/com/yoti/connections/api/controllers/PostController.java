package com.yoti.connections.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PostController {

    @PostMapping(value = "/add")
    public String saveItem() {
        log.info("inside post");
        return "OK";
    }
}
