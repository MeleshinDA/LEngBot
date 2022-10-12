package com.example.lengbot.services;

import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.dao.WordsDAO;
import com.example.lengbot.services.structures.DefaultHashMap;
import com.example.lengbot.telegram.handlers.HandlersStates;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import org.apache.tomcat.util.buf.StringUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


/**
 * Класс, в котором собраны методы-команды. Вызов команд происходит через словарь.
 */
public class HandlersCommandService { // Можно разедлить на 2 класса, в одном те, которые требуют chatId, в другом inputText и chatId

  /**
   * Словарь всех команд, работающий по принципу - по введённой строке выдать метод, в который потом
   * будет передаваться аргумент.
   */
  @Getter
  private final DefaultHashMap<String, Function<Message, SendMessage>> allCommands;
  private final UserStatesService userStatesService;
  private final UserDAO userDAO;
  private final QuestionDAO questionDAO;
  private final WordsDAO wordsDAO;


  public HandlersCommandService(UserDAO userDAO, QuestionDAO questionDAO, WordsDAO wordsDAO,
      UserStatesService userStatesService) {
    this.userDAO = userDAO;
    this.questionDAO = questionDAO;
    this.wordsDAO = wordsDAO;
    this.userStatesService = userStatesService;

    this.allCommands = new DefaultHashMap<>(this::defaultMethod);
    this.allCommands.put("/start", this::startMethod);
    this.allCommands.put("Пройти тест", this::startTestMethod);
    this.allCommands.put("Ввести уровень", this::enterLvlMethod);
    this.allCommands.put("Помощь", this::helpMethod);
    this.allCommands.put("Получить слова", this::getNewWordsMethod);
  }

  /**
   * Метод, который вызывается при начале работы с пользователем.
   *
   * @param message полученное сообщение
   */
  private SendMessage startMethod(Message message) {

    String chatId = message.getChatId().toString();
    userDAO.saveUser(Integer.parseInt(chatId));

    return new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
  }

  /**
   * Метод, который вызывается при начале работы с тестом.
   *
   * @param message полученное сообщение
   */
  private SendMessage startTestMethod(Message message) {
    String chatId = message.getChatId().toString();
    String inputText = message.getText();

    userStatesService.getUserTestService().setTest(questionDAO.getTest());
    userStatesService.setCurState(HandlersStates.TESTING);
    userStatesService.getUserTestService().resetTest();

    return new SendMessage(chatId,
        "Решите следующие задания:\n" + userStatesService.doTest(inputText, chatId));
  }

  /**
   * Метод, который вызывается при вводе уровня
   *
   * @param message полученное сообщение
   */
  private SendMessage enterLvlMethod(Message message) {
    String chatId = message.getChatId().toString();
    userStatesService.setCurState(HandlersStates.ENTERING_LEVEL);

    return new SendMessage(chatId, "Введите Ваш уровень. Доступны: A0, A1, A2, B1, B2, C1, C2.");
  }

  /**
   * Метод, который вызывается при получении пользователем помощи
   *
   * @param message полученное сообщение
   */
  private SendMessage helpMethod(Message message) {
    String chatId = message.getChatId().toString();

    return new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
  }

  /**
   * Метод, который выдаёт пользователю новые слова
   *
   * @param message полученное сообщение
   */
  private SendMessage getNewWordsMethod(Message message) {
    Long chatId = message.getChatId();

    List<String> words = wordsDAO.getNewWordsFromDb(chatId);

    return new SendMessage(chatId.toString(), StringUtils.join(words, '\n'));
  }

  /**
   * Метод, который используется в случае, когда команда не распознана.
   *
   * @param message полученное сообщение
   */
  private SendMessage defaultMethod(Message message) {
    String chatId = message.getChatId().toString();

    return new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
  }
}
