package com.sparta.myselectshop.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductMypriceRequestDto {
	public static final int MIN_MY_PRICE = 100;
	@Min(value = MIN_MY_PRICE, message = "유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + "원 이상으로 설정해 주세요.")
	private int myprice;
}