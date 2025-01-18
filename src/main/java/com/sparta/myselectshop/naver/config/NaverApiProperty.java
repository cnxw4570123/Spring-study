package com.sparta.myselectshop.naver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@ConfigurationProperties("naver.client")
@RequiredArgsConstructor
public class NaverApiProperty {
	private final String id;
	private final String secret;
}
