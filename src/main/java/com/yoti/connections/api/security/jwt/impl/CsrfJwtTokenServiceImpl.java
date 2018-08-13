package com.yoti.connections.api.security.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yoti.connections.api.security.jwt.CsrfJwtTokenService;
import com.yoti.connections.api.security.jwt.exception.JwtProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CsrfJwtTokenServiceImpl implements CsrfJwtTokenService {

    private final String SECRET = "sdfsdf#w34";

    private final String CLAIM_NAME = "token";

    private final Clock clock;

    private final static String ISSUER_NAME = "yoti_csrf";

    @Override
    public String createToken(final BigInteger userId) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(SECRET);
            Date issuedAt = Date.from(clock.instant());
            Instant instant = clock.instant().plus(1, ChronoUnit.MINUTES);
            Date expriyDate = Date.from(instant);
            String token = JWT.create().withIssuer(ISSUER_NAME)
                    .withClaim(CLAIM_NAME, getClaimValue(userId))
                    .withExpiresAt(expriyDate)
                    .withIssuedAt(issuedAt)
                    .sign(algorithmHS);
            return token;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String verifyToken(final String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER_NAME)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Claim claim = jwt.getClaim(CLAIM_NAME);
            return claim.asString();
        } catch (Exception e) {
            throw new JwtProcessingException(e.getMessage());
        }
    }

    private String getClaimValue(final BigInteger userId) {
        return UUID.randomUUID().toString();
    }
}
