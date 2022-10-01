package com.example.lengbot.constants;

/**
 * Перечисление вожможных ответов бота
 */
public enum BotMessageEnum {
    HELP_MESSAGE("Помощь"),
    NON_COMMAND_MESSAGE("Пожалуйста воспользуйтесь клавиатурой"),
    EXCEPTION_ILLEGAL_MESSAGE(" работаю только с текстом"),
    EXCEPTION_UNKNOWN("Возникла ошибка");

    private final String message;

    BotMessageEnum(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
