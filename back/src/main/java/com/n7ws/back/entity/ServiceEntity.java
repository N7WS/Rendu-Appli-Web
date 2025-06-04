package com.n7ws.back.entity;

import java.util.Collection;

import jakarta.persistence.CascadeType;
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
// @Table(name = "Services")
@RequiredArgsConstructor
public class ServiceEntity implements com.n7ws.back.entity.Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uid;
    @NonNull
    private String name;
    @NonNull
    private Integer port;

    // @ManyToMany(mappedBy = "services")
    @ManyToMany
    private Collection<DeviceEntity> devices;

    @ManyToMany(mappedBy = "services", cascade = CascadeType.REMOVE)
    private Collection<ScriptEntity> scripts;
}
