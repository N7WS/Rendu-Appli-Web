package com.n7ws.back;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.n7ws.back.domain.ServiceDomain;
import com.n7ws.back.entity.ServiceEntity;
import com.n7ws.back.mapper.ServiceMapper;
import com.n7ws.back.model.ServiceModel;

@SpringBootTest(classes = BackApplication.class)
public class ServiceTests {
    private ServiceDomain serviceDomain;
    private ServiceModel serviceModel;
    private ServiceEntity serviceEntity;

    @Test
    void domainConstructorNoException() {
        // Tests if the constructor of ServiceDomain does not throw an exception
        assertDoesNotThrow(() -> {
            new ServiceDomain(
                "uid",
                "apache-server",
                8000,
                new ArrayList<>()
            );
        });
    }

    @Test
    void constructorCorrectness() {
        // Tests if the constructor of ServiceDomain initializes the attributes correctly
        assertEquals("apache-server", serviceDomain.name());
        assertEquals(8000, serviceDomain.port());
    }

    @Test
    void mapDomainToModel() {
        // Tests if the mapping from ServiceDomain to ServiceModel is correct
        serviceModel = null;
        serviceModel = ServiceMapper.toModel(serviceDomain);
        assertEquals(serviceDomain.name(), serviceModel.name());
        assertEquals(serviceDomain.port(), serviceModel.port());
    }

    @Test
    void mapEntityToDomain() {
        // Tests if the mapping from ServiceEntity to ServiceDomain is correct
        serviceDomain = null;
        serviceDomain = ServiceMapper.toDomain(serviceEntity);
        assertEquals(serviceEntity.getName(), serviceDomain.name());
        assertEquals(serviceEntity.getPort(), serviceDomain.port());
    }

    @BeforeEach
    void init() {
        // Initializes a ServiceDomain object before each test
        this.serviceDomain = new ServiceDomain(
            "uid",
            "apache-server",
            8000,
            new ArrayList<>()
        );

        // Initializes a ServiceModel object before each test
        this.serviceModel = new ServiceModel(
            "uid",
            "apache-server",
            8000,
            new ArrayList<>()
        );

        // Initializes a ServiceEntity object before each test
        this.serviceEntity = new ServiceEntity(
            "apache-server",
            8000,
            new ArrayList<>()
        );
    }
}
