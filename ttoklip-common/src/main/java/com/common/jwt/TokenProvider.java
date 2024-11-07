package com.common.jwt;

public interface TokenProvider {
    String create(String email);

    boolean validate(String token);

    String extract(String token);
}
