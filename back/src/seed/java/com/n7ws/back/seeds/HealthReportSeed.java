package com.n7ws.back.seeds;

import java.util.ArrayList;

import com.n7ws.back.SeedApplication;
import com.n7ws.back.repository.HealthReportRepository;

public class HealthReportSeed extends Seed {
    public HealthReportSeed() {
        this.repository = SeedApplication.context.getBean(HealthReportRepository.class);
        this.entities = new ArrayList<>();
        this.count = 50;
    }

    public void seed() {
    }
}
