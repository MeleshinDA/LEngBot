package com.example.lengbot.constants;

/**
 * Перечисление вожможных ответов бота
 */
public enum BotMessageEnum {
    HELP_MESSAGE("Бот для изучения английского языка.\n" +
            "Доступны следующие команды:\n" +
            "Пройти тест - бот последовательно высылает задания, в конце результат.\n" +
            "Ввести уровень - введите уровень языка. Доступны: A0, A1, A2, B1, B2, C1, C2"),
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
