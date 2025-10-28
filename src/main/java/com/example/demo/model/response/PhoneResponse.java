package com.example.demo.model.response;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneResponse {

    private String number;
    private String cityCode;
    private String countryCode;

}
