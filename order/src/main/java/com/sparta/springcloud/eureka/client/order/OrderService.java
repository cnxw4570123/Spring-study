package com.sparta.springcloud.eureka.client.order;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final ProductClient productClient;

	public String getProductInfo(String productId) {
		return productClient.getProductInfo(productId);
	}

	public String getOrder(String orderId) {
		if (orderId.equals("1")) {
			String productId = "2";
			return "Your order is..." + orderId + ", product is..." + getProductInfo(productId);
		}
		return "Not exist order...";
	}
}
