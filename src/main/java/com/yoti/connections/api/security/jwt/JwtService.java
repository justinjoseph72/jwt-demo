package com.yoti.connections.api.security.jwt;

import com.yoti.connections.api.security.jwt.exception.JwtProcessingException;

import java.math.BigInteger;

public interface JwtService {

    String createJwtString(BigInteger userId);

    String verifyToken(String token) throws JwtProcessingException;

    String decodeToken(String token);

}
