package com.example.demo.repository;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository {
    // new book
    void save(Book book);
    // search all books
    List<Book> findAll();
    Book findById(Long id);
    Book findByTitle(String title);

    void barrowBook(Book book, User user);

    void deleteById(Long id);

}
