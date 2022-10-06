package com.example.lengbot.appconfig;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import com.example.lengbot.telegram.LEngBot;
import com.example.lengbot.telegram.handlers.CallbackQueryHandler;
import com.example.lengbot.telegram.handlers.MessageHandler;
import com.example.lengbot.telegram.handlers.MessageHandlerFactory;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

import javax.sql.DataSource;


/**
 * Конфигурация приложения
 */
@Configuration
@AllArgsConstructor
public class SpringConfig {

  private final BotConfig botConfig;

  private final DBConfig dbConfig;

  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setDriverClassName(dbConfig.getDriver());
    driverManagerDataSource.setUrl(dbConfig.getURL());
    driverManagerDataSource.setUsername(dbConfig.getUserName());
    driverManagerDataSource.setPassword(dbConfig.getPassword());
    return driverManagerDataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate() {
    return new JdbcTemplate(dataSource());
  }

  @Bean
  public SetWebhook setWebhook() {
    return SetWebhook.builder().url(botConfig.getWebHookPath()).build();
  }


  @Bean
  public LEngBot lEngBot(SetWebhook setWebhook, CallbackQueryHandler callbackQueryHandler,
      MessageHandlerFactory messageHandlerFactory) {
    LEngBot lEngBot = new LEngBot(setWebhook, callbackQueryHandler, messageHandlerFactory);

    lEngBot.setBotPath(botConfig.getWebHookPath());
    lEngBot.setBotUsername(botConfig.getBotUsername());
    lEngBot.setBotToken(botConfig.getBotToken());
    return lEngBot;
  }
}
