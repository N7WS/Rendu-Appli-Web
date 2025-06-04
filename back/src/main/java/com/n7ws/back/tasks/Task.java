package com.n7ws.back.tasks;

public record Task (
    String name,
    String script_path // TODO: changer par un vrai script ?
) {}
