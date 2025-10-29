package com.example.demo.service.impl;

import com.example.demo.dao.UserDao;
import com.example.demo.model.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.service.valid.UserValid;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private UserValid userValid;

    @Override
    public User insert(User user) {
        userValid.validateInsert(user);
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setToken(JwtUtil.generateToken(user.getName()));
        user.setActive(true);
        return userDao.save(user);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return userDao.findById(id);
    }

}
