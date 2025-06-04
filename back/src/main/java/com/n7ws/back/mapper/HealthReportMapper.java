package com.n7ws.back.mapper;

import com.n7ws.back.domain.HealthReportDomain;
import com.n7ws.back.entity.HealthReportEntity;
import com.n7ws.back.model.HealthReportModel;

public interface HealthReportMapper extends Mapper {
    static HealthReportDomain toDomain(HealthReportEntity healthReportEntity) {
        return new HealthReportDomain(
            healthReportEntity.getDeviceName(),
            healthReportEntity.getTimestamp(),
            healthReportEntity.getPing(),
            healthReportEntity.getRamUsage(),
            healthReportEntity.getCpuUsage()
        );
    }

    static HealthReportModel toModel(HealthReportDomain healthReportDomain) {
        return new HealthReportModel(
            healthReportDomain.deviceName(),
            healthReportDomain.timestamp(),
            healthReportDomain.ping(),
            healthReportDomain.ramUsage(),
            healthReportDomain.cpuUsage()
        );
    }

    static HealthReportEntity toEntity(HealthReportModel healthReportModel) {
        return new HealthReportEntity(
            healthReportModel.deviceName(),
            healthReportModel.timestamp(),
            healthReportModel.ping(),
            healthReportModel.ramUsage(),
            healthReportModel.cpuUsage()
        );
    }
}
