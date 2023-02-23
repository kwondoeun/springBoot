package com.simple.basic.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class UserSuccessHandler implements HandlerInterceptor {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		//ModelAndView은 컨트롤러에서 model에 담은 데이터도 확인가능.
		System.out.println("포스트핸들러:" + request.getRequestURI());
	}

	
}
