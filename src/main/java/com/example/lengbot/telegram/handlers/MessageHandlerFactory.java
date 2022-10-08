package com.example.lengbot.telegram.handlers;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.dao.WordsDAO;
import org.springframework.stereotype.Component;

@Component
public class MessageHandlerFactory {

  private final UserDAO userDAO;
  private final QuestionDAO questionDAO;
  private final WordsDAO wordsDAO;

  public MessageHandlerFactory(UserDAO userDAO, QuestionDAO questionDAO, WordsDAO wordsDAO) {
    this.userDAO = userDAO;
    this.questionDAO = questionDAO;
    this.wordsDAO = wordsDAO;
  }

  public MessageHandler createMessageHandler() {
    return new MessageHandler(this.userDAO, this.questionDAO, this.wordsDAO);
  }
}
