package com.sparta.springauth.Vehicle;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Metro implements Vehicle {
	@Override
	public void commute() {
		System.out.print("전철을 타고");
		Vehicle.super.commute();
	}
}

