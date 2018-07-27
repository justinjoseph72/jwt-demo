package com.yoti.connections.api.security.jwt;

import org.springframework.security.access.AccessDeniedException;

import java.math.BigInteger;

public interface JwtService {

    String createJwtString(BigInteger userId);

    String verifyToken(String token) throws AccessDeniedException;

    String decodeToken(String token);

}
