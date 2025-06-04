package com.n7ws.back.mapper;

import com.n7ws.back.domain.ServiceDomain;
import com.n7ws.back.entity.ServiceEntity;
import com.n7ws.back.model.ServiceModel;

public interface ServiceMapper extends Mapper {
    static ServiceDomain toDomain(ServiceEntity serviceEntity) {
        return new ServiceDomain(
            serviceEntity.getUid(),
            serviceEntity.getName(),
            serviceEntity.getPort(),
            serviceEntity.getScripts()
                .stream()
                .map(ScriptMapper::toDomain)
                .toList()
        );
    }

    static ServiceModel toModel(ServiceDomain serviceDomain) {
        return new ServiceModel(
            serviceDomain.uid(),
            serviceDomain.name(),
            serviceDomain.port(),
            serviceDomain.scripts()
                .stream()
                .map(ScriptMapper::toModel)
                .toList()
        );
    }

    static ServiceEntity toEntity(ServiceModel serviceModel) {
        if (serviceModel.scripts() == null) {
            return new ServiceEntity(
                serviceModel.uid(),
                serviceModel.name(),
                serviceModel.port(),
                null,
                null
            );
        }

        return new ServiceEntity(
            serviceModel.uid(),
            serviceModel.name(),
            serviceModel.port(),
            null,
            serviceModel.scripts()
                .stream()
                .map(ScriptMapper::toEntity)
                .toList()
        );
    }
}
