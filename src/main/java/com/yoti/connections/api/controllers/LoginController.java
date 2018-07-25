package com.yoti.connections.api.controllers;

import com.yoti.connections.api.data.DataAccess;
import com.yoti.connections.api.domain.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

//@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    public static final String LOGIN_PATH = "/login";

    private final DataAccess dataAccess;
    private final AtomicInteger index = new AtomicInteger(0);


    //@GetMapping(value = LOGIN_PATH)
    private ResponseEntity<String> loginMethod(@RequestParam("token") String token) {
        log.info("login with token {}",token);
        int a = index.getAndIncrement();
        Person loginPerson = Person.builder()
                .email("email"+a)
                .givenName("givenname"+a)
                .familyName("familyName"+a)
                .build();
        dataAccess.savePerson(loginPerson);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Set-Cookie","jwt=Sdfsdf");
        ResponseEntity<String> response =new ResponseEntity<>("sdf",headers,HttpStatus.OK);
        return response;
    }
}
