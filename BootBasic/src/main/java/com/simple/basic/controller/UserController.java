package com.simple.basic.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.basic.command.UsersVO;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/login")
	public String login() {
		return "user/login";
	}
	
	//로그인기능
	@PostMapping("/login")
	public String loginForm(UsersVO vo, HttpSession session) {
		
		//select * from 유저 where id = ? and pw = ?
		
		//서비스영역 호출 (로그인 성공)
		UsersVO userVO = new UsersVO();
		userVO.setId("aaa");
		userVO.setName("길동홍");
		
		//로그인 성공 - 세션을 이용해서 인증값
		if(userVO != null) {
			session.setAttribute("user_id", userVO.getId()); //토큰
			return "redirect:/user/mypage"; //로그인성공
		}
		
		return "user/login"; //로그인실패
	}
	
	@GetMapping("/mypage")
	public String mypage() {
		return "user/mypage";
	}
	
	@GetMapping("/info")
	public String info() {
		return "user/info";
	}
	
	
}
