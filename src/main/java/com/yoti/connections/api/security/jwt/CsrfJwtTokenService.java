package com.yoti.connections.api.security.jwt;

import java.math.BigInteger;

public interface CsrfJwtTokenService {

    String createToken(BigInteger userId);

    String verifyToken(String token);
}
