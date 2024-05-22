package com.example.qitaab_backend.dto;

import lombok.Data;

@Data
public class SellerSignupDTO {

    private String email;
    private String password;

    private String name;
    private String phone;
    private double amount;
    private String address;


}
