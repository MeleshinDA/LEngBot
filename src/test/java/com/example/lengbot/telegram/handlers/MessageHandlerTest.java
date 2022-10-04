package com.example.lengbot.telegram.handlers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MessageHandlerTest {

    @Test
    void answerMessage_DEFAULT() {
        MessageHandler messageHandler = new MessageHandler();
        Message message = new Message();
        message.setText("Hello TEST");
        message.setChat(new Chat(21L, "255"));

        String expected = "Пожалуйста воспользуйтесь клавиатурой";
        String actual = ((SendMessage) messageHandler.answerMessage(message)).getText();

        assertEquals(expected, actual);
    }

    @Test
    void answerMessage_DEFAULTWithTestingMessage() {
        MessageHandler messageHandler = new MessageHandler();
        Message message = new Message();
        message.setText("Пройти тест");
        message.setChat(new Chat(21L, "255"));

        String expected = """
                Решите следующие задания:
                Переведите слово: Sunrise
                a. Озеро, b. Река, c. Восход, d. Страна""";
        String actual = ((SendMessage) messageHandler.answerMessage(message)).getText();

        assertEquals(expected, actual);
    }

    @Test
    void answerMessage_DEFAULTWithEnteringLvlMessage() {
        MessageHandler messageHandler = new MessageHandler();
        Message message = new Message();
        message.setText("Ввести уровень");
        message.setChat(new Chat(21L, "255"));

        String expected = "Введите Ваш уровень. Доступны: A0, A1, A2, B1, B2, C1, C2.";
        String actual = ((SendMessage) messageHandler.answerMessage(message)).getText();

        assertEquals(expected, actual);
    }

    @Test
    void answerMessage_DEFAULTWithHelpMessage() {
        MessageHandler messageHandler = new MessageHandler();
        Message message = new Message();
        message.setText("Помощь");
        message.setChat(new Chat(21L, "255"));

        String expected = """
                Бот для изучения английского языка.
                Доступны следующие команды:
                Пройти тест - бот последовательно высылает задания, в конце результат.
                Ввести уровень - введите уровень языка. Доступны: A0, A1, A2, B1, B2, C1, C2""";
        String actual = ((SendMessage) messageHandler.answerMessage(message)).getText();

        assertEquals(expected, actual);
    }
}