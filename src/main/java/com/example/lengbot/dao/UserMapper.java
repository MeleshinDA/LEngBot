package com.example.lengbot.dao;

import com.example.lengbot.models.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {

  @Override
  public User mapRow(ResultSet rs, int rowNum) throws SQLException {
    User user = new User();

    user.setLvl(rs.getString("lvl"));
    user.setCurWordsIndex(rs.getInt("curWordsIndex"));
    user.setId(String.valueOf(rs.getInt("id")));

    return user;
  }
}
