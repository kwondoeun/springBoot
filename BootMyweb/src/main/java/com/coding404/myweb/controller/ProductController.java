package com.coding404.myweb.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.product.service.ProductService;
import com.coding404.myweb.util.Criteria;
import com.coding404.myweb.util.PageVO;

@Controller
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	@Qualifier("productService")
	private ProductService productService;
	
	@GetMapping("/productReg")
	public String reg() {
		return "product/productReg";
	}
	
	@GetMapping("/productList")
	public String list(HttpSession session, /*, HttpServletRequest request*/
			           Model model,
			           Criteria cri) { 
		
		//프로세스
		//사용할값이 없어서 강제로 admin이라고 가정
		session.setAttribute("user_id", "admin");
		
		//로그인 한 회원만 조회
		String user_id = (String)session.getAttribute("user_id");
		
		ArrayList<ProductVO> list = productService.getList(user_id, cri);
		model.addAttribute("list", list);
		
		//페이지네이션 처리
		int total = productService.getTotal(user_id);
		PageVO pageVO = new PageVO(cri, total);
		model.addAttribute("pageVO", pageVO);
		
		return "product/productList";
	}
	
	@GetMapping("/productDetail")
	public String detail() {
		
		//자율적 숙제 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		return "product/productDetail";
	}
	
	//등록 요청
	@PostMapping("/registForm")
	public String registForm(/*@Valid*/ ProductVO vo,
								RedirectAttributes ra) {
		
		int result = productService.regist(vo);
		
		String msg = result == 1 ? "정상 입력되었습니다" : "등록에 실패했습니다";
		
		ra.addFlashAttribute("msg", msg);
		
		return "redirect:/product/productList"; //목록으로
	}

}
