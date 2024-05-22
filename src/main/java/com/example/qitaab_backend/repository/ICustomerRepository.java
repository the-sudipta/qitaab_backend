package com.example.qitaab_backend.repository;

import com.example.qitaab_backend.entity.Customer;
import com.example.qitaab_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

    Customer findCustomerById(Long id);
    Customer findCustomerByUser(User user);
}
