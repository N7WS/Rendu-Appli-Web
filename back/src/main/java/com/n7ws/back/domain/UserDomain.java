package com.n7ws.back.domain;

public record UserDomain(
    String email,
    String lastname,
    String firstname,
    String password,
    boolean admin
) {}
