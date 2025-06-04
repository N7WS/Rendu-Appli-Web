package com.n7ws.back.seeds;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import com.n7ws.back.Format;
import com.n7ws.back.entity.Entity;

public abstract class Seed {
    public static final Logger logger = LoggerFactory.getLogger(Seed.class);

    @SuppressWarnings("rawtypes")
    protected JpaRepository repository;
    protected Collection<Entity> entities;
    protected Integer count;

    private String name = this.getClass().getSimpleName().replace("Seed", "s");

    public void delete() {
        logger.info(Format.centeredHeaderTitle(name, 50, '-'));
        logger.info("Deleting {}...", name);
        this.repository.deleteAll();
    }

    @SuppressWarnings("unchecked")
    public void run() {
        logger.info(Format.centeredHeaderTitle(name, 50, '-'));
        logger.info("Seeding {}...", name);
        seed();
        logger.info("{} seeded: {}", name, this.count);
        this.entities.forEach(entity -> {
            logger.info(" - {}", entity);
        });
        this.repository.saveAll(this.entities);
    }

    abstract protected void seed();
}
