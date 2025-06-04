package com.n7ws.back.model;

import java.util.Collection;

public record ServiceModel(
    String uid,
    String name,
    Integer port,
    Collection<ScriptModel> scripts
) {}
