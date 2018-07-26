package com.yoti.connections.api.security.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yoti.connections.api.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${yoti.jwt.key}")
    private String secret;

    @Autowired
    private Clock clock;

    @Override
    public String createJwtString(final BigInteger userId) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(secret);
            Instant instant = clock.instant().plus(1, ChronoUnit.MINUTES);
            Date expriyDate = Date.from(instant);
            String token = JWT.create().withIssuer("yoti")
                    .withClaim("useId",userId.toString())
                    .withExpiresAt(expriyDate)
                    .sign(algorithmHS);
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String verifyToken(final String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("yoti")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getPayload();

        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String decodeToken(final String token) {
        try{
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim("useId");
            return claim.asString();
        }catch (JWTDecodeException e){
            e.printStackTrace();
        }
        return null;
    }
}
