package com.example.lengbot.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс работы баз данных для класса User
 */
@Repository
public class UserDAO {
    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        UserDAO.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Метод внесения нового пользователя в базу данных
     * @param chatId чат пользователя
     */
        public static void SaveUser(long chatId)
    {
        jdbcTemplate.update("INSERT INTO users(id, lvl) VALUES(?, ?)", chatId, "A0");
    }

    /**
     * Метод обновления данных пользователя в базе данных
     * @param chatId идентификатор пользователя
     * @param lvl уровень английского языка пользователя
     */
    public static void UpdateUser(long chatId, String lvl)
    {
        jdbcTemplate.update("UPDATE users SET lvl=? WHERE id=?", lvl, chatId);
    }
}
