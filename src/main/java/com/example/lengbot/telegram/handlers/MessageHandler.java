package com.example.lengbot.telegram.handlers;

import com.example.lengbot.API.UserStatesService;
import com.example.lengbot.API.UserTestService;
import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import com.example.lengbot.telegram.keyboards.InlineKeyboardMaker;
import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;


@Component
public class MessageHandler {

    private UserStatesService userStatesService;
    private ReplyKeyboardMaker replyKeyboardMaker;
    private InlineKeyboardMaker inlineKeyboardMaker;

    private UserTestService userTestService;
    private UserDAO userDAO;

    private QuestionDAO questionDAO;

    private List<Question> test;

    public MessageHandler() {

    }

    @Autowired
    public MessageHandler(ReplyKeyboardMaker replyKeyboardMaker, InlineKeyboardMaker inlineKeyboardMaker, UserDAO userDAO, QuestionDAO questionDAO) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.userDAO = userDAO;
        this.questionDAO = questionDAO;
        this.test = new ArrayList<>(this.questionDAO.GetTest());
    }

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        if (userStatesService.getIsTesting())
        {
            userTestService.CheckAnswer(inputText);
            return getTestMessages(chatId);
        }

        if (userStatesService.getIsEnteringLvl())
        {
            if (userTestService.CheckUserLvl(inputText))
            {
                userDAO.UpdateUser(Integer.parseInt(chatId), inputText.toUpperCase());
                userStatesService.setIsEnteringLvl(false);
                return new SendMessage(chatId, "Уровень сохранён");
            }
            return new SendMessage(chatId, "Неправильно введён уровень, доступные варианты: A0, A1, A2, B1, B2");
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
            default -> new SendMessage(chatId, "Пожалуйста воспользуйтесь клавиатурой");
        };
    }

    private SendMessage getStartMessage(String chatId) {
        userDAO.SaveUser(Integer.parseInt(chatId));
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getTestMessages(String chatId) {
        Question curQuestion = userTestService.NextQuestion();
        SendMessage sendMessage = new SendMessage(chatId, "");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        if (curQuestion == null) {
            userStatesService.setIsTesting(true);
            sendMessage.setText("Тест пройден! Ваш балл:");
        } else
            sendMessage.setText(curQuestion.getText() + "\n" + curQuestion.getPossibleAnswers());

        return sendMessage;
    }

    private SendMessage getLevelMessages(String chatId) {

        SendMessage sendMessage = new SendMessage(chatId, "Введите уровень:");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }
}
