package com.yoti.connections.api.security.handler;

import com.yoti.connections.api.security.jwt.JwtService;
import com.yoti.connections.api.security.model.YotiPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final static String JWT_COKKIE_NAME = "login_token";

    public LoginSuccessHandler(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        BigInteger userId = (BigInteger)authentication.getPrincipal();
        log.info("the principal is {}",userId);
        String jwtToken = getJwtTokenForUserId(userId);
        String cookieStr = JWT_COKKIE_NAME + "=" + jwtToken;
        response.addHeader("Set-Cookie", cookieStr);
        String targetUrl = getDefaultTargetUrl();
        this.clearAuthenticationAttributes(request);
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


    private String getJwtTokenForUserId(final BigInteger userId){
        return jwtService.createJwtString(userId);
    }
}
