package com.n7ws.back.model;

public record UserModel(
    String email,
    String lastname,
    String firstname,
    String password,
    boolean admin
) {}
