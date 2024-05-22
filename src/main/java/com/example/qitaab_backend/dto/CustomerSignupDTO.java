package com.example.qitaab_backend.dto;

import lombok.Data;

@Data
public class CustomerSignupDTO {

    private String email;
    private String password;

    private String name;
    private String phone;
    private int age;


}
