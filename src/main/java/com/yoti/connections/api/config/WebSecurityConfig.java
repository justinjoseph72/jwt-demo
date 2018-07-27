package com.yoti.connections.api.config;

import com.yoti.connections.api.controllers.LoginController;
import com.yoti.connections.api.security.filter.YotiAuthenticationFilter;
import com.yoti.connections.api.security.filter.YotiJwtFilter;
import com.yoti.connections.api.security.handler.LoginSuccessHandler;
import com.yoti.connections.api.security.handler.YotiConnectionLogoutHandler;
import com.yoti.connections.api.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGOUT = "/logout";

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private YotiJwtFilter yotiJwtFilter;

    @Autowired
    private JwtService jwtService;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(LoginController.LOGIN_PATH).permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .logout().clearAuthentication(true).invalidateHttpSession(true).logoutUrl(LOGOUT).permitAll()
                .addLogoutHandler(getLogoutHandler())
                .logoutSuccessUrl("https://localhost:9000/")
                .and()
                .formLogin().disable()
                .addFilterBefore(getLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getFilter(), BasicAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    private LogoutHandler getLogoutHandler() {
        LogoutHandler handler = new  YotiConnectionLogoutHandler();
        return handler;
    }

    private Filter getLoginFilter() {
        YotiAuthenticationFilter filter = new YotiAuthenticationFilter(manager, LoginController.LOGIN_PATH);
        filter.setAllowSessionCreation(false);
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }

    private AuthenticationFailureHandler failureHandler() {
        final SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("https://localhost:9000/logout");
        return handler;
    }

    private AuthenticationSuccessHandler successHandler() {
        final LoginSuccessHandler handler = new LoginSuccessHandler(jwtService);
        handler.setAlwaysUseDefaultTargetUrl(true);
        handler.setDefaultTargetUrl("https://localhost:9000/");
        return handler;
    }

    private Filter getFilter() {
        return yotiJwtFilter;
    }
}
