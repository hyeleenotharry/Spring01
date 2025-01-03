package com.example.demo.service;

import com.example.demo.domain.Book;
import com.example.demo.domain.Loan;
import com.example.demo.domain.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    BookRepository bookRepository;

    public String barrowBook(String title, User user){
        Optional<Book> book = Optional.ofNullable(bookRepository.findByTitle(title));

        if (book.isPresent()){
            if (book.get().isAvailable() == true){
                Book user_bk = book.get();
                Loan loan = new Loan();

                loan.setUser(user);
                loan.setBook(book.get());
                loan.setLoanDate(LocalDate.now());

                loanRepository.borrowBook(user_bk, user);
            } else {
                return "이미 대출된 책입니다.";
            }

        } else {
            return "찾으시는 책이 존재하지 않습니다.";
        }

        return "대출이 완료되었습니다.";
    }
    public void returnBook(Long id, User user){
        Loan loan = loanRepository.findBookById(Long.valueOf(id));

        Book book = loan.getBook();
        book.setAvailable(true);

        loanRepository.returnBook(id, user);
    }
}
