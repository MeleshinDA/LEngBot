package com.example.lengbot.API;

import com.example.lengbot.dao.QuestionDAO;
import com.example.lengbot.dao.UserDAO;
import com.example.lengbot.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class HandlersAPI {
    public UserDAO userDAO;
    private Iterator<Question> questionItr;// = questionDAO.GetTest().listIterator();

    public HandlersAPI(){}

    @Autowired
    public HandlersAPI(UserDAO userDAO, QuestionDAO questionDAO){
        this.userDAO = userDAO;
        this.questionItr = questionDAO.GetTest().listIterator();
    }
    public Question GetNextQuestion()
    {
        if(questionItr.hasNext()) {
            return questionItr.next();
        }
        return null;
    }
}
