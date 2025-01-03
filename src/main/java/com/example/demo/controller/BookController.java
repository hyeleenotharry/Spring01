package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.User;
import com.example.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 모든 책
    @GetMapping
    public List<Book> getBooks() {
        return null;
    }

    // 특정 책 - 아이디
    public Book findById(Long id) {
        return null;
    }

    // 특정 책 - 제목
    public Book findByTitle(String title) {
        return null;
    }

    // 새로운 책
    @PostMapping("/new/")
    public Book saveBook(@RequestBody Book book) {
        return null;
    }

    // 책 대출
    public Book borrowBook(Book book, User user) {
        return null;
    }

    // 책 삭제
    public void deleteById(Long id) {

    }
}
