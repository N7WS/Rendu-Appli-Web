package com.n7ws.back;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.n7ws.back.seeds.DeviceSeed;
import com.n7ws.back.seeds.HealthReportSeed;
import com.n7ws.back.seeds.ScriptSeed;
import com.n7ws.back.seeds.Seed;
import com.n7ws.back.seeds.ServiceSeed;
import com.n7ws.back.seeds.UserSeed;

@SpringBootApplication
public class SeedApplication {

    public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SeedApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        context = app.run(args);

        Seed.logger.info(Format.centeredHeaderTitle("Seeding database", 50, '='));
        Collection<Seed> seeds = new ArrayList<>();
        seeds.add(new DeviceSeed());
        seeds.add(new HealthReportSeed());
        seeds.add(new ScriptSeed());
        seeds.add(new ServiceSeed());
        seeds.add(new UserSeed());
        seeds.forEach(seed -> seed.delete());
        seeds.forEach(seed -> seed.run());
        Seed.logger.info(Format.centeredFooter(50, '-'));
        Seed.logger.info("Database seeded successfully.");
        Seed.logger.info(Format.centeredFooter(50, '='));

        context.close();
        System.exit(0);
	}
}
