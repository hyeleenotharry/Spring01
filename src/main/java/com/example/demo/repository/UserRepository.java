package com.example.demo.repository;

import com.example.demo.domain.User;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

public interface UserRepository{
    void save(User user);
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);

    boolean existsById(Long id);
}


