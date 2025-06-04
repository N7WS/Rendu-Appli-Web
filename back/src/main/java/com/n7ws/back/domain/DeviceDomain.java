package com.n7ws.back.domain;

import java.util.Collection;

import com.n7ws.back.enums.DeviceState;

public record DeviceDomain(
    String name,
    DeviceState status,
    String room,
    String cpuName,
    Integer cpuCores,
    Integer cpuFreq, // MHz
    Integer ramSize, // Mo
    Integer ramFreq, // MHz
    Collection<ServiceDomain> services
) {}
