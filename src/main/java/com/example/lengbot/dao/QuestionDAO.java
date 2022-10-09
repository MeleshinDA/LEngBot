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
    this.dbQuestionTypesToCount.put("firsttypequestions", 8);
    this.dbQuestionTypesToCount.put("secondtypequestions", 5);
    this.dbQuestionTypesToCount.put("thirdtypequestions", 2);
  }

  /**
   * Метод получения теста из базы данных
   *
   * @return Готовый тест (список вопросов)
   */
  public List<Question> getTest() {
    List<Question> test = new ArrayList<>();

    for (String tableName : dbQuestionTypesToCount.keySet()) {
      String sql = String.format("SELECT * FROM %s ORDER BY RANDOM() LIMIT %d", tableName,
          dbQuestionTypesToCount.get(tableName));
      test.addAll(jdbcTemplate.query(sql,
          new QuestionMapper()));
    }

    return test;
  }
}