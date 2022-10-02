package com.example.lengbot.telegram.handlers;

import com.example.lengbot.Services.UserStatesService;
import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


/**
 * Обработчик сообщений пользователя
 */
@Getter
@Component
public class MessageHandler {

    private ReplyKeyboardMaker replyKeyboardMaker;

    private UserDAO userDAO;
    private QuestionDAO questionDAO;

    private UserStatesService userStatesService;


    public MessageHandler() {

    }

    @Autowired
    public MessageHandler(ReplyKeyboardMaker replyKeyboardMaker, UserDAO userDAO, QuestionDAO questionDAO) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.userDAO = userDAO;
        this.questionDAO = questionDAO;
        this.userStatesService = new UserStatesService(userDAO, questionDAO);
    }

    public MessageHandler(MessageHandler that) {
        this(that.getReplyKeyboardMaker(), that.getUserDAO(), that.getQuestionDAO());
    }

    /**
     * @param message сообщение от пользователя
     * @return ответ на сообщение пользователя
     */
    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        return switch (userStatesService.getCurState()) {
            case DEFAULT -> handleMessage(inputText, chatId);
            case TESTING -> new SendMessage(chatId, userStatesService.doTest(inputText, chatId));
            case ENTERINGLVL -> new SendMessage(chatId, userStatesService.enterLvl(inputText, chatId));
        };
    }

    /**
     * Ответ пользователю в штатной ситуации, в зависимости от его сообщения.
     * @param inputText Ввод пользователя.
     * @param chatId Id чата с пользователем.
     * @return Сообщение с соответствующим ответом для пользователя.
     */
    private SendMessage handleMessage(String inputText, String chatId) {
        return switch (inputText) {
            case null -> throw new IllegalArgumentException();
            case "/start" -> {
                userDAO.SaveUser(Integer.parseInt(chatId));
                yield new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
            }
            case "Пройти тест" -> {
                userStatesService.setCurState(HandlersStates.TESTING);
                yield new SendMessage(chatId, "Решите следующие задания:\n" + userStatesService.doTest(inputText, chatId));
            }
            case "Ввести уровень" -> {
                userStatesService.setCurState(HandlersStates.ENTERINGLVL);
                yield new SendMessage(chatId, "Введите Ваш уровень. Доступны: A0, A1, A2, B1, B2, C1, C2.");
            }
            case "Помощь" -> new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
            default -> new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        };
    }
}
