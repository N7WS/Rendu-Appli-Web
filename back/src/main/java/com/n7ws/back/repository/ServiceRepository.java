package com.n7ws.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.n7ws.back.entity.ServiceEntity;

public interface ServiceRepository extends JpaRepository<ServiceEntity, String> {

}
