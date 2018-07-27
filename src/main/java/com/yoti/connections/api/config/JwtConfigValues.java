package com.yoti.connections.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "yoti.jwt")
@Data
@Component
public class JwtConfigValues {

    private String secret;
    private int timeout;
}
