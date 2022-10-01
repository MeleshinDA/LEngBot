package com.example.lengbot.telegram.handlers;

import com.example.lengbot.API.UserStatesService;
import com.example.lengbot.API.UserTestService;
import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;


/**
 * Обработчик сообщений пользователя
 */
@Getter
@Component
public class MessageHandler {

    private UserStatesService userStatesService;
    private ReplyKeyboardMaker replyKeyboardMaker;

    private UserTestService userTestService;
    private UserDAO userDAO;
    private QuestionDAO questionDAO;


    private List<Question> test;

    public MessageHandler() {

    }

    @Autowired
    public MessageHandler(ReplyKeyboardMaker replyKeyboardMaker, UserDAO userDAO, QuestionDAO questionDAO, UserTestService userTestService) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.userDAO = userDAO;
        this.questionDAO = questionDAO;
        this.test = new ArrayList<>(this.questionDAO.GetTest());
        this.userStatesService = new UserStatesService();
        this.userTestService = userTestService;
    }

    public MessageHandler(MessageHandler that){
        this(that.getReplyKeyboardMaker(), that.getUserDAO(), that.getQuestionDAO(), that.getUserTestService());
    }

    /**
     * @param message сообщение от пользователя
     * @return ответ на сообщение пользователя
     */
    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        if (userStatesService.getIsTesting()) {
            userTestService.CheckAnswer(inputText);
            return getTestMessages(chatId);
        }

        if (userStatesService.getIsEnteringLvl()) {
            if (userTestService.CheckUserLvl(inputText)) {
                userDAO.UpdateUser(Integer.parseInt(chatId), inputText.toUpperCase());
                userStatesService.setIsEnteringLvl(false);
                return new SendMessage(chatId, "Уровень сохранён");
            }
            return new SendMessage(chatId, "Неправильно введён уровень, доступные варианты: A1, A2, B1, B2, C1, C2");
        }

        return switch (inputText) {
            case null -> throw new IllegalArgumentException();
            case "/start" -> getStartMessage(chatId);
            case "Пройти тест" -> {
                userStatesService.setIsTesting(true);
                userTestService = new UserTestService(test);
                yield getTestMessages(chatId);
            }
            case "Ввести уровень" -> {
                userStatesService.setIsEnteringLvl(true);
                yield getLevelMessages(chatId);
            }
            case "Помощь" -> new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
            default -> new SendMessage(chatId, BotMessageEnum.NON_COMMAND_MESSAGE.getMessage());
        };
    }

    /**
     *
     * @param chatId идентификатор пользователя
     * @return сообщение с приветственным текстом
     */
    private SendMessage getStartMessage(String chatId) {
        userDAO.SaveUser(Integer.parseInt(chatId));
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    /**
     * @param chatId идентификатор пользователя
     * @return сообщения с текстом вопросов теста
     */
    private SendMessage getTestMessages(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, "");
        sendMessage.enableMarkdown(true);
        Question curQuestion = userTestService.NextQuestion();

        if (curQuestion == null) {
            userStatesService.setIsTesting(false);
            sendMessage.setText("Тест пройден! Ваш балл: " + userTestService.getScore() + " из 41" +
                    "\nВаш уровень: " + userTestService.getLevel());
            userDAO.UpdateUser(Long.parseLong(chatId), userTestService.getLevel());
            userTestService.resetTest();
        } else
            sendMessage.setText(curQuestion.getText() + "\n" + curQuestion.getPossibleAnswers());

        return sendMessage;
    }

    /**
     * @param chatId идентификатор пользователя
     * @return сообщение с текстом запроса уровня
     */
    private SendMessage getLevelMessages(String chatId) {

        SendMessage sendMessage = new SendMessage(chatId, "Введите уровень:");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }
}
