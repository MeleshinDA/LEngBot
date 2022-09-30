package com.example.lengbot.API;

import com.example.lengbot.models.Question;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Getter
@Scope("prototype")
public class UserTestService {

    private List<Question> test;

    private int scores;

    private int curQuestionIndex;
    private Question curQuestion;

    public UserTestService() {
    }

    public UserTestService(List<Question> test) {
        this.test = new ArrayList<>(test);
    }

    public Question NextQuestion() {
        if (curQuestionIndex <= test.size() - 1) {
            curQuestion = test.get(curQuestionIndex++);
            return curQuestion;
        } else
            return null;
    }

    public void CheckAnswer(String answer) {
        if (curQuestion.getRightAnswer().equals(answer))
            scores += curQuestion.getWeight();
    }

    public Boolean CheckUserLvl(String lvl) {
        Set<String> rightLvls = new HashSet<>();
        rightLvls.add("A0");
        rightLvls.add("A1");
        rightLvls.add("A2");
        rightLvls.add("B1");
        rightLvls.add("B2");

        return rightLvls.contains(lvl.toUpperCase());
    }

    public void resetTest() {
        curQuestion = null;
        curQuestionIndex = 0;
        scores = 0;
    }
}
