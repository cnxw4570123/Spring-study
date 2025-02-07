package com.sparta.springcloud.eureka.client.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Value("${server.port}")
	private String port;

	@GetMapping("/product/{id}")
	public String getProduct(@PathVariable("id") String id) {
		return "Product " + id + " info: from port = " + port;
	}
}
