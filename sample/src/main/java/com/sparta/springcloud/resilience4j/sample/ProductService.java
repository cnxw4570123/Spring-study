package com.sparta.springcloud.resilience4j.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final Logger logger = LoggerFactory.getLogger(ProductService.class);

	public Product getProductDetail(String productId) {
		if ("111".equals(productId)) {
			throw new RuntimeException("empty response body");
		}
		return new Product(
			productId,
			"sample Product : " + productId
		);
	}
}
