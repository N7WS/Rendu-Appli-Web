package com.n7ws.back.model;

import java.time.Instant;

public record HealthReportModel(
    String deviceName,
    Instant timestamp,
    Integer ping,
    Integer ramUsage,
    Integer cpuUsage
) {}
