package com.example.lengbot.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Модель вопроса
 */
@Getter
@Setter
public class Question {
    /**
     * Текст вопроса
     */
    private String text;
    /**
     * Возможные варианты ответа
     */
    private String possibleAnswers;
    /**
     * Правильный ответ
     */
    private String rightAnswer;
    /**
     * Цена вопроса
     */
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
