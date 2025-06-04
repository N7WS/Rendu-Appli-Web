package com.n7ws.back.entity;

import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity
@NoArgsConstructor
// @Table(name = "HealthReports")
@AllArgsConstructor
@IdClass(HealthReportEntity.HealthReportId.class)
public class HealthReportEntity implements com.n7ws.back.entity.Entity {
    @Id
    @NonNull
    private String deviceName;
    @Id
    @NonNull
    private Instant timestamp;
    @NonNull
    private Integer ping;
    @NonNull
    private Integer ramUsage;
    @NonNull
    private Integer cpuUsage;

    public String getUid() {
        return new HealthReportId(deviceName, timestamp).toString();
    }

    public record HealthReportId(String deviceName, Instant timestamp) implements Serializable {}
}