package com.yoti.connections.api.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JwtServiceTest {

    @Autowired
    private JwtService service;

    @Test
    public void shouldGenreteJwt() {
        String jwt = service.createJwtString(BigInteger.ONE);
        log.info(jwt);
    }

    @Test
    public void verfiY() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VJZCI6IjEiLCJpc3MiOiJ5b3RpIiwiZXhwIjoxNTMyNjIwNTA3fQ.MbFYz22-Q2B50GNsNA1q8KIk_YRREGvoufs-RjMyqcI";
        String jjj = service.verifyToken(token);
        log.info(jjj);
    }

    @Test
    public void decodeToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VJZCI6IjEiLCJpc3MiOiJ5b3RpIiwiZXhwIjoxNTMyNjIwNTA3fQ.MbFYz22-Q2B50GNsNA1q8KIk_YRREGvoufs-RjMyqcI";
        String jd = service.decodeToken(token);
        log.info(jd);
    }
}
