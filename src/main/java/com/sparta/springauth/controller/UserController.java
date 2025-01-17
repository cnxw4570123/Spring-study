package com.sparta.springauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sparta.springauth.dto.LoginRequestDto;
import com.sparta.springauth.dto.SignupRequestDto;
import com.sparta.springauth.service.UserService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api")
public class UserController {
	private final UserService userSerivce;

	public UserController(UserService userSerivce) {
		this.userSerivce = userSerivce;
	}

	@GetMapping("/user/login-page")
	public String loginPage() {
		return "login";
	}

	@GetMapping("/user/signup")
	public String signupPage() {
		return "signup";
	}

	@PostMapping("/user/signup")
	public String singup(@ModelAttribute SignupRequestDto requestDto) {
		userSerivce.signup(requestDto);
		return "redirect:/api/user/login-page";
	}
}