package com.sparta.springauth;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import com.sparta.springauth.Vehicle.Vehicle;

@SpringBootTest
public class BeanTest {
	@Autowired
	@Qualifier("car")
	Vehicle vehicle;

	@Test
	@DisplayName("Primary와 Qualifier의 우선순위 확인")
	void test1() {
		vehicle.commute();
	}
}
