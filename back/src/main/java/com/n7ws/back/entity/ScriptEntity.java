package com.n7ws.back.entity;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
// @Table(name = "Scripts")
@RequiredArgsConstructor
public class ScriptEntity implements com.n7ws.back.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uid;

    @NonNull
    private String name;
    @NonNull
    private String path;

    @NonNull
    @ManyToMany
    private Collection<ServiceEntity> services;
}
