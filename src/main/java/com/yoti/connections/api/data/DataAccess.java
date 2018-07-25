package com.yoti.connections.api.data;

import com.yoti.connections.api.domain.Person;

import java.math.BigInteger;

public interface DataAccess {

    Person savePerson(Person person);

    Person findPersonById(BigInteger id);

}
