package com.n7ws.back.seeds;

import java.util.ArrayList;

import com.n7ws.back.Random;
import com.n7ws.back.SeedApplication;
import com.n7ws.back.entity.DeviceEntity;
import com.n7ws.back.repository.DeviceRepository;

public class DeviceSeed extends Seed {
    public DeviceSeed() {
        this.repository = SeedApplication.context.getBean(DeviceRepository.class);
        this.entities = new ArrayList<>();
        this.count = 50;
    }

    public void seed() {
        for(int i = 0; i < this.count; i++) {
            DeviceEntity device = new DeviceEntity(
                Random.randomName(),
                Random.randomState(),
                Random.randomRoom(),
                Random.randomCpuName(),
                Random.randomCpuCores(),
                Random.randomCpuFreq(),
                Random.randomRamSize(),
                Random.randomRamFreq()
            );
            this.entities.add(device);
        }
    }
}
