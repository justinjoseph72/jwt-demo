package com.yoti.connections.api.security.filter;

import com.yoti.connections.api.security.model.YotiAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class YotiAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private static final String TOKEN_PARAM_NAME = "token";

    public YotiAuthenticationFilter(AuthenticationManager authenticationManager, String authenticationPath){
        super(new AntPathRequestMatcher(authenticationPath));
        this.authenticationManager= authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
       log.info("starting to filter the login process");
       String token = obtainToken(httpServletRequest);
       final YotiAuthenticationToken authenticationToken = new YotiAuthenticationToken(token);
       setDetails(httpServletRequest,authenticationToken);
       return authenticationManager.authenticate(authenticationToken);
    }

    private void setDetails(final HttpServletRequest httpServletRequest, final YotiAuthenticationToken authenticationToken) {
        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(httpServletRequest));
    }

    private String obtainToken(final HttpServletRequest httpServletRequest){
        return httpServletRequest.getParameter(TOKEN_PARAM_NAME);
    }
}
