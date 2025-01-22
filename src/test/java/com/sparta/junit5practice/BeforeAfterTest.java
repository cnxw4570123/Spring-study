package com.sparta.junit5practice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeforeAfterTest {
	private static final Logger log = LoggerFactory.getLogger(BeforeAfterTest.class);

	@BeforeEach
	void setUp() {
		log.info("각각의 테스트 코드가 실행되기 전에 실행");
	}

	@AfterEach
	void tearDown() {
		log.info("각각의 테스트 코드가 실행된 후에 실행\n");
	}

	@BeforeAll
	static void beforeAll() {
		log.info("모든 테스트 코드가 실행되기 전 최초로 수행\n");
	}

	@AfterAll
	static void afterAll() {
		log.info("모든 테스트 코드가 실행된 후에 최초로 수행\n");
	}

	@Test
	void test1() {
		log.info("테스트 코드 1 작성");
	}

	@Test
	void test2() {
		log.info("테스트 코드 2 작성");
	}
}
