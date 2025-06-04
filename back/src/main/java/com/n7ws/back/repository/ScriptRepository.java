package com.n7ws.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.n7ws.back.entity.ScriptEntity;

public interface ScriptRepository extends JpaRepository<ScriptEntity, String> {

}
