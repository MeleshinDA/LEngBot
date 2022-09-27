package com.example.lengbot.telegram.handlers;

import com.example.lengbot.API.HandlersAPI;
import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.constants.ButtonNameEnum;
import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import com.example.lengbot.telegram.TelegramApiClient;
import com.example.lengbot.telegram.keyboards.InlineKeyboardMaker;
import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.LinkedList;
import java.util.List;


@Component
@AllArgsConstructor
public class MessageHandler {

    //private Boolean isTesting = false;


    ReplyKeyboardMaker replyKeyboardMaker;
    InlineKeyboardMaker inlineKeyboardMaker;
    UserDAO userDAO;
    QuestionDAO questionDAO;

    //HandlersAPI handlersAPI;

    public BotApiMethod<?> answerMessage(Message message){
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        /*if (isTesting)
            return getTestMessages(chatId);*/
        return switch (inputText){
            case null -> throw new IllegalArgumentException();
            case "/start" -> getStartMessage(chatId);
            case "Пройти тест" -> getTestMessages(chatId);
                //isTesting = true;
            case "Ввести уровень" -> getLevelMessages(chatId);
            default -> new SendMessage(chatId, "Пожалуйста воспользуйтесь клавиатурой");
        };
    }

    private SendMessage getStartMessage(String chatId){
        userDAO.SaveUser(Integer.parseInt(chatId));
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getTestMessages(String chatId){
        List<Question> questions =  questionDAO.GetTest();
        List<SendMessage> messages = new LinkedList<>();
        for (Question question: questions){
            SendMessage newMessage = new SendMessage(chatId, question.getText());
            newMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtons(" "));
            messages.add(newMessage);
        }
        SendMessage newMessage = new SendMessage(chatId, questions.get(0).getText());
        newMessage.setReplyMarkup(inlineKeyboardMaker.getInlineMessageButtons(" "));
        messages.add(newMessage);
        /*Question curQuestion = handlersAPI.GetNextQuestion();
        SendMessage sendMessage = new SendMessage(chatId, "");
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());

        if (curQuestion == null)
        {
            isTesting = false;
            sendMessage.setText("Тест пройден! Ваш балл:");
        }
        else
            sendMessage.setText(curQuestion.getText() + "\n" + curQuestion.getPossibleAnswers());

        return sendMessage;*/
        return newMessage;
    }

    private SendMessage getLevelMessages(String chatId){
        return null;
    }
}
