package com.sparta.springcloud.eureka.client.gateway;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LocalJwtAuthenticationFilter implements GlobalFilter {
	private final SecretKey key;

	public LocalJwtAuthenticationFilter(@Value("${service.jwt.secret-key}") String salt) {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(salt));
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		// 인증 시도에는 필터 적용 X
		if(path.equals("/auth/sign-In")){
			return chain.filter(exchange);
		}

		String token = extractToken(exchange);

		// 인증 실패 시 401 바로 반환
		if (!StringUtils.hasText(token) || !validateToken(token)) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		return chain.filter(exchange);
	}

	private static String extractToken(ServerWebExchange exchange) {
		ServerHttpRequest request = exchange.getRequest();
		String token = request.getHeaders().getFirst("Authorization");
		if (!StringUtils.hasText(token) || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7);
	}

	private boolean validateToken(String token) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.verifyWith(key)
				.build().parseSignedClaims(token);
			log.info("#####payload :: {}", claimsJws.getPayload().toString());

			// 추가적인 검증 로직 (예: 토큰 만료 여부 확인 등)을 여기에 추가할 수 있습니다.
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
