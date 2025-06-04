package com.n7ws.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.n7ws.back.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String uid);
}
