package com.sparta.springcloud.eureka.client.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("product-service")
public interface ProductClient {

	@GetMapping("/product/{id}")
	String getProductInfo(@PathVariable("id") String id);
}
