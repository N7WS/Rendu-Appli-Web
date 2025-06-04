package com.n7ws.back.entity;

import java.util.Collection;

import com.n7ws.back.enums.DeviceState;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
// @Table(name = "Devices")
public class DeviceEntity implements com.n7ws.back.entity.Entity {
    /**< Device */
    @Id
    @NonNull
    private String name;
    @NonNull
    private DeviceState status;
    @NonNull
    private String room;
    /**< CPU */
    @NonNull
    private String cpuName;
    @NonNull
    private Integer cpuCores;
    @NonNull
    private Integer cpuFreq; // MHz
    /**< RAM */
    @NonNull
    private Integer ramSize; // Mo
    @NonNull
    private Integer ramFreq; // MHz

    @ManyToMany(mappedBy = "devices", cascade = CascadeType.REMOVE)
    private Collection<ServiceEntity> services;

    // @ManyToMany
    // private Collection<ServiceEntity> services;
    // @OneToMany
    // private Collection<HealthInfoEntity> healthInfos;
    // @OneToMany
    // private Collection<DeviceConfigEntity> deviceConfigs;

    public String getUid() {
        return name;
    }
}