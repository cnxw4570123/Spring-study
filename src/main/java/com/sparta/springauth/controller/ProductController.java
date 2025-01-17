package com.sparta.springauth.controller;

import com.sparta.springauth.dto.ProductRequestDto;
import com.sparta.springauth.entity.User;
import com.sparta.springauth.entity.UserRole;
import com.sparta.springauth.security.UserDetailsImpl;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api")
public class ProductController {

	@GetMapping("/products")
	public String getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		System.out.println("ProductController.getProducts : 인증 완료");
		User user = userDetails.getUser();
		System.out.println("user.getEmail() = " + user.getEmail());
		System.out.println("user.getUsername() = " + user.getUsername());

		return "redirect:/";
	}

	@Secured(UserRole.Authority.ADMIN) // 관리자용
	@GetMapping("/products/secured")
	public String getProductsByAdmin(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		System.out.println("userDetails.getUsername() = " + userDetails.getUsername());
		for (GrantedAuthority authority : userDetails.getAuthorities()) {
			System.out.println("authority.getAuthority() = " + authority.getAuthority());
		}

		return "redirect:/";
	}

	@PostMapping("/validation")
	@ResponseBody
	public ProductRequestDto testValid(@RequestBody @Valid ProductRequestDto requestDto) {
		return requestDto;
	}
}