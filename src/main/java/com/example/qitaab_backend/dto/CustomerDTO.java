package com.example.qitaab_backend.dto;

import lombok.Data;

@Data
public class CustomerDTO {


    private Long id; // ID of the Customer Table
    private String name;
    private String phone;
    private int age;


    private String email;
    //    private String password;
    private Long user_id; // ID of the user table
}
