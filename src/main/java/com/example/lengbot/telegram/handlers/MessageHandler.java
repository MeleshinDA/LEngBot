package com.example.lengbot.telegram.handlers;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.WordsDAO;
import com.example.lengbot.services.HandlersCommandService;

import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.services.UserStatesService;

import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


/**
 * Обработчик сообщений пользователя
 */

public class MessageHandler {

  private final UserStatesService userStatesService;
  private final HandlersCommandService handlersCommandService;

  private final ReplyKeyboardMaker replyKeyboardMaker = new ReplyKeyboardMaker();

  protected MessageHandler(UserDAO userDAO, QuestionDAO questionDAO, WordsDAO wordsDAO) {
    this.userStatesService = new UserStatesService(userDAO, questionDAO);
    this.handlersCommandService = new HandlersCommandService(userDAO, questionDAO, wordsDAO, userStatesService);
  }


  /**
   * @param message сообщение от пользователя
   * @return ответ на сообщение пользователя
   */
  public BotApiMethod<?> answerMessage(Message message) {

    String chatId = message.getChatId().toString();

    String replyText = userStatesService.handleStates(message, handlersCommandService.getAllCommands());

    SendMessage replyMessage = new SendMessage(chatId, replyText);

    replyMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

    return replyMessage;

  }
}
