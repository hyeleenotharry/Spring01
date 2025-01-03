package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(String title, String author) {
        Book book = new Book(title, author);
        bookRepository.save(book);

    };
    // search all books
    public List<Book> findAll(){
        return null;
    };
    public Book findById(Long id){
        return null;
    };
    public Book findByTitle(String title){
        return null;
    };

    public void deleteById(Long id){

    };
}
