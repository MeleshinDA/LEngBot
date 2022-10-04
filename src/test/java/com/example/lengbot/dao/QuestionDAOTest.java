package com.example.lengbot.dao;


import com.example.lengbot.JdbcTemplateForTests;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuestionDAOTest {
    @Test
    void QuestionDAO_TestConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(JdbcTemplateForTests.getUrl(),
                JdbcTemplateForTests.getUserName(),
                JdbcTemplateForTests.getPassword());
        assertTrue(conn.isValid(10));
    }
    @Test
    void getTest_Successful() throws SQLException {
        var actual = QuestionDAO.getTest();
        assertEquals(actual.size(), 15);
    }
}