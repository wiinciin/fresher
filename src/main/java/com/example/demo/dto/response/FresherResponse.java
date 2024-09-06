package com.example.demo.dto.response;


import lombok.Data;



@Data
public class FresherResponse {
    private int id;
    private int idUser;
    private String username;
    private String name;
    private String email;
    private String phoneNumber;
    private String programmingLanguage;
}
