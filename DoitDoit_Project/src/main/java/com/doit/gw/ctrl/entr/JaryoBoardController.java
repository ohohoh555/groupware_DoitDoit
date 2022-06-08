package com.doit.gw.ctrl.entr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.doit.gw.service.entr.IJaryoService;
import com.doit.gw.vo.entr.FileListVo;

@Controller
public class JaryoBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IJaryoService service;
	
	@RequestMapping(value = "/jaryoBoard.do", method = RequestMethod.GET)
	public String jaryoBoard() {
		logger.info("@jaryoBoard 자료게시판 이동");
		return "/jaryo/jaryoBoard";
	}
	
	@RequestMapping(value = "/selJaryoAllUser.do", method = RequestMethod.POST,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<FileListVo> selJaryoAllUser(){
		logger.info("@selJaryoAllUser 사용자 자료게시글 전체조회");
		List<FileListVo> data = service.selJaryoAllUser();
		return data;
	}
	
	@RequestMapping(value = "/saveJaryo.do", method = RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String saveFile(MultipartHttpServletRequest multipartRequest, HttpServletRequest request, FileListVo fVo) throws IOException {
		logger.info("@saveJaryo 자료글 저장하기 : {}", fVo);
		
		String server = WebUtils.getRealPath(request.getSession().getServletContext(), "/storage/");
		
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY\\MM\\");
		String nowFormat = now.format(formatter); 
		
		String back = "C:\\Users\\user\\git\\groupware_doit\\DoitDoit_Project\\src\\main\\resources\\back\\"+nowFormat;
		
		File serverPath = new File(server);
		File backPath = new File(back);
		if(!serverPath.exists() || !backPath.exists()) {
			serverPath.mkdirs();
			backPath.mkdirs();
		}
		
		
		Iterator<String> itr =multipartRequest.getFileNames();
		OutputStream outFile = null;
		

		while(itr.hasNext()) { // 받은파일을 모두
			UUID uid = UUID.randomUUID(); //유효아이디 생성
			MultipartFile mpFile=multipartRequest.getFile(itr.next());
			String originFileName=mpFile.getOriginalFilename(); // 실제 파일명 
			String saveFileSize=Long.toString(mpFile.getSize());
			String saveFileName=uid+"_"+originFileName; //유효아이디+파일명 = 저장될 파일명
			byte[] bytes = mpFile.getBytes();
			
			String fileFullPath = backPath+saveFileName; 
			System.out.println("파일 저장이름 : "+saveFileName);
			System.out.println("파일 사이즈 : "+saveFileSize);
			System.out.println("파일 전체 경로 : "+fileFullPath);
			System.out.println("파일 서버 경로 : "+serverPath);
			
			
			
			// 등록된 파일을 백업 파일로 복사
			File backFile = new File(serverPath+saveFileName);
			
			outFile = new FileOutputStream(backFile);
			outFile.write(bytes);
			outFile.flush();
			
			fVo.setFlist_originname(originFileName);
			fVo.setFlist_size(saveFileSize);
			fVo.setFlist_uuid(uid.toString());
			fVo.setFlist_uploadpath(back);
			
			int cnt = service.insJaryo(fVo);
			logger.info("@saveJaryo 자료글 입력 성공횟수:{}",cnt);

			try {
				//file.createNewFile(); //파일을 생성만 해주고 내용이 없음...
				mpFile.transferTo(new File(fileFullPath));//등록된 파일을 서버공간으로 이동시킴

			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				outFile.close();
			}
		}
		return "업로드 성공";
	}
	
	
	@RequestMapping(value = "/jaryoDel.do", method = RequestMethod.GET)
	public void jaryoDel(String eboard_no, HttpServletResponse response) throws IOException {
		logger.info("@jaryoDel 사용자 자료글 삭제처리 :{}",eboard_no);
		int cnt = service.updJaryoDelflagUser(eboard_no);
		logger.info("@jaryoDel 자료글 숨김 성공횟수 : {}", cnt);
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter =response.getWriter();
		
		if(cnt >0) {
			printWriter.println("<script>alert('선택한 자료를 삭제하였습니다!'); location.href='./jaryoBoard.do'</script>");
		}else {
			printWriter.println("<script>alert('자료삭제에 실패하였습니다!')</script>");
		}
		
		return;
	
	}
	

}
