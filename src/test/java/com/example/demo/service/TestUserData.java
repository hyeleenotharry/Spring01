package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.repository.UserDao;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class TestUserData {
    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userRepository;

    @AfterEach
    public void tearDown() {
        userRepository.clearDb();
    } // @Test 실행할 때마다 db 초기화

    @Test
    public void requiredTest(){
        User user = new User();
        user.setUsername("kim");
        user.setEmail("dlgpfl@gmail.com");
        user.setPassword("123456");

        try {
            userService.join(user);
        }catch (Exception e){
            e.printStackTrace();
        }

        assertTrue(userRepository.findByUsername("kim").isPresent()); // join 확인
        //assertFalse(userRepository.findByUsername("JohnDoe").isPresent());
    }

    @Test
    public void requiredNewTest(){
        User user1 = new User();
        user1.setUsername("lee");
        user1.setEmail("dlgpgl@gmail.com");
        user1.setPassword("123456");

        User user2 = new User();
        user2.setUsername("error");
        user2.setEmail("error@example.com");
        user2.setPassword("password123");

        try{
            userService.join(user1); // 여기까지 실행
            userService.join(user2); // 롤백 발생

            Long userId = userRepository.findByUsername("lee").get().getId();
            userService.deleteById(userId);
        }catch (Exception e){
            e.printStackTrace();
        }

        assertFalse(userRepository.findByUsername("error").isPresent()); // join 트랜잭션이 롤백되었으므로 "error" 는 존재하지 않는다
        assertTrue(userRepository.findByUsername("lee").isPresent()); // userService.join(user2) 에서 catch 문으로 넘어갔으므로 delete 문 실행되지 않는다
    }
}
