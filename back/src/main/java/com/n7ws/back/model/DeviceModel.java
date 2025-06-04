package com.n7ws.back.model;

import java.util.Collection;

import com.n7ws.back.enums.DeviceState;

public record DeviceModel(
    String name,
    DeviceState status,
    String room,
    String cpuName,
    Integer cpuCores,
    Integer cpuFreq, // MHz
    Integer ramSize, // Mo
    Integer ramFreq, // MHz
    Collection<ServiceModel> services
) {}
