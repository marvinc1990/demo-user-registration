package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.model.response.UserResponse;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@RequestBody UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        return ResponseEntity.ok(mapper.map(userService.insert(user),
                UserResponse.class));
    }

    @PutMapping
    public ResponseEntity<UserResponse> update(@RequestBody UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        return ResponseEntity.ok(mapper.map(userService.update(user),
                UserResponse.class));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable UUID userId) {
        userService.delete(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("id", userId);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(mapper.map(user, UserResponse.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<User> users = userService.findAll();
        List<UserResponse> list = users.stream()
                .map(customer -> mapper.map(customer, UserResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

}
