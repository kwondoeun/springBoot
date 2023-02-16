package com.coding404.myweb.product.service;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Mapper //인터페이스에는 매퍼필요
public interface ProductMapper {
	public int regist (ProductVO vo);
	//매개변수로 전달되는 데이터가 2개 이상이라면 이름붙이기
	public ArrayList<ProductVO> getList(@Param("user_id") String user_id,
			                            @Param("cri") Criteria cri); //조회: 특정회원정보만 조회
	
	public int getTotal(@Param("user_id") String user_id,
			            @Param("cri")Criteria cri); //전체게시글수
}
