package com.simple.basic.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //get, set, toString
@NoArgsConstructor //기본생성자
@AllArgsConstructor //모든생성자
@Builder //빌더패턴
public class BuilderVO2 {
	
	private String name;
	private int age;

}
