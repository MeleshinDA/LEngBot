package com.example.lengbot.services;

import com.example.lengbot.models.Question;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Класс работы с тестом пользователя
 */
@Service
@Getter
@Scope("prototype")
public class UserTestService {

  private final Set<String> rightLevels = new HashSet<>();
  private Question curQuestion;
  private List<Question> test;
  private Iterator<Question> testIterator;

  private int score;

  public UserTestService() {
  }

  public UserTestService(List<Question> test) {
    this.test = new ArrayList<>(test);
    this.testIterator = test.listIterator();
    rightLevels.add("A0");
    rightLevels.add("A1");
    rightLevels.add("A2");
    rightLevels.add("B1");
    rightLevels.add("B2");
    rightLevels.add("C1");
    rightLevels.add("C2");
  }

  /**
   * Итерируется по вопросам теста
   */
  public void getNextQuestion() {
    if (testIterator.hasNext()) {
      curQuestion = testIterator.next();
    } else {
      curQuestion = null;
    }
  }

  /**
   * Проверка правильности ответа
   *
   * @param answer ответ пользователя на вопрос теста
   */
  public void checkAnswer(String answer) {
    if (curQuestion.getRightAnswer().equals(answer)) {
      score += curQuestion.getWeight();
    }
  }

  /**
   * Проверка правильности введенного уровня языка
   *
   * @param lvl введенный уровень языка
   * @return правильность введенного
   */
  public Boolean isLvlCorrect(String lvl) {
    return rightLevels.contains(lvl.toUpperCase());
  }

  /**
   * Востанавливает тест к изначальному состоянию
   */
  public void resetTest() {
    testIterator = test.listIterator();
    curQuestion = null;
    score = 0;
  }

  /**
   * Получение уровня языка у пользователя
   *
   * @return уровень языка пользователя
   */
  public String getLevel() {
    if (score <= 3) {
      return "A0";
    } else if (score <= 7) {
      return "A1";
    } else if (score <= 14) {
      return "A2";
    } else if (score <= 21) {
      return "B1";
    } else if (score <= 28) {
      return "B2";
    } else if (score <= 35) {
      return "C1";
    } else {
      return "C2";
    }
  }
}
