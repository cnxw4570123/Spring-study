package com.sparta.myselectshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.sparta.myselectshop.config.KakaoProperty;
import com.sparta.myselectshop.naver.config.NaverApiProperty;

@SpringBootApplication
@EnableConfigurationProperties({NaverApiProperty.class, KakaoProperty.class})
@EnableScheduling
public class MyselectshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyselectshopApplication.class, args);
	}

}
