package com.example.qitaab_backend.repository;

import com.example.qitaab_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long> {

    User findByEmailAndPassword(String email, String password);
    User findByEmail(String email);

    User findUserById(Long id);

//    User findById(Long id);

}
