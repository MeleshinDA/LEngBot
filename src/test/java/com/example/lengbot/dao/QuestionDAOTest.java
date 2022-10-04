package com.example.lengbot.dao;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuestionDAOTest {

    @Value("${db.url}")
    private String url;

    @Value("${db.username}")
    private String userName;

    @Value("${db.password}")
    private String password;

    @Test
    void QuestionDAO_TestConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, userName, password);
        assertTrue(conn.isValid(10));
    }

    @Test
    void getTest_Successful() {
        var actual = QuestionDAO.getTest();
        assertEquals(actual.size(), 15);
    }
}