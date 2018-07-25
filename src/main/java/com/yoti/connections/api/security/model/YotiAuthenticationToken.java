package com.yoti.connections.api.security.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class YotiAuthenticationToken extends AbstractAuthenticationToken {
    private String connectToken;

    private String UNKNOWN = "UNKNOWN";

    public YotiAuthenticationToken(String connectToken){
        super(null);
        this.connectToken=connectToken;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return connectToken;
    }

    @Override
    public Object getPrincipal() {
        return UNKNOWN;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted.");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials(){
        super.eraseCredentials();
        connectToken = null;
    }
}
