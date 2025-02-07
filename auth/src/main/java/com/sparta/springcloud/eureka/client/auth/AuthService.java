package com.sparta.springcloud.eureka.client.auth;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
	private final String issuer;
	private final Long accTokenExpriation;
	private final SecretKey secretKey;

	public AuthService(
		@Value("${service.jwt.secret-key}") String secret,
		@Value("${spring.application.name}") String issuer,
		@Value("${service.jwt.access-expiration}") Long accTokenExpiration
	) {
		this.issuer = issuer;
		this.accTokenExpriation = accTokenExpiration;
		secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secret));
	}

	public String createAccessToken(String userId) {

		Date now = new Date(System.currentTimeMillis());
		return
			Jwts.builder()
				.claim("userId", userId)
				.claim("role", "ROLE_USER")
				.issuer(issuer)
				.issuedAt(now)
				.expiration(Date.from(now.toInstant().plusMillis(accTokenExpriation)))
				.signWith(secretKey, Jwts.SIG.HS512)
				.compact();
	}
}
