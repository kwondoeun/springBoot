package com.simple.basic.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.simple.basic.command.ValidVO;

@Controller
@RequestMapping("/valid")
public class ValidController {
	
	@GetMapping("/ex01")
	public String ex01() {
		return "valid/ex01";
	}
	
	//@Valid - 유효성검사를 진행, Errors - 유효성검사에 실패하면 에러객체로 바인딩
	@PostMapping("/actionForm")
	public String actionForm(@Valid ValidVO vo, Errors error, Model model) {
		
		
		if(error.hasErrors()) { //에러가 있다면 true, 에러가 없다면 false
			List<FieldError> list = error.getFieldErrors(); //에러가 발생된 목록
			for(FieldError err : list) {
				//System.out.println(err.getField()); //에러가 발생한 필드명
				//System.out.println(err.getDefaultMessage()); //에러 메시지
				if(err.isBindingFailure()) { //유효성 검사의 실패가 아니라, 자바 내부의 에러라면 true값을 반환
					model.addAttribute("valid_" + err.getField(), "형식이 올바르지 않습니다");
				} else { //유효성 검사에 실패한 목록
					model.addAttribute("valid_" + err.getField() , err.getDefaultMessage());
				}
			} //for문 end
			
			model.addAttribute("vo", vo); //사용자가 작성한 값을 화면으로
			return "valid/ex01"; //원래 화면으로
		}
		
		return "valid/ex01_result";
	}
	
}
