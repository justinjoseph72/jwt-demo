package com.yoti.connections.api.security.filter;

import com.yoti.connections.api.data.DataAccess;
import com.yoti.connections.api.domain.Person;
import com.yoti.connections.api.security.jwt.LoginJwtTokenService;
import com.yoti.connections.api.security.jwt.exception.JwtProcessingException;
import com.yoti.connections.api.security.model.YotiPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final String AUTH_HEADER = "auth_token";

    private final DataAccess dataAccess;

    private final LoginJwtTokenService loginJwtTokenService;

    @Override
    protected void doFilterInternal(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final FilterChain filterChain)
            throws ServletException, IOException {
        log.info("{} ", httpServletRequest.getRequestURI());
        String jwtToken = getToken(httpServletRequest);
        try {
            BigInteger userId = validateAndFetchUserIdFromToken(jwtToken);
            if(userId!=null){
                log.info("the jwt token  is {}", jwtToken);
                Person person = dataAccess.findPersonById(userId);
                YotiPrincipal principal = new YotiPrincipal(person, Collections.emptySet());
                log.info("the user accesss the resource is {}", person.getId());
                SecurityContextHolder.getContext().setAuthentication(principal);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }
            else {
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } catch (JwtProcessingException e) {
            log.info("the jwt process failed dut to {}",e.getMessage());
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }

    private BigInteger validateAndFetchUserIdFromToken(final String jwtToken) {
        if ( !StringUtils.isEmpty(jwtToken) ) {
            String userIdStr = loginJwtTokenService.verifyToken(jwtToken);
            return new BigInteger(userIdStr);
        } else {
            return null;
        }
    }

    private String getToken(final HttpServletRequest httpServletRequest) {
        String authHeader = httpServletRequest.getHeader(AUTH_HEADER);
        return authHeader;
    }

}
