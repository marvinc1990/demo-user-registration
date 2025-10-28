package com.example.demo.service;

import com.example.demo.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User insert(User user);

    User update(User user);

    void delete(UUID id);

    Optional<User> findById(UUID id);

    List<User> findAll();

}
