package com.example.lengbot.dao;

import com.example.lengbot.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс работы баз данных для класса Question
 */
@Repository
public class QuestionDAO {

  private static JdbcTemplate jdbcTemplate;

  @Autowired
  public QuestionDAO(JdbcTemplate jdbcTemplate) {
    QuestionDAO.jdbcTemplate = jdbcTemplate;
  }

  /**
   * Метод получения теста из базы данных
   *
   * @return Готовый тест (список вопросов)
   */
  public static List<Question> getTest() {
    return jdbcTemplate.query("SELECT * FROM test", new QuestionMapper());
  }
}