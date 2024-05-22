package com.example.qitaab_backend.dto;

import lombok.Data;

@Data
public class BookPurchaseDTO {

    private Long book_id;
    private int quantity;
    private Long user_id;


}
