package com.example.lengbot.telegram.handlers;

import com.example.lengbot.API.HandlersAPI;
import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import com.example.lengbot.telegram.keyboards.InlineKeyboardMaker;
import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.HashSet;
import java.util.Set;


@Component
public class MessageHandler {

    private Boolean isTesting = false;
    private Boolean isEnteringLvl = false;
    private Question curQuestion;
    private int scores;
    private ReplyKeyboardMaker replyKeyboardMaker;
    private InlineKeyboardMaker inlineKeyboardMaker;

    private HandlersAPI handlersAPI;


    public MessageHandler() {

    }

    @Autowired
    public MessageHandler(ReplyKeyboardMaker replyKeyboardMaker, InlineKeyboardMaker inlineKeyboardMaker, HandlersAPI handlersAPI) {
        this.replyKeyboardMaker = replyKeyboardMaker;
        this.inlineKeyboardMaker = inlineKeyboardMaker;
        this.handlersAPI = handlersAPI;

    }

    public BotApiMethod<?> answerMessage(Message message) {
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        if (isTesting) {
            if (curQuestion.getRightAnswer().equals(inputText))
                scores += curQuestion.getWeight();
            return getTestMessages(chatId);
        }

        if (isEnteringLvl) {
            Set<String> rightLvls = new HashSet<String>();
            rightLvls.add("A0");
            rightLvls.add("A1");
            rightLvls.add("A2");
            rightLvls.add("B1");
            rightLvls.add("B2");

            if (rightLvls.contains(inputText.toUpperCase())) {
                handlersAPI.userDAO.UpdateUser(Integer.parseInt(chatId), inputText.toUpperCase());
                isEnteringLvl = false;
                return new SendMessage(chatId, "Уровень сохранён");
            }

            return new SendMessage(chatId, "Неправильно введён уровень, доступные варианты: A0, A1, A2, B1, B2");
        }


        return switch (inputText) {
            case null -> throw new IllegalArgumentException();
            case "/start" -> getStartMessage(chatId);
            case "Пройти тест" -> {
                isTesting = true;
                yield getTestMessages(chatId);
            }
            case "Ввести уровень" -> {
                isEnteringLvl = true;
                yield getLevelMessages(chatId);
            }
            default -> new SendMessage(chatId, "Пожалуйста воспользуйтесь клавиатурой");
        };
    }

    private SendMessage getStartMessage(String chatId) {
        handlersAPI.userDAO.SaveUser(Integer.parseInt(chatId));
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getTestMessages(String chatId) {
        curQuestion = handlersAPI.GetNextQuestion();
        SendMessage sendMessage = new SendMessage(chatId, "");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        if (curQuestion == null) {
            isTesting = false;
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
