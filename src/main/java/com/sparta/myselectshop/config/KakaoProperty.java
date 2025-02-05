package com.sparta.myselectshop.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@ConfigurationProperties(value = "kakao")
@RequiredArgsConstructor
@Getter
public class KakaoProperty {
	private final String REDIRECT_URI;
	private final String RESTAPI_KEY;
}
