package com.example.lengbot.appconfig;

import com.example.lengbot.LEngBot;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
