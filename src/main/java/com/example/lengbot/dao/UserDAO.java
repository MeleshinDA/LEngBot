package com.example.lengbot.dao;


import com.example.lengbot.models.User;
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
  public void saveUser(long chatId) { // user в WordsDAO не инициализируется, где-то
    try
    {
      jdbcTemplate.update("INSERT INTO users(id, lvl, curWordsIndex) VALUES(?, ?, ?)", chatId, "A0",
          0);
    } catch (Exception e) {}
  }

  /**
   * Метод обновления данных пользователя в базе данных
   *
   * @param chatId идентификатор пользователя
   * @param lvl    уровень английского языка пользователя
   */
  public void updateUserLvl(long chatId, String lvl) {
    jdbcTemplate.update("UPDATE users SET lvl=?, curWordsIndex=? WHERE id=?", lvl, 0, chatId);
  }

  public void updateUserIndex(long chatId, int curWordsIndex) {
    jdbcTemplate.update("UPDATE users SET curWordsIndex=? WHERE id=?", curWordsIndex, chatId);
  }

  public User getUser(long chatId) {
    return jdbcTemplate.queryForObject("SELECT * FROM users WHERE id=?", new Object[]{chatId},
        new UserMapper());
  }

}
