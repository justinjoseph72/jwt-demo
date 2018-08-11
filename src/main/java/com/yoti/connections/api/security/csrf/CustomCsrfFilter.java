package com.yoti.connections.api.security.csrf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

@Component
@Slf4j
public class CustomCsrfFilter extends OncePerRequestFilter {

    private final String HEADER_NAME = "custom_csrf";

    private final AccessDeniedHandler accessDeniedHandler = new AccessDeniedHandlerImpl();

    public static final RequestMatcher DEFAULT_CSRF_MATCHER = new CustomCsrfFilter.DefaultRequiresCsrfMatcher();

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        request.setAttribute(HttpServletResponse.class.getName(), response);
        if ( !this.DEFAULT_CSRF_MATCHER.matches(request) ) {
            filterChain.doFilter(request, response);
        } else {
            String actualToken = request.getHeader(HEADER_NAME);
            if ( StringUtils.isEmpty(actualToken) ) {
                log.warn("not custom csrf token present ");
                accessDeniedHandler.handle(request, response, new MissingCsrfTokenException(actualToken));
            }
            log.info("continuing from the filter chain");
            filterChain.doFilter(request, response);
        }
    }

    private static final class DefaultRequiresCsrfMatcher implements RequestMatcher {

        private final HashSet<String> allowedMethods;

        private DefaultRequiresCsrfMatcher() {
            this.allowedMethods = new HashSet(Arrays.asList("GET", "HEAD", "TRACE", "OPTIONS"));
        }

        public boolean matches(HttpServletRequest request) {
            return !this.allowedMethods.contains(request.getMethod());
        }
    }
}
