package com.n7ws.back;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.n7ws.back.domain.UserDomain;
import com.n7ws.back.entity.UserEntity;
import com.n7ws.back.mapper.UserMapper;
import com.n7ws.back.model.UserModel;

@SpringBootTest(classes = BackApplication.class)
public class UserTests {
    private UserDomain userDomain;
    private UserModel userModel;
    private UserEntity userEntity;

    @Test
    void domainConstructorNoException() {
        // Tests if the constructor of UserDomain does not throw an exception
        assertDoesNotThrow(() -> {
            new UserDomain(
                "uid",
                "doe",
                "john",
                "johndoe42",
                false
            );
        });
    }

    @Test
    void constructorCorrectness() {
        // Tests if the constructor of UserDomain initializes the attributes correctly
        assertEquals("doe", userDomain.lastname());
        assertEquals("john", userDomain.firstname());
        assertEquals("johndoe42", userDomain.password());
        assertFalse(userDomain.admin());
    }

    @Test
    void mapDomainToModel() {
        // Tests if the mapping from UserDomain to UserModel is correct
        userModel = null;
        userModel = UserMapper.toModel(userDomain);
        assertEquals(userDomain.lastname(), userModel.lastname());
        assertEquals(userDomain.firstname(), userModel.firstname());
        assertEquals(userDomain.password(), userModel.password());
        assertEquals(userDomain.admin(), userModel.admin());
    }

    @Test
    void mapEntityToDomain() {
        // Tests if the mapping from UserEntity to UserDomain is correct
        userDomain = null;
        userDomain = UserMapper.toDomain(userEntity);
        assertEquals(userEntity.getLastname(), userDomain.lastname());
        assertEquals(userEntity.getFirstname(), userDomain.firstname());
        assertEquals(userEntity.getPassword(), userDomain.password());
        assertEquals(userEntity.getAdmin(), userDomain.admin());
    }

    @BeforeEach
    void init() {
        // Initializes a UserDomain object before each test
        this.userDomain = new UserDomain(
            "uid",
            "doe",
            "john",
            "johndoe42",
            false
        );

        // Initializes a UserModel object before each test
        this.userModel = new UserModel(
            "uid",
            "doe",
            "john",
            "johndoe42",
            false
        );

        // Initializes a UserEntity object before each test
        this.userEntity = new UserEntity(
            "john@doe.fr",
            "doe",
            "john",
            "johndoe42",
            new ArrayList<>(),
            false
        );
    }
}
