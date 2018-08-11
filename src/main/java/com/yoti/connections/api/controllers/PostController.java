package com.yoti.connections.api.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class PostController {

    @PostMapping(value = "/add")
    public String saveItem() {
        log.info("inside post");
        return "OK";
    }

    @GetMapping(value = "display")
    public String displayItems(@RequestParam("item") String itemName) {
        log.info("itemName is {}",itemName);
        return "done";
    }
}
