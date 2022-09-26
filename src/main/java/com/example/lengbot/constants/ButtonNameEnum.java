package com.example.lengbot.constants;

public enum ButtonNameEnum {
    GET_TEST_BUTTON("Пройти тест"),
    ENTER_LEVEL_BUTTON("Ввести уровень"),
    HELP_BUTTON("Помощь");

    private final String buttonName;

    ButtonNameEnum(String buttonName){
        this.buttonName = buttonName;
    }

    public String getButtonName(){
        return buttonName;
    }
}
