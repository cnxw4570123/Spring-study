package com.sparta.queryfilemapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sparta.queryfilemapper.dao.UserDao;
import com.sparta.queryfilemapper.mapper.UserMapper;

@SpringBootApplication
public class QueryFileMapperApplication {

	private static final Logger log = LoggerFactory.getLogger(QueryFileMapperApplication.class);

	public static void main(String[] args) {
		var context = SpringApplication.run(QueryFileMapperApplication.class, args);

		// dao 클래스 사용
		var dao = context.getBean(UserDao.class);
		log.info("User by dao : {}", dao.selectUserById(1L));

		// Mapper 인터페이스 사용
		var mapper = context.getBean(UserMapper.class);
		log.info("User by fileMapper: {}", mapper.selectUserById(1L));
	}

}
