package com.example.lengbot.telegram;

import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.telegram.handlers.CallbackQueryHandler;
import com.example.lengbot.telegram.handlers.MessageHandler;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.IOException;


@Getter
@Setter
public class LEngBot extends SpringWebhookBot {
    private String botUsername;
    private String botToken;
    private String botPath;

    MessageHandler messageHandler;
    CallbackQueryHandler callbackQueryHandler;


    public LEngBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler) {
        super(setWebhook);
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try{
            return handleUpdate(update);
        } catch (IllegalArgumentException e){
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
        }
//        catch (Exception e){
//            return new SendMessage(update.getMessage().getChatId().toString(),
//                    BotMessageEnum.EXCEPTION_UNKNOWN.getMessage());
//        }
    }


    private BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()){
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else{
            Message message = update.getMessage();
            if(message != null)
                return messageHandler.answerMessage(update.getMessage());
        }
        return null;
    }
}
