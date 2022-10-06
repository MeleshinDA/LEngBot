package com.example.lengbot.services;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.telegram.handlers.HandlersStates;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;

/**
 * Состояния пользователя
 */

@Getter
@Setter
@Scope("prototype")
public class UserStatesService {

  private HandlersStates curState = HandlersStates.DEFAULT;

  private UserTestService userTestService;
  private UserDAO userDAO;

  public UserStatesService(UserDAO userDAO, QuestionDAO questionDAO) {
    this.userDAO = userDAO;
    this.userTestService = new UserTestService(new ArrayList<>(questionDAO.getTest()));
  }

  /**
   * Генерация ответа пользователю, если он проходит тест.
   *
   * @param inputText Ввод пользователя.
   * @param chatId    Id чата с пользователем.
   * @return Строка с вопросом или, если тест закончен, результатом.
   */
  public String doTest(String inputText, String chatId) {
    if (userTestService.getCurQuestion() != null) {
      userTestService.checkAnswer(inputText.toLowerCase());
    }

    userTestService.getNextQuestion();

    if (userTestService.getCurQuestion() == null) {
      userDAO.updateUser(Long.parseLong(chatId), userTestService.getLevel());

      curState = HandlersStates.DEFAULT;

      return "Тест пройден! Ваш балл: " + userTestService.getScore() + " из 41" + "\nВаш уровень: "
          + userTestService.getLevel();
    }

    return userTestService.getCurQuestion().getText() + "\n" + userTestService.getCurQuestion()
        .getPossibleAnswers();
  }

  /**
   * Генерация ответа пользователю, если он вводит уровень.
   *
   * @param inputText Ввод пользователя.
   * @param chatId    Id чата с пользователем.
   * @return Строка, информирующая об успешном сохранении уровня, или напоминание о корректной форме
   * ввода.
   */
  public String enterLvl(String inputText, String chatId) {
    if (userTestService.isLvlCorrect(inputText)) {
      userDAO.updateUser(Integer.parseInt(chatId), inputText.toUpperCase());
      curState = HandlersStates.DEFAULT;
      return "Уровень сохранён";
    }
    return "Неправильно введён уровень, доступные варианты: A1, A2, B1, B2, C1, C2";
  }
}
