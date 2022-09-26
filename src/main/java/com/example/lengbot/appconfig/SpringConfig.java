package com.example.lengbot.appconfig;

import com.example.lengbot.telegram.LEngBot;
import com.example.lengbot.telegram.handlers.CallbackQueryHandler;
import com.example.lengbot.telegram.handlers.MessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;



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
    public LEngBot springWebHookBot(SetWebhook setWebhook, MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler)
    {
        LEngBot lEngBot = new LEngBot(setWebhook, messageHandler, callbackQueryHandler);

        lEngBot.setBotPath(botConfig.getWebHookPath());
        lEngBot.setBotUsername(botConfig.getBotUsername());
        lEngBot.setBotToken(botConfig.getBotToken());
        return lEngBot;
    }
}
