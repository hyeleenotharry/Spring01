package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
// 데이터베이스와의 연결(Connection)을 관리하는 객체
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement  // transaction 활성화
public class TransactionConfig {
    // DataSource 및 JdbcTemplate 설정 가능
    // 추가적인 설정이 없다면 빈 class
    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    // jdbc 트랜잭션 매니저 - spring boot 의 경우 자동으로 설정되어 있기 때문에 따로 설정 필요하지 x
    // 이외에도 JpaTransactionManager, HibernateTransactionManager 등이 있음
}
