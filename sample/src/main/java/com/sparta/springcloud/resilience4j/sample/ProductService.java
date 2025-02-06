package com.sparta.springcloud.resilience4j.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final Logger log = LoggerFactory.getLogger(ProductService.class);
	private final CircuitBreakerRegistry circuitBreakerRegistry;

	@PostConstruct
	public void registerEventListener() {
		circuitBreakerRegistry.circuitBreaker("productService")
			.getEventPublisher()
			.onStateTransition(event -> log.info("######CircuitBreaker State Transition = {}", event))
			// 호출 차단 이벤트 리스너
			.onFailureRateExceeded(event -> log.info("#####CircuitBreaker Failure Rated Exceed = {}", event))
			.onCallNotPermitted(event -> log.info("#####CircuitBreaker Call Not Permitted = {}", event))
			.onError(event -> log.info("#####CircuitBreaker Error = {}", event));
	}

	@CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetProductDetails")
	public Product getProductDetail(String productId) {
		if ("111".equals(productId)) {
			throw new RuntimeException("empty response body");
		}
		return new Product(
			productId,
			"sample Product : " + productId
		);
	}

	public Product fallbackGetProductDetails(String productId, Throwable t) {
		log.error("#####Fallback triggered for productId : {} due to {}", productId, t.getMessage());
		return new Product(
			productId,
			"Fallback Product"
		);
	}
}
