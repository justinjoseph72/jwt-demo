package com.yoti.connections.api.security.handler;

import com.yoti.connections.api.security.jwt.CsrfJwtTokenService;
import com.yoti.connections.api.security.jwt.LoginJwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;

@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final LoginJwtTokenService loginJwtTokenService;

    private final CsrfJwtTokenService csrfJwtTokenService;

    private final static String JWT_COKKIE_NAME = "login_token";

    private final static String CSRF_HEADER_NAME = "yc_csrf_token";

    public LoginSuccessHandler(LoginJwtTokenService loginJwtTokenService,CsrfJwtTokenService csrfJwtTokenService) {
        this.loginJwtTokenService = loginJwtTokenService;
        this.csrfJwtTokenService = csrfJwtTokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        BigInteger userId = (BigInteger) authentication.getPrincipal();
        log.info("the principal is {}", userId);
        String jwtToken = getJwtTokenForUserId(userId);
        Cookie cookie = new Cookie(JWT_COKKIE_NAME, jwtToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        response.addCookie(cookie);
        addCsrfTokenToResponse(response,userId);
        String targetUrl = getDefaultTargetUrl();
        this.clearAuthenticationAttributes(request);
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private void addCsrfTokenToResponse(final HttpServletResponse response,final BigInteger userId) {
        String csrfToken = csrfJwtTokenService.createToken(userId);
        response.addHeader(CSRF_HEADER_NAME,csrfToken);
    }

    private String getJwtTokenForUserId(final BigInteger userId) {
        return loginJwtTokenService.createJwtString(userId);
    }
}
