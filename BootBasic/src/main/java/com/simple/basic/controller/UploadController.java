package com.simple.basic.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import net.coobird.thumbnailator.Thumbnailator;

@Controller
@RequestMapping("/upload")
public class UploadController {
	
	@GetMapping("/ex01")
	public void ex01() {}
	
	@Value("${project.uploadpath}")
	private String uploadpath;
	
	//날짜별로 폴더를 생성
	public String makeDir() {
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String now = sdf.format(date);
		
		String path = uploadpath + "\\" + now; //경로
		File file = new File(path);
		
		if(file.exists() == false ) { //존재하면 true
			file.mkdir(); //폴더생성
		}
		
		return path;
	}
	
	//단일 파일 업로드
	@PostMapping("/upload_ok")
	public String uploadOk(@RequestParam("file") MultipartFile file) {
		
//		String origin = file.getOriginalFilename(); //파일명
//		long size = file.getSize(); //사이즈
//		String type = file.getContentType(); //파일 데이터에 컨텐츠타입
//		System.out.println(origin);
//		System.out.println(size);
//		System.out.println(type);
		
		//파일명
		String origin = file.getOriginalFilename();
		//브라우저별로 경로가 포함돼서 올라오는 경우가 있어서 간단한 처리(파일명만 뜨게 잘라냄)
		String filename = origin.substring(origin.lastIndexOf("\\") + 1);
		//폴더생성
		String filepath = makeDir();
		//중복파일의 처리
		String uuid = UUID.randomUUID().toString();
		//최종저장경로
		String savename = filepath + "\\" + uuid + "_" + filename;
		
		System.out.println(filename);
		System.out.println(filepath);
		System.out.println(uuid);
		System.out.println(savename);
		
		try {
			File save = new File(savename); //세이브경로
			file.transferTo(save); //업로드진행
			
			//썸네일 경로
			String thumbsname = filepath + "\\" + uuid + "_thumbs_" + filename;
			//썸네일 생성 (복사할파일위치, 썸네일생성위치, 가로, 세로)
			Thumbnailator.createThumbnail(new File(savename),
					                      new File(thumbsname),
					                      150,
					                      150);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "upload/ex01_ok";
	}
	
	//multiple옵션으로 다중파일 업로드
	@PostMapping("/upload_ok2")
	public String uploadOk2(MultipartHttpServletRequest files) {
		
		//name태그가 file인 것을 찾음
		List<MultipartFile> list = files.getFiles("file");
		//반복처리
		
		for(MultipartFile file : list) {
			//파일명
			String origin = file.getOriginalFilename();
			//브라우저별로 경로가 포함돼서 올라오는 경우가 있어서 간단한 처리(파일명만 뜨게 잘라냄)
			String filename = origin.substring(origin.lastIndexOf("\\") + 1);
			//폴더생성
			String filepath = makeDir();
			//중복파일의 처리
			String uuid = UUID.randomUUID().toString();
			//최종저장경로
			String savename = filepath + "\\" + uuid + "_" + filename;
			
			System.out.println(filename);
			System.out.println(filepath);
			System.out.println(uuid);
			System.out.println(savename);
			
			try {
				File save = new File(savename); //세이브경로
				file.transferTo(save); //업로드진행
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "upload/ex01_ok"; 
	}
	
	//복수태그로 여러파일 업로드
	@PostMapping("/upload_ok3")
	public String uploadOk3(@RequestParam("file") List<MultipartFile> list) {
		
		//리스트에서 빈값은 제거
		list = list.stream()
				   .filter( (x) -> x.isEmpty() == false)
				   .collect(Collectors.toList());
		
		//반복처리
		for(MultipartFile file : list) {
			//파일명
			String origin = file.getOriginalFilename();
			//브라우저별로 경로가 포함돼서 올라오는 경우가 있어서 간단한 처리(파일명만 뜨게 잘라냄)
			String filename = origin.substring(origin.lastIndexOf("\\") + 1);
			//폴더생성
			String filepath = makeDir();
			//중복파일의 처리
			String uuid = UUID.randomUUID().toString();
			//최종저장경로
			String savename = filepath + "\\" + uuid + "_" + filename;
			
			System.out.println(filename);
			System.out.println(filepath);
			System.out.println(uuid);
			System.out.println(savename);
			
			try {
				File save = new File(savename); //세이브경로
				file.transferTo(save); //업로드진행
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return "upload/ex01_ok"; 
	}
	
	
	//비동기 업로드 - 넘어오는 데이터는 form 형식이기 때문에 vo or requestParam으로 받으면됨
	@PostMapping("/upload_ok4")
	@ResponseBody //return의 값이 요청이 온곳으로 반환
	public String uploadOk4(@RequestParam("file") MultipartFile file,
			                @RequestParam("writer") String writer) {
		
		//파일명
		String origin = file.getOriginalFilename();
		//브라우저별로 경로가 포함돼서 올라오는 경우가 있어서 간단한 처리(파일명만 뜨게 잘라냄)
		String filename = origin.substring(origin.lastIndexOf("\\") + 1);
		//폴더생성
		String filepath = makeDir();
		//중복파일의 처리
		String uuid = UUID.randomUUID().toString();
		//최종저장경로
		String savename = filepath + "\\" + uuid + "_" + filename;
		
		System.out.println(filename);
		System.out.println(filepath);
		System.out.println(uuid);
		System.out.println(savename);
		
		try {
			File save = new File(savename); //세이브경로
			file.transferTo(save); //업로드진행
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	
	
	
	

}
