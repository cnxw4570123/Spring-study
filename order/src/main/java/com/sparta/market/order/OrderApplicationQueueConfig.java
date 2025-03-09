package com.sparta.market.order;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderApplicationQueueConfig {

	@Value("${message.exchange}")
	private String exchange;

	@Value("${message.queue.product}")
	private String product;
	@Value("${message.queue.payment}")
	private String payment;

	@Bean
	public TopicExchange exchange() {
		return new TopicExchange(exchange);
	}

	@Bean
	public Queue queueProduct() {
		return new Queue(product);
	}

	@Bean
	public Queue queuePayment() {
		return new Queue(payment);
	}

	@Bean
	public Binding bindingProduct() {
		return BindingBuilder.bind(queueProduct()) // product 큐로
			.to(exchange()) // 익스체인지에서
			.with(product); // 라우팅 키가 market.product일 때
	}

	@Bean
	public Binding bindingPayment() {
		return BindingBuilder.bind(queuePayment()) // payment 큐로
			.to(exchange()) // 익스체인지에서
			.with(payment); // 라우팅 키가 market.payment일 때
	}
}