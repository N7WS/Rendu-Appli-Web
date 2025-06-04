package com.n7ws.back.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.n7ws.back.entity.HealthReportEntity;
import com.n7ws.back.entity.HealthReportEntity.HealthReportId;

public interface HealthReportRepository extends JpaRepository<HealthReportEntity, HealthReportId> {
    // Find all by device name (half of the composite key)
    Collection<HealthReportEntity> findAllByDeviceName(String deviceName);
}
