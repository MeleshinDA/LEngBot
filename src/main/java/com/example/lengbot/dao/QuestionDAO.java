package com.example.lengbot.dao;

import com.example.lengbot.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Question> GetTest()
    {
        return jdbcTemplate.query("SELECT * FROM test", new QuestionMapper());
    }
}