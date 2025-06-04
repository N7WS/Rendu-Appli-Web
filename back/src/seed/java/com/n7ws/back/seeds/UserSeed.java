package com.n7ws.back.seeds;

import java.util.ArrayList;

import com.n7ws.back.SeedApplication;
import com.n7ws.back.repository.UserRepository;

public class UserSeed extends Seed {
    public UserSeed() {
        this.repository = SeedApplication.context.getBean(UserRepository.class);
        this.entities = new ArrayList<>();
        this.count = 50;
    }

    public void seed() {
    }
}
