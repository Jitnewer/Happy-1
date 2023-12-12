package com.example.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class APIConfig {

    public List<String> SECURED_PATHS = List.of("admin");

    @Value("${jwt.pass-phrase}")
    private String jwtPassphrase;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    @Value("${jwt.duration-of-validity}")
    private int jwtDurationOfValidity;

    // Getters for the properties

    public String getJwtPassphrase() {
        return jwtPassphrase;
    }

    public String getJwtIssuer() {
        return jwtIssuer;
    }

    public int getJwtDurationOfValidity() {
        return jwtDurationOfValidity;
    }
}
