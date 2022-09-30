package com.example.lengbot.dao;


import com.example.lengbot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User GetUser(long id)
    {
        return jdbcTemplate.queryForObject("SELECT id, lvl FROM user WHERE id=?", new Object[]{ id }, new UserMapper());
    }

    public void SaveUser(long chatId)
    {
        jdbcTemplate.update("INSERT INTO users(id, lvl) VALUES(?, ?)", chatId, "A0");
    }

    public void UpdateUser(long chatId, String lvl)
    {
        jdbcTemplate.update("UPDATE users SET lvl=? WHERE id=?", lvl, chatId);
    }
}
