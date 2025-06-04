package com.n7ws.back.mapper;

import com.n7ws.back.domain.DeviceDomain;
import com.n7ws.back.entity.DeviceEntity;
import com.n7ws.back.model.DeviceModel;

public interface DeviceMapper extends Mapper {
    static DeviceDomain toDomain(DeviceEntity deviceEntity) {
        return new DeviceDomain(
            deviceEntity.getName(),
            deviceEntity.getStatus(),
            deviceEntity.getRoom(),
            deviceEntity.getCpuName(),
            deviceEntity.getCpuCores(),
            deviceEntity.getCpuFreq(), // MHz
            deviceEntity.getRamSize(), // Mo
            deviceEntity.getRamFreq(), // MHz
            deviceEntity.getServices()
                .stream()
                .map(ServiceMapper::toDomain)
                .toList()
        );
    }

    static DeviceModel toModel(DeviceDomain deviceEntity) {
        return new DeviceModel(
            deviceEntity.name(),
            deviceEntity.status(),
            deviceEntity.room(),
            deviceEntity.cpuName(),
            deviceEntity.cpuCores(),
            deviceEntity.cpuFreq(), // MHz
            deviceEntity.ramSize(), // Mo
            deviceEntity.ramFreq(), // MHz
            deviceEntity.services()
                .stream()
                .map(ServiceMapper::toModel)
                .toList()
        );
    }

    static DeviceEntity toEntity(DeviceModel deviceModel) {
        return new DeviceEntity(
            deviceModel.name(),
            deviceModel.status(),
            deviceModel.room(),
            deviceModel.cpuName(),
            deviceModel.cpuCores(),
            deviceModel.cpuFreq(), // MHz
            deviceModel.ramSize(), // Mo
            deviceModel.ramFreq(), // MHz
            deviceModel.services() == null ? null :
                deviceModel.services()
                    .stream()
                    .map(ServiceMapper::toEntity)
                    .toList()
        );
    }
}
