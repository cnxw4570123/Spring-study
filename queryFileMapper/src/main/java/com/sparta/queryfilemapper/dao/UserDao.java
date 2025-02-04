package com.sparta.queryfilemapper.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import com.sparta.queryfilemapper.domain.User;

@Component
public class UserDao {
	private final SqlSession sqlSession;

	public UserDao(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public User selectUserById(long id) {
		return sqlSession.selectOne("selectUserById", id);
	}
}
