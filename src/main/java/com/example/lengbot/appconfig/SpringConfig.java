package com.example.lengbot.appconfig;

import com.example.lengbot.LEngBot;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import javax.websocket.MessageHandler;

@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final BotConfig botConfig;

    @Bean
    public SetWebhook setWebhook()
    {
        return SetWebhook
                .builder()
                .url(botConfig.getWebHookPath())
                .build();
    }

    @Bean
    public LEngBot springWebHookBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQuery callbackQuery)
    {
        LEngBot lEngBot = new LEngBot(setWebhook, messageHandler, callbackQuery);
        lEngBot.setWebhook(botConfig.getWebHookPath());
        lEngBot.setBotUserName(botConfig.getBotUsername());
        lEngBot.setBotToken(botConfig.getBotToken());
        return lEngBot;
    }
}
