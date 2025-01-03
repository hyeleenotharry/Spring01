package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        // user attribute mapping
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}

@Repository
public class UserDao implements UserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = "insert into users (username, password, email) values (?, ?, ?)";
        try {  // 예외 처리
            jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail()); // update() 를 통해 create()
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "select * from users where username = ?";
        try{
            // username 단일 결과 조회
            Optional<User> user = Optional.ofNullable(jdbcTemplate.queryForObject(sql, new UserRowMapper(), username)); // Optional return, nullpointerror 를 피하기 위해 Optional 로 wrap
            return Optional.ofNullable(user.orElse(null));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }

    }

    @Override
    public User findByEmail(String email) {
        String sql = "select * from users where email = ?";

        return (jdbcTemplate.queryForObject(sql, new UserRowMapper(), email));
    } // 기본키 > return null 불가능

    @Override
    public List<User> findAll() {
        String sql = "select * from users";
        // 다수의 결과 list 형태로 조회
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from users where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "select * from users where id = ?";
        User user = jdbcTemplate.queryForObject(sql, new UserRowMapper(), id);
        if (user != null) {
            return true;
        }
        return false;
    }
    // 테스트용 db 날리기 메소드
    public void clearDb(){
        String sql = "delete from users";
        jdbcTemplate.update(sql);
    }
}
