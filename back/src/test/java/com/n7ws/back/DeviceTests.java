package com.n7ws.back;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.n7ws.back.domain.DeviceDomain;
import com.n7ws.back.entity.DeviceEntity;
import com.n7ws.back.enums.DeviceState;
import com.n7ws.back.mapper.DeviceMapper;
import com.n7ws.back.model.DeviceModel;

@SpringBootTest(classes = BackApplication.class)
public class DeviceTests {
    private DeviceDomain deviceDomain;
    private DeviceModel deviceModel;
    private DeviceEntity deviceEntity;

    @Test
    void domainConstructorNoException() {
        // Tests if the constructor of DeviceDomain does not throw an exception
        assertDoesNotThrow(() -> {
            new DeviceDomain(
                "iode",
                DeviceState.ONLINE,
                "c200",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
            );
        });
    }

    @Test
    void constructorCorrectness() {
        // Tests if the constructor of DeviceDomain initializes the attributes correctly
        assertEquals("iode", deviceDomain.name());
        assertEquals(DeviceState.ONLINE, deviceDomain.status());
        assertEquals("c200", deviceDomain.room());
    }

    @Test
    void mapDomainToModel() {
        // Tests if the mapping from DeviceDomain to DeviceModel is correct
        deviceModel = null;
        deviceModel = DeviceMapper.toModel(deviceDomain);
        assertEquals(deviceDomain.name(), deviceModel.name());
        assertEquals(deviceDomain.status(), deviceModel.status());
        assertEquals(deviceDomain.room(), deviceModel.room());
    }

    @Test
    void mapEntityToDomain() {
        // Tests if the mapping from DeviceEntity to DeviceDomain is correct
        deviceDomain = null;
        deviceDomain = DeviceMapper.toDomain(deviceEntity);
        assertEquals(deviceEntity.getName(), deviceDomain.name());
        assertEquals(deviceEntity.getStatus(), deviceDomain.status());
        assertEquals(deviceEntity.getRoom(), deviceDomain.room());
    }

    @BeforeEach
    void init() {
        // Initializes a DeviceDomain object before each test
        this.deviceDomain = new DeviceDomain(
            "iode",
            DeviceState.ONLINE,
            "c200",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );

        // Initializes a DeviceModel object before each test
        this.deviceModel = new DeviceModel(
            "iode",
            DeviceState.ONLINE,
            "c200",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );

        // Initializes a DeviceEntity object before each test
        this.deviceEntity = new DeviceEntity(
            "iode",
            DeviceState.ONLINE,
            "c200",
            new ArrayList<>(),
            new ArrayList<>(),
            new ArrayList<>()
        );
    }
}
