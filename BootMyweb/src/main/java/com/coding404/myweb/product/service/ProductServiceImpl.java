package com.coding404.myweb.product.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.coding404.myweb.command.CategoryVO;
import com.coding404.myweb.command.ProductUploadVO;
import com.coding404.myweb.command.ProductVO;
import com.coding404.myweb.util.Criteria;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	//업로드패스
	@Value("${project.uploadpath}")
	private String uploadpath;
	
	//날짜별로폴더생성
	public String makeDir() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		String now = sdf.format(date);
		
		String path = uploadpath + "\\" + now; //경로
		File file = new File(path);
		
		if(file.exists() == false ) { //존재하면 true
			file.mkdir(); //폴더생성
		}
				
		return now; //년월일 폴더위치 
	}
	
	
	//글등록
	//한프로세스 안에서 예외가 발생하면, 기존에 진행했던 CRUD작업을 Rollback
	//조건 - catch를 통해서 예외처리가 진행되면 트랜잭션 처리가 되지 않습니다.
	@Transactional(rollbackFor = Exception.class)
	@Override
	public int regist(ProductVO vo, List<MultipartFile> list) {
		
		//1. 글등록처리 -> 
		int result = productMapper.regist(vo);
		
		//2. 파일인서트 ->
		//반복처리
		for(MultipartFile file : list) {
			//파일명
			String origin = file.getOriginalFilename(); 
	   	 	//브라우저별로 경로가 포함되서 올라오는 경우가 있어서 간단한 처리
			String filename = origin.substring( origin.lastIndexOf("\\") + 1 );
			//폴더생성
			String filepath = makeDir();
			//중복파일의 처리
			String uuid = UUID.randomUUID().toString();
			//최종저장경로
			String savename = uploadpath + "\\" + filepath + "\\" + uuid + "_" + filename;
					
			try {
				File save = new File(savename); //세이브경로
				file.transferTo(save); //업로드 진행
				
			} catch (Exception e) {
				e.printStackTrace();
				return 0; //실패의 의미로
			}
			
			//모든개발자 들이 했을꺼야. 구글링
			
			//인서트 - insert이전에 prod_id가 필요한데, selectKey방식으로 처리 
			ProductUploadVO prodVO = ProductUploadVO.builder()
								   .filename(filename) 
								   .filepath(filepath)
								   .uuid(uuid)
								   .prod_writer(vo.getProd_writer()) 
								   .build();
			
			productMapper.registFile(prodVO );
			
		} //end for
		
		return result; //성공시 1, 실패시 0
	}

	@Override
	public ArrayList<ProductVO> getList(String user_id, Criteria cri) {
		return productMapper.getList(user_id, cri);
	}

	@Override
	public int getTotal(String user_id, Criteria cri) {
		return productMapper.getTotal(user_id, cri);
	}

	@Override
	public List<CategoryVO> getCategory() {
		return productMapper.getCategory();
	}

	@Override
	public List<CategoryVO> getCategoryChild(CategoryVO vo) {
		return productMapper.getCategoryChild(vo);
	}


	@Override
	public List<ProductUploadVO> getProductImg(ProductVO vo) {
		return productMapper.getProductImg(vo);
	}

}
