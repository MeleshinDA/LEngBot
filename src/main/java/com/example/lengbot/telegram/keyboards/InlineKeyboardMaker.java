package com.example.lengbot.telegram.keyboards;

import com.example.lengbot.constants.InlineAnswersEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@Component
public class InlineKeyboardMaker {

    public InlineKeyboardMarkup getInlineMessageButtons(String prefix){
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for(InlineAnswersEnum answers : InlineAnswersEnum.values()){
            rowList.add(getButton(answers.getButtonName(), prefix+answers.name()));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtons = new ArrayList<>();
        keyboardButtons.add(button);
        return keyboardButtons;
    }

}
