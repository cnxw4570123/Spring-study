package com.sparta.springauth.Vehicle;

public interface Vehicle {
	default void commute() {
		System.out.println(" 출근합니다.");
	}
}
