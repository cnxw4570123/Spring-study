package com.sparta.jdbc;

import java.util.Collections;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class DataRepository {
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	// 테이블 생성
	public void createTable() {
		getDefaultJdbcTemplate().execute("CREATE TABLE IF NOT EXISTS USERS (id SERIAL, name VARCHAR(255))");
	}

	// 데이터 추가
	public void insertUser(String name) {
		namedParameterJdbcTemplate.update("INSERT INTO USERS (name) VALUES(:name)", Collections.singletonMap("name", name));
	}

	public User findUserById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
			"SELECT * FROM USERS WHERE id = :id",
			new MapSqlParameterSource("id", id),
			new UserRowMapper()
		);
	}

	public void updateUser(Long id, String newName) {
		SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(User.class);
		namedParameterJdbcTemplate.update("UPDATE USERS SET name = :name WHERE id = :id", namedParameters);
	}

	public void deleteUser(Long id) {
		namedParameterJdbcTemplate.update("DELETE FROM USERS WHERE id = ?", new MapSqlParameterSource("id", id));
	}

	private JdbcTemplate getDefaultJdbcTemplate() {
		return namedParameterJdbcTemplate.getJdbcTemplate();
	}
}
