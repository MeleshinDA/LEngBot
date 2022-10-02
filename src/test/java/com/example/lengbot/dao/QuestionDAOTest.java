package com.example.lengbot.dao;


import org.junit.jupiter.api.Test;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;


class QuestionDAOTest {

    private final String url = "based";
    private final String userName = "floppa";
    private final String password = "gigachad";
    @Test
    void QuestionDAO_TestConnection() throws SQLException {

        Connection conn = DriverManager.getConnection(url, userName, password);
        assertTrue(conn.isValid(10));
    }

    public DataSource postgreDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setPassword(password);
        dataSource.setUsername(userName);

        return dataSource;
    }
    @Test
    void getTest_Successful() throws SQLException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate(postgreDataSource());
        QuestionDAO questionDAO = new QuestionDAO(jdbcTemplate);
        var actual = questionDAO.getTest();
        assertEquals(actual.size(), 15);
    }
}