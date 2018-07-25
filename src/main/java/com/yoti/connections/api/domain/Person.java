package com.yoti.connections.api.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

@Builder
@Value
public class Person {

    private BigInteger id;
    private String givenName;
    private String familyName;
    private String email;

    @JsonCreator
    public  Person(
             BigInteger id,
             String givenName,
             String familyName,
             String email
    ){
      this.id = id;
      this.givenName = givenName;
      this.familyName = familyName;
      this.email = email;
    }
}
