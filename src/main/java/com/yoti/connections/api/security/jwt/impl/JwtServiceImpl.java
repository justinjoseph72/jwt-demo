package com.yoti.connections.api.security.jwt.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.yoti.connections.api.config.JwtConfigValues;
import com.yoti.connections.api.security.jwt.JwtService;
import com.yoti.connections.api.security.jwt.exception.JwtProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtConfigValues configValues;

    private final Clock clock;

    private final static String USER_ID_CLAIM = "userId";

    private final static String ISSUER_NAME = "yoti";

    @Override
    public String createJwtString(final BigInteger userId) {
        try {
            Algorithm algorithmHS = Algorithm.HMAC256(configValues.getSecret());
            int timeoutInMinutes = configValues.getTimeout();
            Date issuedAt = Date.from(clock.instant());
            Instant instant = clock.instant().plus(timeoutInMinutes, ChronoUnit.MINUTES);
            Date expriyDate = Date.from(instant);
            String token = JWT.create().withIssuer(ISSUER_NAME)
                    .withClaim(USER_ID_CLAIM, userId.toString())
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
            Algorithm algorithm = Algorithm.HMAC256(configValues.getSecret());
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER_NAME)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Claim claim = jwt.getClaim(USER_ID_CLAIM);
            return claim.asString();
        } catch (Exception e) {
            throw new JwtProcessingException(e.getMessage());
        }
    }

    @Override
    public String decodeToken(final String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Claim claim = jwt.getClaim(USER_ID_CLAIM);
            return claim.asString();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
