package com.example.lengbot.API;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.models.Question;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTestService {

    private List<Question> test;

    private int score;

    private int curQuestionIndex;

    public UserTestService(){}

    public UserTestService(QuestionDAO questionDAO, int score, int curQuestionIndex) {
        this.test = new ArrayList<>(questionDAO.GetTest());
        this.score = score;
        this.curQuestionIndex = curQuestionIndex;
    }

    public Question GetNextQuestion()
    {
        if(questionItr.hasNext()) {
            return questionItr.next();
        }
        return null;
    }
}
