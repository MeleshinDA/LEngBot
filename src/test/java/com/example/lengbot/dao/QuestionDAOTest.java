package com.example.lengbot.dao;


import java.util.ArrayList;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class QuestionDAOTest {

  @Autowired
  private QuestionDAO questionDAO;

  @Test
  void getTest_ReturnsIsSizeRight() {
    var actual = new ArrayList<>(questionDAO.getTest());
    assertEquals(actual.size(), 15);
  }

  @Test
  void getTest_ReturnsIsTestRandom() {
    var test1 = new ArrayList<>(questionDAO.getTest());
    var test2 = new ArrayList<>(questionDAO.getTest());
    assertNotEquals(test1, test2);
  }
}