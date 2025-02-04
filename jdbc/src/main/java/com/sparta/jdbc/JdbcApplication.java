package com.sparta.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcApplication.class, args);

		String url = "jdbc:h2:mem:test";
		String username = "sa";

		try (Connection connection = DriverManager.getConnection(url, username, null)) {

			// 테이블 생성
			String createSql = "CREATE TABLE USERS (id SERIAL, username varchar(255))";
			try (PreparedStatement preparedStatement = connection.prepareStatement(createSql);) {
				preparedStatement.execute();
			}

			// 데이터 삽입
			String insertSql = "INSERT INTO USERS (username) VALUES ('raymond')";
			try (PreparedStatement preparedStatement = connection.prepareStatement(insertSql);) {
				preparedStatement.execute();
			}

			String selectSql = "SELECT * FROM USERS";
			try (PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
				var rs = preparedStatement.executeQuery();

				while (rs.next()) {
					System.out.printf("%d, %s", rs.getInt("id"), rs.getString("username"));
				}

			}

		} catch (SQLException e) {
			if (e.getMessage().contains("Table \"USERS\" already exists")) {
				System.out.println("USER 테이블이 이미 존재합니다.");
			}
		}
	}

}
