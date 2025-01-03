package com.example.demo.controller;

import com.example.demo.domain.Book;
import com.example.demo.domain.Loan;
import com.example.demo.domain.User;
import com.example.demo.service.BookService;
import com.example.demo.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book/")
public class BookController {

    private BookService bookService;
    private LoanService loanService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 모든 책
    @GetMapping
    public List<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping("/findById/")
    // 특정 책 - 아이디
    public Book findById(Long id) {
        return bookService.findById(id);
    }

    @PostMapping("/findByTitle")
    // 특정 책 - 제목
    public Book findByTitle(String title) {
        return bookService.findByTitle(title);
    }

    // 새로운 책
    @PostMapping("/newBook/")
    public void saveBook(@RequestBody String title, String author) {
        bookService.save(title, author);
    }

    @PostMapping("/borrowBook/")
    // 책 대출
    public String borrowBook(String title, User user) {
        return loanService.barrowBook(title, user);
    }

    @PostMapping("/returnBook/")
    // 책 반환
    public void returnBook(Long id, User user) {
        loanService.returnBook(id, user);
    }

    @PostMapping("/deleteBook/")
    // 책 삭제
    public void deleteById(Long id) {

    }
}
