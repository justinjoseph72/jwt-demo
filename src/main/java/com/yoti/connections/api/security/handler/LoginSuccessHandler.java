package com.yoti.connections.api.security.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.addHeader("Set-Cookie", "jwt-new=Sdfsdsdfsff");
        String targetUrl = this.getDefaultTargetUrl();
        String targetUrlParameter = this.getTargetUrlParameter();
        this.clearAuthenticationAttributes(request);
//            String targetUrl = savedRequest.getRedirectUrl();
//            this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

}
