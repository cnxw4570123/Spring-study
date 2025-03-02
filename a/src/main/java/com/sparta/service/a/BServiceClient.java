package com.sparta.service.a;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "service-b", url = "${service.b.url}")
public interface BServiceClient {

	@GetMapping("/hello")
	public String getHello();
}
