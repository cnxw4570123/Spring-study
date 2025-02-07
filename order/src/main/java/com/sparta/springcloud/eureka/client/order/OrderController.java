package com.sparta.springcloud.eureka.client.order;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class OrderController {
	private final OrderService orderService;

	@GetMapping("/order/{orderId}")
	public String getProductInfo(@PathVariable("orderId") String orderId) {
		return orderService.getOrder(orderId);
	}
}
