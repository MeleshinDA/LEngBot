package com.example.lengbot.appconfig;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация базы данных
 */
@Getter
@Configuration
public class DBConfig {
    @Value("${db.driverClassName}")
    private String driver;
    @Value("${db.url}")
    private String URL;
    @Value("${db.username}")
    private String userName;
    @Value("${db.password}")
    private String password;
}
