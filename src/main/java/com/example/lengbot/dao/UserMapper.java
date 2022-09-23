package com.example.lengbot.dao;

import com.example.lengbot.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getString("id"));
        user.setLvl(rs.getString("lvl"));

        return user;
    }
}
