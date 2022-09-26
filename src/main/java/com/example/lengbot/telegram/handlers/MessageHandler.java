package com.example.lengbot.telegram.handlers;

import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.constants.ButtonNameEnum;
import com.example.lengbot.telegram.TelegramApiClient;
import com.example.lengbot.telegram.keyboards.InlineKeyboardMaker;
import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


@Component
@RequiredArgsConstructor
public class MessageHandler {


    TelegramApiClient telegramApiClient;
    ReplyKeyboardMaker replyKeyboardMaker;
    InlineKeyboardMaker inlineKeyboardMaker;

    public BotApiMethod<?> answerMessage(Message message){
        String chatId = message.getChatId().toString();
        String inputText = message.getText();

        return switch (inputText){
            case null -> throw new IllegalArgumentException();
            case "/start" -> getStartMessage(chatId);
            case "Пройти тест" -> getTestMessages(chatId);
            case "Ввести уровень" -> getLevelMessages(chatId);
            default -> new SendMessage(chatId, "Пожалуйста воспользуйтесь клавиатурой");
        };
    }

    private SendMessage getStartMessage(String chatId){
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboardMaker.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getTestMessages(String chatId){
        return null;
    }

    private SendMessage getLevelMessages(String chatId){
        return null;
    }
}
