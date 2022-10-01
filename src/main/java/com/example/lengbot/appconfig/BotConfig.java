package com.example.lengbot.appconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация бота
 */
@Getter
@Configuration
public class BotConfig {
    @Value("${telegrambot.botUsername}")
    private String botUsername;
    @Value("${telegrambot.botToken}")
    private String botToken;
    @Value("${telegrambot.webHookPath}")
    private String webHookPath;

}
