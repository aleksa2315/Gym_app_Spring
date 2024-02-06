package org.example.security.service;

import io.jsonwebtoken.Claims;

public interface TokenService {

    String generate(Claims claims);

    Claims parseToken(String jwt);

    public Long parseId(String jwt);
    public String parseRole(String jwt);
}
