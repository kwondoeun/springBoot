package com.simple.basic;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.simple.basic.command.MemoVO;
import com.simple.basic.command.UsersVO;
import com.simple.basic.mapper.TestMapper;

@SpringBootTest
public class JoinTest {

	@Autowired
	private TestMapper testMapper;
	
	//N:1조인
	@Test
	public void testCode01() {
		List<MemoVO> list = testMapper.joinOne();
		System.out.println(list.toString());
	}
	
	
	//1:N조인
	@Test
	public void testCode02() {
		UsersVO<MemoVO> vo = testMapper.joinTwo();
		System.out.println(vo.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
}

