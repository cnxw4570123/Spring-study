package com.sparta.springresttemplateclient.naver.service;

import com.sparta.springresttemplateclient.naver.dto.ItemDto;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "NAVER API")
@Service
public class NaverApiService {
	private final String clientId;
	private final String clientSecret;

	private final RestTemplate restTemplate;

	public NaverApiService(RestTemplateBuilder builder, @Value("${naver.client.id}") String clientId,
		@Value("${naver.client.secret}") String clientSecret) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.restTemplate = builder.build();
	}

	public List<ItemDto> searchItems(String query) {
		// 요청 URL 만들기
		URI uri = UriComponentsBuilder
			.fromUriString("https://openapi.naver.com")
			.path("/v1/search/shop.json")
			.queryParam("display", 15)
			.queryParam("query", query)
			.encode()
			.build()
			.toUri();
		log.info("uri = {}", uri);

		RestClient build = RestClient.builder()
			.requestFactory(new HttpComponentsClientHttpRequestFactory())
			.baseUrl(uri)
			.build();

		return build.get()
			.headers(httpHeaders -> {
				httpHeaders.set("X-Naver-Client-Id", clientId);
				httpHeaders.set("X-Naver-Client-Secret", clientSecret);
			}).exchange((req, res) -> {
				if (res.getStatusCode().is4xxClientError()) {
					throw new RuntimeException(res.getStatusCode() + " " + res.getHeaders());
				}
				String entity = res.bodyTo(String.class);
				log.info("NAVER API Status Code : {}", res.getStatusCode());
				return fromJSONtoItems(entity);
			});

	}

	public List<ItemDto> fromJSONtoItems(String responseEntity) {
		JSONObject jsonObject = new JSONObject(responseEntity);
		JSONArray items = jsonObject.getJSONArray("items");
		List<ItemDto> itemDtoList = new ArrayList<>();

		for (Object item : items) {
			ItemDto itemDto = new ItemDto((JSONObject)item);
			itemDtoList.add(itemDto);
		}

		return itemDtoList;
	}
}