package com.example.lengbot.telegram.keyboards;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Создание главной клавиатуры бота
 */
@Component
public class ReplyKeyboardMaker {
  public ReplyKeyboardMaker(){}

  public ReplyKeyboardMarkup getMainMenuKeyboard() {
    KeyboardRow keyboardButtons = new KeyboardRow();
    keyboardButtons.add(new KeyboardButton("Пройти тест"));
    keyboardButtons.add(new KeyboardButton("Ввести уровень"));
    keyboardButtons.add(new KeyboardButton("Помощь"));

    List<KeyboardRow> keyboard = new ArrayList<>();
    keyboard.add(keyboardButtons);

    final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    replyKeyboardMarkup.setKeyboard(keyboard);
    replyKeyboardMarkup.setSelective(true);
    replyKeyboardMarkup.setResizeKeyboard(true);
    replyKeyboardMarkup.setOneTimeKeyboard(false);

    return replyKeyboardMarkup;
  }
}
