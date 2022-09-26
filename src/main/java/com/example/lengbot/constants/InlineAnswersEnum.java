package com.example.lengbot.constants;

import lombok.Getter;

public enum InlineAnswersEnum {
    A("a"),
    B("b"),
    C("c"),
    D("d");

    @Getter
    private final String buttonName;

    InlineAnswersEnum(String buttonName){
        this.buttonName = buttonName;
    }
}
