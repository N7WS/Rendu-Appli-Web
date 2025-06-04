package com.n7ws.client;

import java.time.Instant;

public record HealthInfo(
    String deviceName,
    Instant timestamp,
    Integer ping,
    Integer ramUsage,
    Integer cpuUsage
) {}
