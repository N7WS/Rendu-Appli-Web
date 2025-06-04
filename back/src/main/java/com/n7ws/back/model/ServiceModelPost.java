package com.n7ws.back.model;

import java.util.Collection;

public record ServiceModelPost(
    String uid,
    String name,
    Integer port,
    Collection<String> scriptsId
) {}

