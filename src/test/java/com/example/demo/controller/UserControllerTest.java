package com.example.demo.controller;

import com.example.demo.model.dto.UserDto;
import com.example.demo.model.entity.User;
import com.example.demo.model.response.UserResponse;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.UUID;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createUserSuccessfully() throws Exception {
        UserDto dto = new UserDto();
        dto.setName("Juan Rodriguez");
        dto.setEmail("juan@rodriguez.com");
        dto.setPassword("Hunter28$");

        User user = new User();
        user.setUserId(UUID.randomUUID());
        user.setEmail(dto.getEmail());

        UserResponse response = new UserResponse();
        response.setEmail(dto.getEmail());

        when(modelMapper.map(any(UserDto.class), any())).thenReturn(user);
        when(userService.insert(any(User.class))).thenReturn(user);
        when(modelMapper.map(any(User.class), any())).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(dto.getEmail()));
    }

}
