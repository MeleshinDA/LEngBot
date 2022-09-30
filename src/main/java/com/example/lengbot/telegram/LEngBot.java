package com.example.lengbot.telegram;

import com.example.lengbot.API.UserTestService;
import com.example.lengbot.constants.BotMessageEnum;
import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.telegram.handlers.CallbackQueryHandler;
import com.example.lengbot.telegram.handlers.MessageHandler;
import com.example.lengbot.telegram.keyboards.ReplyKeyboardMaker;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.util.concurrent.ConcurrentHashMap;


@Getter
@Setter
public class LEngBot extends SpringWebhookBot {
    private String botUsername;
    private String botToken;
    private String botPath;
    private ConcurrentHashMap<Long, MessageHandler> usersHandlers;
    private CallbackQueryHandler callbackQueryHandler;
    private MessageHandler messageHandler;

    ReplyKeyboardMaker replyKeyboardMaker;
    UserDAO userDAO;
    QuestionDAO questionDAO;
    UserTestService userTestService;


    public LEngBot(SetWebhook setWebhook, CallbackQueryHandler callbackQueryHandler, MessageHandler messageHandler) {
        super(setWebhook);
        this.usersHandlers = new ConcurrentHashMap<>();
        this.callbackQueryHandler = callbackQueryHandler;
        this.messageHandler = messageHandler;
    }


    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        try {
            return handleUpdate(update);
        } catch (IllegalArgumentException e) {
            return new SendMessage(update.getMessage().getChatId().toString(),
                    BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());
        }
    }


    private BotApiMethod<?> handleUpdate(Update update) {
        long chatId = update.getMessage().getChatId();

        if (!usersHandlers.containsKey(chatId))
            usersHandlers.put(chatId, new MessageHandler(messageHandler));
        MessageHandler usersHandler = usersHandlers.get(chatId);
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null)
                return usersHandler.answerMessage(update.getMessage());
        }
        return null;
    }
}
