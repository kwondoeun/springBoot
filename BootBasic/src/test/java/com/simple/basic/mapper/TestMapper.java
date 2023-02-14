package com.simple.basic.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper //스프링 부트에서는 매퍼 인터페이스 꼭 붙이기!!
public interface TestMapper {
	
	public String getTime();

}
