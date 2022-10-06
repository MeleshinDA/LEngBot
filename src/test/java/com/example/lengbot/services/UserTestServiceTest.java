package com.example.lengbot.services;

import com.example.lengbot.models.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTestServiceTest {

  @Test
  void getNextQuestion_OnOneQuestionReturnsSameQuestion() {
    Question question = new Question("Hello", "a b c d", "a", 2);
    List<Question> test = new ArrayList<>();
    test.add(question);
    UserTestService userTestService = new UserTestService(test);

    userTestService.getNextQuestion();
    Question actual = userTestService.getCurQuestion();

    assertEquals(actual, question);
  }

  @Test
  void getNextQuestion_NullQuestionReturnsNull() {
    List<Question> test = new ArrayList<>();
    UserTestService userTestService = new UserTestService(test);

    userTestService.getNextQuestion();
    Question actual = userTestService.getCurQuestion();

    assertNull(actual);
  }

  @Test
  void getNextQuestion_MultipleQuestionsRightOrder() {
    List<Question> expected = new ArrayList<>();
    expected.add(new Question("Hello", "a b c d", "a", 2));
    expected.add(new Question("Day", "a b c d", "c", 2));
    expected.add(new Question("Welcome", "a b c d", "d", 3));
    expected.add(new Question("Uber", "a b c d", "a", 3));
    expected.add(new Question("Lake", "a b c d", "b", 3));
    UserTestService userTestService = new UserTestService(expected);

    List<Question> actual = new ArrayList<>();
    for (var i = 0; i < expected.size(); i++) {
      userTestService.getNextQuestion();
      actual.add(userTestService.getCurQuestion());
    }

    assertEquals(actual, expected);
  }

  @Test
  void checkAnswer_OnRightAnswerAddScores() {
    List<Question> test = new ArrayList<>();
    Question q1 = new Question("Hello", "a b c d", "a", 2);
    test.add(q1);
    UserTestService userTestService = new UserTestService(test);
    userTestService.getNextQuestion();
    Integer expected = userTestService.getScore() + q1.getWeight();
    userTestService.checkAnswer("a");
    Integer actual = userTestService.getScore();

    assertEquals(expected, actual);
  }

  @Test
  void checkAnswer_OnWrongAnswerDoesntAddScores() {
    List<Question> test = new ArrayList<>();
    test.add(new Question("Hello", "a b c d", "a", 2));
    UserTestService userTestService = new UserTestService(test);
    userTestService.getNextQuestion();
    Integer expected = userTestService.getScore();
    userTestService.checkAnswer("b");
    Integer actual = userTestService.getScore();

    assertEquals(expected, actual);
  }

  @Test
  void isLvlCorrect_OnCorrectInputReturnsTrueA0() {
    UserTestService userTestService = new UserTestService(new ArrayList<>());
    Boolean actual = userTestService.isLvlCorrect("A0");
    assertTrue(actual);
  }

  @Test
  void isLvlCorrect_OnCorrectInputReturnsTrueA1() {
    UserTestService userTestService = new UserTestService(new ArrayList<>());
    Boolean actual = userTestService.isLvlCorrect("A1");
    assertTrue(actual);
  }

  @Test
  void isLvlCorrect_OnCorrectInputReturnsTrueA2() {
    UserTestService userTestService = new UserTestService(new ArrayList<>());
    Boolean actual = userTestService.isLvlCorrect("A2");
    assertTrue(actual);
  }

  @Test
  void isLvlCorrect_OnCorrectInputReturnsTrueB1() {
    UserTestService userTestService = new UserTestService(new ArrayList<>());
    Boolean actual = userTestService.isLvlCorrect("B1");
    assertTrue(actual);
  }

  @Test
  void isLvlCorrect_OnCorrectInputReturnsTrueB2() {
    UserTestService userTestService = new UserTestService(new ArrayList<>());
    Boolean actual = userTestService.isLvlCorrect("B2");
    assertTrue(actual);
  }

  @Test
  void isLvlCorrect_OnWrongInputReturnsFalse() {
    UserTestService userTestService = new UserTestService();
    Boolean actual = userTestService.isLvlCorrect("A5");
    assertFalse(actual);
  }

  @Test
  void resetTest_SetsCurQuestionIndexToZero() {
    List<Question> test = new ArrayList<>();
    test.add(new Question("Hello", "a b c d", "a", 2));
    UserTestService userTestService = new UserTestService(test);
    userTestService.getNextQuestion();

    userTestService.resetTest();
    assertNull(userTestService.getCurQuestion());

  }

  @Test
  void getLevel() {
    List<Question> test = new ArrayList<>();
    test.add(new Question("Hello", "a b c d", "a", 2));
    test.add(new Question("Day", "a b c d", "c", 2));
    test.add(new Question("Welcome", "a b c d", "d", 3));
    test.add(new Question("Uber", "a b c d", "a", 3));
    test.add(new Question("Lake", "a b c d", "b", 3));
    test.add(new Question("Rumor", "a b c d", "c", 5));
    UserTestService userTestService = new UserTestService(test);
    for (var i = 0; i < test.size(); ++i) {
      userTestService.getNextQuestion();
      Question curQuestion = userTestService.getCurQuestion();
      userTestService.checkAnswer(curQuestion.getRightAnswer());
    }

    assertEquals("B1", userTestService.getLevel());
  }
}