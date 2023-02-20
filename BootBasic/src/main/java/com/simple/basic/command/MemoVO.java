package com.simple.basic.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemoVO {
	
	private int mno;
	private String writer;
	private String memo;
	
	//N : 1조인에서 N쪽에 처리할 데이터를 추가
	private String name;
	private String email;

}
