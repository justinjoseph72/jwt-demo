package com.yoti.connections.api.security.jwt;

import java.math.BigInteger;

public interface JwtService {

    String createJwtString(BigInteger userId);

    String verifyToken(String token);

    String decodeToken(String token);

}
