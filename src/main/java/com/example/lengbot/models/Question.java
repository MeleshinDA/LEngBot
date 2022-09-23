package com.example.lengbot.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Question {
    private String text;
    private String possibleAnswers;
    private String rightAnswer;
    private int weight;

    public Question() {

    }

    public Question(String text, String possibleAnswers, String rightAnswer, int weight) {
        this.text = text;
        this.possibleAnswers = possibleAnswers;
        this.rightAnswer = rightAnswer;
        this.weight = weight;
    }

}
