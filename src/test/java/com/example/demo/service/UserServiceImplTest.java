package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.model.entity.Phone;
import com.example.demo.model.entity.User;
import com.example.demo.service.impl.UserServiceImpl;
import com.example.demo.service.valid.UserValid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private UserValid userValid;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertUserSuccessfully() {
        User user = new User();
        user.setName("Juan Rodriguez");
        user.setEmail("juan@rodriguez.com");
        user.setPassword("Hunter28$");

        User savedUser = new User();
        savedUser.setUserId(java.util.UUID.randomUUID());
        when(userDao.save(any(User.class))).thenReturn(savedUser);

        User result = userService.insert(user);

        verify(userValid).validateInsert(user);
        verify(userDao).save(user);

        assertNotNull(result);
        assertNotNull(user.getCreated());
        assertNotNull(user.getModified());
        assertNotNull(user.getLastLogin());
        assertTrue(user.isActive());
        assertNotNull(user.getToken());
    }

}
