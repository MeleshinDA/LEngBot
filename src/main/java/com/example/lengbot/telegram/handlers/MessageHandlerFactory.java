package com.example.lengbot.telegram.handlers;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import org.springframework.stereotype.Component;

@Component
public class MessageHandlerFactory {

  private final UserDAO userDAO;
  private final QuestionDAO questionDAO;

  public MessageHandlerFactory(UserDAO userDAO, QuestionDAO questionDAO) {
    this.userDAO = userDAO;
    this.questionDAO = questionDAO;
  }

  public MessageHandler createMessageHandler() {
    return new MessageHandler(this.userDAO, this.questionDAO);
  }
}
