package com.example.lengbot.telegram;

import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramApiClient {
    private final String URL;
    private final String botToken;

    private final RestTemplate restTemplate;

    public TelegramApiClient(@Value("${telegram.api-url}") String URL,
                             @Value("${telegram.bot-token}") String botToken){
        this.URL = URL;
        this.botToken = botToken;
        this.restTemplate = new RestTemplate();
    }


}
