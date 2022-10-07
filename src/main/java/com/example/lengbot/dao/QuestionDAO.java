package com.example.lengbot.dao;

import com.example.lengbot.models.Question;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс работы баз данных для класса Question
 */
@Repository
public class QuestionDAO {

  private final Map<String, Integer> dbQuestionTypesToCount;
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public QuestionDAO(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;

    this.dbQuestionTypesToCount = new LinkedHashMap<>();
    this.dbQuestionTypesToCount.put("first", 8);
    this.dbQuestionTypesToCount.put("second", 5);
    this.dbQuestionTypesToCount.put("third", 2);
  }

  /**
   * Метод получения теста из базы данных
   *
   * @return Готовый тест (список вопросов)
   */
  public List<Question> getTest() {
    List<Question> test = new ArrayList<>();

    for (String tableName : dbQuestionTypesToCount.keySet()) {
      test.addAll(jdbcTemplate.query("SELECT * FROM ? ORDER BY RANDOM() LIMIT ?",
          new Object[]{tableName, dbQuestionTypesToCount.get(tableName)},
          new QuestionMapper()));
    }

    return test;
  }
}