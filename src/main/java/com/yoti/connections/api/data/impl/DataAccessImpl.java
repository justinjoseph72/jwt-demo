package com.yoti.connections.api.data.impl;

import com.yoti.connections.api.data.DataAccess;
import com.yoti.connections.api.domain.Person;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DataAccessImpl implements DataAccess {

    private final AtomicInteger index = new AtomicInteger(1);

    private Map<BigInteger,Person> personMap = new HashMap<>();

    @Override
    public Person savePerson(final Person person) {
        Person newPerson = Person.builder()
                .id(BigInteger.valueOf(index.getAndIncrement()))
                .givenName(person.getGivenName())
                .familyName(person.getFamilyName())
                .email(person.getEmail())
                .build();
        personMap.put(newPerson.getId(),newPerson);
        return newPerson;
    }

    @Override
    public Person findPersonById(final BigInteger id) {
        return personMap.get(id);
    }
}
