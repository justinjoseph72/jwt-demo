package com.yoti.connections.api.security.model;

import com.yoti.connections.api.domain.Person;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.math.BigInteger;
import java.util.Collection;

public class YotiPrincipal extends AbstractAuthenticationToken {

    private BigInteger id;
    private String email;
    private String givneNmae;
    private String familyName;

    public YotiPrincipal(final Person person, final Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.id = person.getId();
        this.email = person.getEmail();
        this.givneNmae = person.getGivenName();
        this.familyName = person.getFamilyName();
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return id;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }

    public BigInteger getIdAsBigInteger(){
        return  id;
    }
}
