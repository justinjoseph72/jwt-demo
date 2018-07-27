package com.yoti.connections.api.security.jwt.exception;

public class JwtProcessingException extends RuntimeException {

    public  JwtProcessingException(String message){
        super(message);
    }

}
