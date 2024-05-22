package com.example.qitaab_backend.repository;

import com.example.qitaab_backend.entity.Seller;
import com.example.qitaab_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerByUser(User user);
    Seller findSellerById(Long id);


}
