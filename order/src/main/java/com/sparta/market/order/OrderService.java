package com.sparta.market.order;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	@Value("${message.queue.product}")
	private String productQueue;

	@Value("${message.queue.payment}")
	private String paymentQueue;

	private final RabbitTemplate rabbitTemplate; // RabbitMQ 사용 간편하게 해주는 기능

	public void createOrder(String orderId) {
		rabbitTemplate.convertAndSend(productQueue, orderId);
		rabbitTemplate.convertAndSend(paymentQueue, orderId);

	}
}
