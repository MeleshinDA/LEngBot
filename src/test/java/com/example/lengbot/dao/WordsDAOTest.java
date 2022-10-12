package com.example.lengbot.dao;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;


@SpringBootTest
class WordsDAOTest {

  @Autowired
  private WordsDAO wordsDAO;

  @Autowired
  private UserDAO userDAO;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Test
  void getNewWords_OnOneUseReturnsThreeWords() {
    var testChatId = 777777777;
    userDAO.updateUserLvl(testChatId, "tabletestlvl");
    List<String> words = wordsDAO.getNewWordsFromDb(testChatId);
    assertEquals(3, words.size());
  }

  @Test
  void getNewWords_OnRepeatedUseReturnsAllWords() {
    var testChatId = 777777777;
    String wordsSql = String.format("SELECT count(*) FROM %s", "tabletestlvl");
    int wordsCount = jdbcTemplate.queryForObject(wordsSql, Integer.class);

    userDAO.updateUserLvl(testChatId, "tabletestlvl");

    List<String> words = new ArrayList<>();
    for (var i = 0; i < Math.ceil(wordsCount/3.0); i++)
    {
      var wordsToAdd = (wordsDAO.getNewWordsFromDb(testChatId));
      words.addAll(wordsToAdd);
    }

    assertEquals(wordsCount, words.size());

  }

  @Test
  void getNewWords_OnRepeatedUseWhenWordsHaveEndedReturnsFromStart() {
    var testChatId = 777777777;
    String wordsSql = String.format("SELECT count(*) FROM %s", "tabletestlvl");
    int wordsCount = jdbcTemplate.queryForObject(wordsSql, Integer.class);
    int itersCount = (int)Math.ceil(wordsCount/3.0) + 1;
    userDAO.updateUserLvl(testChatId, "tabletestlvl");

    List<String> words = new ArrayList<>();
    for (var i = 0; i < itersCount; i++)
    {
      var wordsToAdd = (wordsDAO.getNewWordsFromDb(testChatId));
      words.addAll(wordsToAdd);
    }

    wordsCount += 3;
    assertEquals(wordsCount, words.size());
  }
}