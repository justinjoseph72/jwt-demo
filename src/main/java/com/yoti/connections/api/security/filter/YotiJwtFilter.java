package com.yoti.connections.api.security.filter;

import com.yoti.connections.api.data.DataAccess;
import com.yoti.connections.api.domain.Person;
import com.yoti.connections.api.security.model.YotiPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class YotiJwtFilter extends OncePerRequestFilter {

    private final String AUTH_HEADER = "auth_header";
    private final DataAccess dataAccess;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain)
            throws ServletException, IOException {
        log.info("{} ",httpServletRequest.getRequestURI());
        String jwtToken = getToken(httpServletRequest);
        if(!StringUtils.isEmpty(jwtToken) ){
            log.info("the jwt token  is {}",jwtToken);
            Person person = dataAccess.findPersonById(BigInteger.ONE);
            YotiPrincipal principal = new YotiPrincipal(person,Collections.emptySet());
//            SecurityContextHolder.getContext().setAuthentication(principal);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            log.info("sdff asf");
         //   SecurityContextHolder.clearContext();
            SecurityContextHolder.getContext().setAuthentication(principal);
        }
        else {
            throw new AccessDeniedException("invalid access token");
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private String getToken(final HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader(AUTH_HEADER);
        return authHeader;
    }

}
