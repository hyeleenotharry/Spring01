package com.example.demo.service;

import com.example.demo.exception.UserNotFoundException;
import jakarta.persistence.*;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional(propagation = Propagation.REQUIRED) // 트랜잭션이 없을 경우 새 트랜잭션 사용
    public String join(User user) {
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(user);

        userRepository.save(user); ;

        // 비즈니스 로직
        // 작업 실패 시 전체 롤백
        if (user.getUsername().equals("error")){  // "error" 라는 유저가 있을 경우 강제 롤백 - 예외 발생 조건
            throw new RuntimeException("강제 롤백");
        }
        return user.getEmail();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW) // 항상 새로운 트랜잭션 실행, 실행 중인 트랜잭션이 있다면 중단하고 새로운 트랜잭션 실행
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED) // commit 된 데이터만 읽기
    protected void validateDuplicateMember(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(m -> {
                    throw  new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
