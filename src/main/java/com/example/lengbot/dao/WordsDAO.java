package com.example.lengbot.dao;

import com.example.lengbot.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WordsDAO {

  private final JdbcTemplate jdbcTemplate;
  private final UserDAO userDAO;

  @Autowired
  public WordsDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO) {
    this.jdbcTemplate = jdbcTemplate;
    this.userDAO = userDAO;
  }

  public List<String> getNewWordsFromDb(long chatId) {
    User curUser = userDAO.getUser(chatId);
    // Вычитаем 1 потому что Between - включительное нер-во
    var prevIndex = curUser.getCurWordsIndex();
    var curIndex = checkWordsIndex(chatId, curUser) - 1;

    String sql = String.format("SELECT word FROM %s WHERE id BETWEEN %d AND %d", curUser.getLvl(),
        prevIndex, curIndex);

    return jdbcTemplate.queryForList(sql, String.class);
  }

  private int checkWordsIndex(long chatId, User curUser) {
    String wordsSql = String.format("SELECT count(*) FROM %s", curUser.getLvl());
    int wordsCount = jdbcTemplate.queryForObject(wordsSql, Integer.class);
    int wordsCountToSend = 5;
    int nextWordsIndex = curUser.getCurWordsIndex() + wordsCountToSend;

    if (nextWordsIndex > wordsCount) {
      userDAO.updateUserIndex(chatId, 0);
    } else {
      userDAO.updateUserIndex(chatId, nextWordsIndex);
    }

    return nextWordsIndex;
  }
}
