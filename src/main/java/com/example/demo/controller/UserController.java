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

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getById(@PathVariable UUID userId) {
        return userService.findById(userId)
                .map(user -> ResponseEntity.ok(mapper.map(user, UserResponse.class)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
