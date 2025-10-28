package com.example.demo.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationProperties {

    @Value("${app.regex.password.regexp}")
    private String passwordRegex;

    @Value("${app.regex.email.regexp}")
    private String emailRegex;

}
