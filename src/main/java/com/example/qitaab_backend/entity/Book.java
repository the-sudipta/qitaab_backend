package com.example.qitaab_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String author;

    @Column
    private double price;

    @Column
    private int quantity;

    @Column
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}