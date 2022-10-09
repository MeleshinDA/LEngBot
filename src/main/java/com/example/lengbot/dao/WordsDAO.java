package com.example.lengbot.dao;

import com.example.lengbot.models.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WordsDAO {

  private final int wordsCountToSend = 3;
  private final JdbcTemplate jdbcTemplate;
  private final UserDAO userDAO;

  @Autowired
  public WordsDAO(JdbcTemplate jdbcTemplate, UserDAO userDAO) {
    this.jdbcTemplate = jdbcTemplate;
    this.userDAO = userDAO;

  }

  public List<String> getNewWordsFromDb(long chatId) {
    User curUser = userDAO.getUser(chatId);

    int prevIndex = curUser.getCurWordsIndex();
    curUser.setCurWordsIndex(prevIndex + wordsCountToSend);

    // Вычитаем 1 потому что Between - включительное нер-во
    String sql = String.format("SELECT word FROM %s WHERE id BETWEEN %d AND %d", curUser.getLvl(),
        prevIndex, curUser.getCurWordsIndex() - 1);

    List<String> res = jdbcTemplate.queryForList(sql, String.class);

    updateUserIndex(chatId, res.size(), curUser.getCurWordsIndex());

    return res;

  }

  private void updateUserIndex(long chatId, int size, int nextWordsIndex) {
    if (size < wordsCountToSend) {
      userDAO.updateUserIndex(chatId, 0);
    } else {
      userDAO.updateUserIndex(chatId, nextWordsIndex);
    }
  }
}
