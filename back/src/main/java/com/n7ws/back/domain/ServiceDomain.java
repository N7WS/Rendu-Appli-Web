package com.n7ws.back.domain;

import java.util.Collection;

public record ServiceDomain(
    String uid,
    String name,
    Integer port,
    Collection<ScriptDomain> scripts
) {}
