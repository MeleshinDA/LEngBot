package com.example.lengbot.dao;


import com.example.lengbot.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
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
}
