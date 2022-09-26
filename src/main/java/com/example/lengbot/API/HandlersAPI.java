package com.example.lengbot.API;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;

import java.util.Iterator;
import java.util.List;

public class HandlersAPI {
    public UserDAO userDAO;
    private QuestionDAO questionDAO;
    private Iterator<Question> questionItr = questionDAO.GetTest().listIterator();
    public Question GetNextQuestion()
    {
        if(questionItr.hasNext()) {
            return questionItr.next();
        }
        return null;
    }
}
