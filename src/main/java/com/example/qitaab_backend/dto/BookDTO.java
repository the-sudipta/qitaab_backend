package com.example.qitaab_backend.dto;

import lombok.Data;

@Data
public class BookDTO {

    private Long id; // ID of the Book Table
    private String name;
    private String author;
    private String type;
    private double price;
    private int quantity;


    private Long user_id; // ID of the user table
}
