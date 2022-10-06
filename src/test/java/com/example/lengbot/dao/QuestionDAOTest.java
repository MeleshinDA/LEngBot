package com.example.lengbot.dao;


import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuestionDAOTest {

  @Autowired
  private QuestionDAO questionDAO;
  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String userName;

  @Value("${spring.datasource.password}")
  private String password;

  @Test
  void QuestionDAO_TestConnection() throws SQLException {
    Connection conn = DriverManager.getConnection(url, userName, password);
    assertTrue(conn.isValid(10));
  }

  @Test
  void getTest_Successful() {
    var actual = new ArrayList<>(questionDAO.getTest());
    assertEquals(actual.size(), 15);
  }
}