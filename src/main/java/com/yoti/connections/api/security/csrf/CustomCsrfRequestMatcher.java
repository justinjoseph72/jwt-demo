package com.yoti.connections.api.security.csrf;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomCsrfRequestMatcher implements RequestMatcher {

    @Override
    public boolean matches(final HttpServletRequest httpServletRequest) {
        return true;
    }
}
