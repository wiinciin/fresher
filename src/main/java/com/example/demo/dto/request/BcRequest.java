package com.example.demo.dto.request;

import lombok.Data;

@Data
public class BcRequest {
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private String programmingLanguage;
}
