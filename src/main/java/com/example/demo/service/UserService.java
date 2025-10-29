package com.example.demo.service;

import com.example.demo.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

    User insert(User user);

    Optional<User> findById(UUID id);

}
