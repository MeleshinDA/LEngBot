package com.example.LEngBot.appconfig;

import com.example.LEngBot.LEngBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "telegrambot")
public class BotConfig {
    private String botUsername;
    private String botToken;
    private String webHookPath;


    @Bean
    public LEngBot lEngBot() {
        return new LEngBot(botUsername, botToken, webHookPath);
    }
}
