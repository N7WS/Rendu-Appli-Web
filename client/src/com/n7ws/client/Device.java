package com.n7ws.client;

public record Device(
    String name,
    DeviceState status,
    String room,
    String cpuName,
    Integer cpuCores,
    Integer cpuFreq, // MHz
    Integer ramSize, // Mo
    Integer ramFreq // MHz
) {}
