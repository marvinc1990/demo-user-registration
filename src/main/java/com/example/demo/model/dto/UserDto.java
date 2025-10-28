package com.example.demo.model.dto;

import com.example.demo.model.entity.Phone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private UUID userId;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private List<PhoneDto> phones;

}
