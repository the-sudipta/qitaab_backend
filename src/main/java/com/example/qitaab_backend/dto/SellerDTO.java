package com.example.qitaab_backend.dto;

import lombok.Data;

@Data
public class SellerDTO {

    private Long id; // ID of the Seller Table
    private String name;
    private String phone;
    private String address;
    private double amount;

    private String email;
    //    private String password;
    private Long user_id; // ID of the user table
}
