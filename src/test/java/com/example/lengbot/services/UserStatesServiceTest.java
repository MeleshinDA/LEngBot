package com.example.lengbot.services;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserStatesServiceTest {

  @Autowired
  private UserDAO userDAO;
  @Autowired
  private QuestionDAO questionDAO;

  @Test
  void doTest_OnEndOfTestReturnResultMessage() {
    UserStatesService userStatesService = new UserStatesService(userDAO, questionDAO);

    for (Question question : userStatesService.getUserTestService().getTest()) {
      userStatesService.doTest("a", "2");
    }

    String actual = userStatesService.doTest("a", "2");
    String expected =
        "Тест пройден! Ваш балл: " + userStatesService.getUserTestService().getScore() + " из 41" +
            "\nВаш уровень: " + userStatesService.getUserTestService().getLevel();

    assertEquals(expected, actual);

  }

  @Test
  void doTest_OnInputReturnOtherQuestion() {
    UserStatesService userStatesService = new UserStatesService(userDAO, questionDAO);

    String actual = userStatesService.doTest("a", "2");
    String expected = "Переведите слово: Sunrise\n"
        + "a. Озеро, b. Река, c. Восход, d. Страна";
    assertEquals(expected, actual);

  }

  @Test
  void doTest_FullScoresAvailable() {
    UserStatesService userStatesService = new UserStatesService(userDAO, questionDAO);

    String actual = null;
    userStatesService.doTest("Пройти тест", "2");
    for (Question question : userStatesService.getUserTestService().getTest()) {
      actual = userStatesService.doTest(question.getRightAnswer(), "2");
    }

    String expected = "Тест пройден! Ваш балл: 41 из 41" +
        "\nВаш уровень: C2";
    assertEquals(expected, actual);

  }

  /**
   * Проверка того, что тест проходится после его первого прохождения.
   */
  @Test
  void doTest_SecondTime() {
    UserStatesService userStatesService = new UserStatesService(userDAO, questionDAO);

    String actual = null;
    userStatesService.doTest("Пройти тест", "2");
    for (Question question : userStatesService.getUserTestService().getTest()) {
      userStatesService.doTest(question.getRightAnswer(), "2");
    }

    userStatesService.doTest("Пройти тест", "2");
    for (Question question : userStatesService.getUserTestService().getTest()) {
      actual = userStatesService.doTest(question.getRightAnswer(), "2");
    }

    String expected = "Тест пройден! Ваш балл: 41 из 41" +
        "\nВаш уровень: C2";
    assertEquals(expected, actual);

  }

  @Test
  void enterLvl_OnCorrectLvlInput() {
    UserStatesService userStatesService = new UserStatesService(userDAO, questionDAO);
    String actual = userStatesService.enterLvl("A2", "2");
    String expected = "Уровень сохранён";
    assertEquals(expected, actual);
  }

  @Test
  void enterLvl_OnInCorrectLvlInput() {
    UserStatesService userStatesService = new UserStatesService(userDAO, questionDAO);
    String actual = userStatesService.enterLvl("BB5", "2");
    String expected = "Неправильно введён уровень, доступные варианты: A1, A2, B1, B2, C1, C2";
    assertEquals(expected, actual);
  }
}