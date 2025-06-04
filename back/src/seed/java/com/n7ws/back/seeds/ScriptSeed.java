package com.n7ws.back.seeds;

import java.util.ArrayList;

import com.n7ws.back.SeedApplication;
import com.n7ws.back.entity.ScriptEntity;
import com.n7ws.back.repository.ScriptRepository;

public class ScriptSeed extends Seed {
    public ScriptSeed() {
        this.repository = SeedApplication.context.getBean(ScriptRepository.class);
        this.entities = new ArrayList<>();
        this.count = 50;
    }

    public void seed() {
        for(int i = 0; i < this.count; i++) {
            String name = String.format("Script %d", i);
            String path = String.format("/path/to/script-%d", i);
            ScriptEntity script = new ScriptEntity(
                name,
                path,
                new ArrayList<>()
            );
            this.entities.add(script);
        }
    }
}
