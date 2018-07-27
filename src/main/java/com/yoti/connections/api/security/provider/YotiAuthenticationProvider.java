package com.yoti.connections.api.security.provider;

import com.yoti.connections.api.data.DataAccess;
import com.yoti.connections.api.domain.Person;
import com.yoti.connections.api.security.model.YotiAuthenticationToken;
import com.yoti.connections.api.security.model.YotiPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@RequiredArgsConstructor
@Slf4j
public class YotiAuthenticationProvider implements AuthenticationProvider {

    private final DataAccess access;
    private final AtomicInteger index = new AtomicInteger(1);

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        if(authentication instanceof YotiAuthenticationToken ){
         log.info("processing autheication token");
         int a = index.getAndIncrement();
            Person loginPerson = Person.builder()
                    .email("email"+a)
                    .givenName("givenname"+a)
                    .familyName("familyName"+a)
                    .build();
            loginPerson = access.savePerson(loginPerson);
            return new YotiPrincipal(loginPerson,Collections.emptySet());
        }
        else {
            throw new IllegalArgumentException("invalid token");
        }
    }

    @Override
    public boolean supports(final Class<?> aClass) {
      return YotiAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
