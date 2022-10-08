package com.example.lengbot.services;

import com.example.lengbot.appconfig.constants.BotMessageEnum;
import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.dao.WordsDAO;
import com.example.lengbot.services.structures.DefaultHashMap;
import com.example.lengbot.telegram.handlers.HandlersStates;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import org.apache.tomcat.util.buf.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Message;

public class HandlersCommandService { // Можно разедлить на 2 класса, в одном те, которые требуют chatId, в другом inputText и chatId

  @Getter
  private final DefaultHashMap<String, Function<Message, String>> allCommands;
  private final UserStatesService userStatesService;
  private final UserDAO userDAO;
  private final QuestionDAO questionDAO;
  private final WordsDAO wordsDAO;


  public HandlersCommandService(UserDAO userDAO, QuestionDAO questionDAO,
      WordsDAO wordsDAO, UserStatesService userStatesService) {
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

  private String startMethod(Message message) {
    String chatId = message.getChatId().toString();
    userDAO.saveUser(Integer.parseInt(chatId));

    return BotMessageEnum.HELP_MESSAGE.getMessage();
  }

  private String startTestMethod(Message message) {
    String chatId = message.getChatId().toString();
    String inputText = message.getText();

    userStatesService.getUserTestService().setTest(questionDAO.getTest());
    userStatesService.setCurState(HandlersStates.TESTING);
    userStatesService.getUserTestService().resetTest();
    return "Решите следующие задания:\n" + userStatesService.doTest(inputText, chatId);
  }

  private String enterLvlMethod(Message message) {
    userStatesService.setCurState(HandlersStates.ENTERING_LEVEL);
    return "Введите Ваш уровень. Доступны: A0, A1, A2, B1, B2, C1, C2.";
  }

  private String helpMethod(Message message) {
    return BotMessageEnum.HELP_MESSAGE.getMessage();
  }

  private String getNewWordsMethod(Message message) {
    Long chatId = message.getChatId();
    List<String> words = wordsDAO.getNewWordsFromDb(chatId);

    return StringUtils.join(words, '\n');
  }

  private String defaultMethod(Message message) {
    return BotMessageEnum.NON_COMMAND_MESSAGE.getMessage();
  }
}
