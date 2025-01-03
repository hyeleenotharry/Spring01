package com.example.demo.repository;

import com.example.demo.domain.Book;
import com.example.demo.domain.Loan;
import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Loan findBookById(Long id);

    @Modifying
    @Query(value = "INSERT INTO Loan (book, user) VALUES (:book, :user)", nativeQuery = true)
    String borrowBook(@Param("book") Book book, @Param("user") User user);

    @Modifying
    @Query("UPDATE Loan l SET l.isReturned = true WHERE l.id = :loanId AND l.user = :user")
    void returnBook(@Param("loanId") Long loanId, @Param("user") User user);
}
