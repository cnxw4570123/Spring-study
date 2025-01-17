package com.sparta.springauth.Vehicle;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("car")
public class Car implements Vehicle {
	@Override
	public void commute() {
		System.out.print("차를 운전해서");
		Vehicle.super.commute();
	}
}