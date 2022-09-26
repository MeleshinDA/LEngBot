package com.example.lengbot.constants;

public enum BotMessageEnum {
    HELP_MESSAGE("Помощь"),
    NON_COMMAND_MESSAGE("Воспользуйтесь клавиатурой"),

    EXCEPTION_ILLEGAL_MESSAGE(" работаю только с текстом");

    private final String message;

    BotMessageEnum(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
