package com.sparta.springcloud.eureka.client.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

	@Value("${server.port}")
	private String port;

	@Value("${message}")
	private String message;

	@GetMapping("/product")
	public String getProduct() {
		return "info: from port = " + port + ", message = " + message;
	}
}
