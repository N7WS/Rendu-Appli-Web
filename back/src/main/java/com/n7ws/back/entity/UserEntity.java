package com.n7ws.back.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
// @Table(name = "Users")
@RequiredArgsConstructor
public class UserEntity implements com.n7ws.back.entity.Entity {
    @Id
    @NonNull
    private String email;
    @NonNull
    private String lastname;
    @NonNull
    private String firstname;
    @NonNull
    private String password;
    @NonNull
    private Boolean admin;

    public String getUid() {
        return email;
    }
}