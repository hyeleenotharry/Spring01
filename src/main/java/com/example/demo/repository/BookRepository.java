package com.example.demo.repository;

import com.example.demo.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // new book
    Book save(Book book);
    // search all books
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book findByTitle(String title);

    void deleteById(Long id);

}
