package com.n7ws.back.domain;

import java.time.Instant;

public record HealthReportDomain(
    String deviceName,
    Instant timestamp,
    Integer ping,
    Integer ramUsage,
    Integer cpuUsage
) {}
