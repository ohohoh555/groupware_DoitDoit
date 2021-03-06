package com.doit.gw.ctrl.entr;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.doit.gw.vo.entr.FileListVo;

/**
 * @since 2022.06.14 
 * @author 오지혜
 * CK Editor에서 파일 업로드/다운로드를 사용할 수 있는 Controller
 */
@Controller
@RequestMapping("/comm")
public class FileUploadController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * Ck에디터의 드래그앤드롭 또는 이미지업로드 창을 통한 파일 업로드
	 * @param request 
	 * @param response 
	 * @param upload 
	 */
	@RequestMapping(value = "/fileupload.do", method = RequestMethod.POST)
	public void editorFileUpload(HttpServletRequest request, HttpServletResponse response,
										@RequestParam MultipartFile upload) {
		
		logger.info("@editorFileUpload 공지게시글 입력시 파일업로드");
		
		//랜덤문자 생성
		UUID uid = UUID.randomUUID();
		
		OutputStream out  = null;
		OutputStream bout = null;
		PrintWriter printWriter = null;
		
		//인코딩
		response.setContentType("text/html; charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		
		try {
			//파일 이름 가져오기
			String fileName = upload.getOriginalFilename();
			System.out.println("파일 이름:"+fileName);
			//파일 내용을 byte[] 으로 들고옴
			byte[] bytes = upload.getBytes();
			//파일 사이즈 측정(byte로 돌려줌 )
			long size = upload.getSize();  
			// long kilobyte = size / 1024;
			// long megabyte = size / 1024;

			System.out.println("파일 사이즈:"+size);
			//파일 확장자 가져오기
			String extension = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
			System.out.println("파일 확장자:"+extension);
			
			
			
			//서버의 물리적인 경로 가져오기
			String path = WebUtils.getRealPath(request.getSession().getServletContext(), "/img/storage/");
			
			//서버가 꺼졌을때를 위한 백업경로(절대경로)
			//업로드 되는 날짜를 구해서 백업경로 폴더 자동으로 생성되게끔 처리
			LocalDate now = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY/MM/");
			String nowFormat = now.format(formatter); 
			
			String back = "/var/doit/jaryoFile/"+nowFormat;
			System.out.println("저장위치 path:"+path);
			System.out.println("백업위치 back:"+back);
			
			//폴더 공간 만들어주기 
			File serverPath = new File(path);
			File backPath = new File(back);
			//폴더(디렉토리)가 없다면 생성
			if(!serverPath.exists()) {
				//만드려는 상위디렉토리가 있어야만 생성가능
				serverPath.mkdirs();  
			}
			if(!backPath.exists()) {
				// 만드려는 상위디렉토리가 없으면 상위도 만들어주고 생성
				backPath.mkdirs(); 
			}
			//덮어쓰기 안되게끔 유효아이디(UUID)로 파일이름 생성
			String uploalName = path+uid+"_"+fileName;
			String backupName = back+uid+"_"+fileName;
			
			File uploadFile = new File(uploalName);
			File backupFile = new File(backupName);
			
			
			//파일 생성
			bout = new FileOutputStream(uploadFile);
			out = new FileOutputStream(backupFile);


			// outputStram에 저장된 데이터를 전송하고 초기화
			bout.write(bytes);
			out.write(bytes);
	    	bout.flush();
	    	out.flush(); 
	    	
			
	    	//응답객체를 얻어서 화면에 링크로 다운받을 수 있게끔
	    	printWriter = response.getWriter();
	    	//서버에 저장된 내용을 다운로드할 수 있도록 처리
	    	String fileUrl = "download.do?uid=" + uid + "&fileName=" + fileName;
			
	    	//업로드시 메시지 출력
	    	printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}");
	    	printWriter.flush();
	    	
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
				try {
					if(out!=null) {out.close(); }
					if(printWriter != null) {printWriter.close();}
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return;

	}
	
	/**
	 * CK 에디터로 서버에 업로드된 파일을 다운로드
	 * @param uid 유효아이디
	 * @param fileName 파일명
	 * @param request
	 * @param response
	 * @return 서버에 업로된 파일의 byte
	 * @throws IOException
	 */
	@RequestMapping(value = "/download.do")
	@ResponseBody
	public byte[] filedownload(@RequestParam(value="uid") String uid,
								@RequestParam(value="fileName") String fileName,
								HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] bytes=null;
		logger.info("@download : {} {}",  uid, fileName);
		
		//서버에 저장된 이미지 경로 
		String path=WebUtils.getRealPath(request.getSession().getServletContext(), "/storage/") ;
		String sDirPath = path+uid+"_"+fileName;
		
		File file = new File(sDirPath);
		
		bytes = FileCopyUtils.copyToByteArray(file);
		
		String outputFilename =new String(file.getName().getBytes(), "8859_1");
		
		/* 
		 * Content-disposition : 지정된 파일명을 지정함으로써 더 자세한 파일의 속성을 알려줄 수 있다.
		 * attachment : 브라우저 인식 파일확장자를 포함하여 모든 확장자의 파일들에 대해, 
		 * 				다운로드 시 무조건 '파일다운로드' 대화상자가 뜨도록 하는 해더속성
		 */
		response.setHeader("Content-Disposition", "attachment; filename=\""+outputFilename+"\"");
		//우리가 보내려고 하는 파일의 크기만큼 셋팅
		response.setContentLength(bytes.length); 
		//MIME타입, octet-stream : 8비트로 된 파일이라는 의미
		response.setContentType("application/octet-stream");

		return bytes; 
	}
	
	
	


}
