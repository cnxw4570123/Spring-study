package com.sparta.rawjpa.manual;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@Import(ManualRepositoryRegistrar.class)
@SpringBootTest
public class ManualRepositoryTest {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	ManualRepository manualRepository;

	@Test
	@DisplayName("빈 등록 테스트")
	void test() {
		assertTrue(applicationContext.containsBean("manualRepository"), "manualRepository 빈이 등록되지 않음.");
	}

	@Test
	@DisplayName("리포지토리 수동 등록 테스트")
	void test1() {
		// given
		String data = "newData";
		Long saved = manualRepository.save(data);

		// when
		String findData = manualRepository.find(saved);

		// then
		assertEquals(data, findData);
	}

}
