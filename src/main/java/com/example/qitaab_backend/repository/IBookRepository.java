package com.example.qitaab_backend.repository;

import com.example.qitaab_backend.entity.Book;
import com.example.qitaab_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long> {

    Book findBookByUser(User user);
    Book findBookById(Long id);

    List<Book> findAllByUser(User user);

}
