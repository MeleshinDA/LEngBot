package com.example.lengbot.dao;

import com.example.lengbot.models.Question;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuestionMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();

        question.setText(rs.getString("text"));
        question.setPossibleAnswers(rs.getString("possibleAnswers"));
        question.setRightAnswer(rs.getString("rightAnswer"));
        question.setWeight(rs.getInt("weight"));

        return question;
    }
}
