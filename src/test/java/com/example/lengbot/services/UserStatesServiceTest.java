package com.example.lengbot.services;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserStatesServiceTest {

    @Test
    void doTest_OnInputWhenNullReturnResultMessage() {
        var mockQuestionDAO = Mockito.mock(QuestionDAO.class);
        var mockUserDAO = Mockito.mock(UserDAO.class);

        UserStatesService userStatesService = new UserStatesService(mockUserDAO, mockQuestionDAO);
        List<Question> test = new ArrayList<>();

        userStatesService.setUserTestService(new UserTestService(test));
        String actual = userStatesService.doTest("a", "2");
        String expected = "Тест пройден! Ваш балл: " + userStatesService.getUserTestService().getScore() + " из 41" +
                "\nВаш уровень: " + userStatesService.getUserTestService().getLevel();
        assertEquals(expected, actual);

    }

    @Test
    void doTest_OnInputReturnOtherQuestion() {
        var mockQuestionDAO = Mockito.mock(QuestionDAO.class);
        var mockUserDAO = Mockito.mock(UserDAO.class);
        UserStatesService userStatesService = new UserStatesService(mockUserDAO, mockQuestionDAO);
        List<Question> test = new ArrayList<>();
        test.add(new Question("Hello", "a b c d", "a", 2));
        userStatesService.setUserTestService(new UserTestService(test));
        String actual = userStatesService.doTest("a", "2");
        String expected = "Hello\na b c d";
        assertEquals(expected, actual);

    }

    @Test
    void doTest_FullScoresAvailable() {
        var mockQuestionDAO = Mockito.mock(QuestionDAO.class);
        var mockUserDAO = Mockito.mock(UserDAO.class);

        UserStatesService userStatesService = new UserStatesService(mockUserDAO, mockQuestionDAO);
        List<Question> test = new ArrayList<>();
        test.add(new Question("Hello", "a b c d", "a", 20));
        test.add(new Question("Night", "a b c d", "b", 21));

        userStatesService.setUserTestService(new UserTestService(test));
        userStatesService.doTest("Пройти тест", "2");
        userStatesService.doTest("a", "2");
        var actual = userStatesService.doTest("b", "2");

        String expected = "Тест пройден! Ваш балл: 41 из 41" +
                "\nВаш уровень: C2";
        assertEquals(expected, actual);

    }

    /**
     * Проверка того, что тест проходится после его первого прохождения.
     *
     *
     */
    @Test
    void doTest_SecondTime() {
        var mockQuestionDAO = Mockito.mock(QuestionDAO.class);
        var mockUserDAO = Mockito.mock(UserDAO.class);

        UserStatesService userStatesService = new UserStatesService(mockUserDAO, mockQuestionDAO);
        List<Question> test = new ArrayList<>();
        test.add(new Question("Hello", "a b c d", "a", 20));
        test.add(new Question("Night", "a b c d", "b", 21));

        userStatesService.setUserTestService(new UserTestService(test));
        userStatesService.doTest("Пройти тест", "2");
        userStatesService.doTest("a", "2");
        userStatesService.doTest("b", "2");

        userStatesService.doTest("Пройти тест", "2");
        userStatesService.doTest("a", "2");
        var actual = userStatesService.doTest("b", "2");

        String expected = "Тест пройден! Ваш балл: 41 из 41" +
                "\nВаш уровень: C2";
        assertEquals(expected, actual);

    }

    @Test
    void enterLvl_OnCorrectLvlInput() {
        var mockQuestionDAO = Mockito.mock(QuestionDAO.class);
        var mockUserDAO = Mockito.mock(UserDAO.class);
        UserStatesService userStatesService = new UserStatesService(mockUserDAO, mockQuestionDAO);
        String actual = userStatesService.enterLvl("A2", "2");
        String expected = "Уровень сохранён";
        assertEquals(expected, actual);
    }

    @Test
    void enterLvl_OnInCorrectLvlInput() {
        var mockQuestionDAO = Mockito.mock(QuestionDAO.class);
        var mockUserDAO = Mockito.mock(UserDAO.class);
        UserStatesService userStatesService = new UserStatesService(mockUserDAO, mockQuestionDAO);
        String actual = userStatesService.enterLvl("BB5", "2");
        String expected = "Неправильно введён уровень, доступные варианты: A1, A2, B1, B2, C1, C2";
        assertEquals(expected, actual);
    }
}