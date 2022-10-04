package com.example.lengbot;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JdbcTemplateForTests {
    @Getter
    @Value("${db.url}")
    private static String url = "jdbc:postgresql://ec2-52-48-159-67.eu-west-1.compute.amazonaws.com:5432/ddl953k0ah32pv";
    @Getter
    @Value("${db.username}")
    private static String userName = "fyegxzllqrcnxo";
    @Getter
    @Value("${db.password}")
    private static String password = "080e77a8d01e7357882bf08d07f5c74351942f505ad3520b8e01aee31c46192b";

    public static DataSource getPostgreDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setUsername(userName);

        return dataSource;
    }
}
