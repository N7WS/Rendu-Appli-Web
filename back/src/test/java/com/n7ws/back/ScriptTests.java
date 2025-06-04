package com.n7ws.back;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.n7ws.back.domain.ScriptDomain;
import com.n7ws.back.entity.ScriptEntity;
import com.n7ws.back.mapper.ScriptMapper;
import com.n7ws.back.model.ScriptModel;

@SpringBootTest(classes = BackApplication.class)
public class ScriptTests {
    private ScriptDomain scriptDomain;
    private ScriptModel scriptModel;
    private ScriptEntity scriptEntity;

    @Test
    void domainConstructorNoException() {
        // Tests if the constructor of ScriptDomain does not throw an exception
        assertDoesNotThrow(() -> {
            new ScriptDomain(
                "uid",
                "apache-server",
                "/home/user/scripts/apache-server.sh"
            );
        });
    }

    @Test
    void constructorCorrectness() {
        // Tests if the constructor of ScriptDomain initializes the attributes correctly
        assertEquals("apache-server", scriptDomain.name());
        assertEquals("/home/user/scripts/apache-server.sh", scriptDomain.path());
        assertEquals("uid", scriptDomain.uid());
    }

    @Test
    void mapDomainToModel() {
        // Tests if the mapping from ScriptDomain to ScriptModel is correct
        scriptModel = null;
        scriptModel = ScriptMapper.toModel(scriptDomain);
        assertEquals(scriptDomain.name(), scriptModel.name());
        assertEquals(scriptDomain.path(), scriptModel.path());
    }

    @Test
    void mapEntityToDomain() {
        // Tests if the mapping from ScriptEntity to ScriptDomain is correct
        scriptDomain = null;
        scriptDomain = ScriptMapper.toDomain(scriptEntity);
        assertEquals(scriptEntity.getName(), scriptDomain.name());
        assertEquals(scriptEntity.getPath(), scriptDomain.path());
    }

    @BeforeEach
    void init() {
        // Initializes a ScriptDomain object before each test
        this.scriptDomain = new ScriptDomain(
            "uid",
            "apache-server",
            "/home/user/scripts/apache-server.sh"
        );

        // Initializes a ScriptModel object before each test
        this.scriptModel = new ScriptModel(
            "uid",
            "apache-server",
            "/home/user/scripts/apache-server.sh"
        );

        // Initializes a ScriptEntity object before each test
        this.scriptEntity = new ScriptEntity(
            "apache-server",
            "/home/user/scripts/apache-server.sh"
        );
    }
}
