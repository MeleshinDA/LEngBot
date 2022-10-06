package com.example.lengbot.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс работы баз данных для класса User
 */
@Repository
public class UserDAO {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public UserDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Метод внесения нового пользователя в базу данных
   *
   * @param chatId чат пользователя
   */
  public void saveUser(long chatId) {
    jdbcTemplate.update("INSERT INTO users(id, lvl) VALUES(?, ?)", chatId, "A0");
  }

  /**
   * Метод обновления данных пользователя в базе данных
   *
   * @param chatId идентификатор пользователя
   * @param lvl    уровень английского языка пользователя
   */
  public void updateUser(long chatId, String lvl) {
    jdbcTemplate.update("UPDATE users SET lvl=? WHERE id=?", lvl, chatId);
  }
}
