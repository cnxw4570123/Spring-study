package com.sparta.jdbc;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		var user = new User();
		user.setId(rs.getLong("id"));
		user.setName(rs.getString("name"));
		return user;
	}
}
