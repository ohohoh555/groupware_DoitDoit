package com.doit.gw.ctrl.entr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.doit.gw.service.entr.IJaryoService;
import com.doit.gw.vo.entr.FileListVo;

@Controller
@RequestMapping("/jaryo")
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
		
		//서버 물리적 경로 가져오기
		String server = WebUtils.getRealPath(request.getSession().getServletContext(), "/storage/");
		
		
		// 서버꺼졌을 때 백업경로(절대경로)
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY\\MM\\");
		String nowFormat = now.format(formatter); 
		String back = "C:\\Users\\user\\git\\groupware_doit\\DoitDoit_Project\\src\\main\\resources\\back\\"+nowFormat;
		
		System.out.println("저장위치 server:"+server);
		System.out.println("백업위치 back"+back);
		
		//폴더공간 만들어주기
		File serverPath = new File(server);
		File backPath = new File(back);
		
		if(!serverPath.exists()) {
			serverPath.mkdir();
		}
		if(!backPath.exists()) {
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
			
			String fileBackPath = backPath+"\\"+saveFileName; 
			
			String fileServerPath = serverPath+"\\"+saveFileName; 
			System.out.println("파일 저장이름 : "+saveFileName);
			System.out.println("파일 사이즈 : "+saveFileSize);
			System.out.println("파일 백업 경로 : "+fileBackPath);
			System.out.println("파일 서버 경로 : "+fileServerPath);
			
			
			
			// 등록된 파일을 백업 파일로 복사
			File backFile = new File(fileBackPath);
			File serverFile = new File(fileServerPath);
			
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
				mpFile.transferTo(serverFile);//등록된 파일을 서버공간으로 이동시킴

			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				outFile.close();
			}
		}
		return "업로드 성공";
	}
	
	
	@RequestMapping(value = "/jaryoDel.do", method = RequestMethod.GET)
	public void jaryoDel(@RequestParam Map<String, Object>map, HttpServletResponse response) throws IOException {
		logger.info("@jaryoDel 사용자 자료글 삭제처리 :{}",map);
		int cnt = service.updJaryoDelflagUser(map);
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
	
	/**
	 * 자료게시판의 다중파일 다운로드
	 * @param chk (유효아이디_파일명 형태로 넘어옴 ex>6ad9047d-a891-45b8-9cec-3bde4453f784_DOIT_쿼리테스트_오지혜.xlsx)
	 * @param request (파일이 저장되어있는 서버의 물리적 경로 호출)
	 * @param response (파일을 다운로드하기 위한 응답객체 호출)
	 * @return byte[] 파일의 바이트
	 * @throws IOException
	 */
	@RequestMapping(value = "/multiJaryo.do", method = RequestMethod.POST)
	@ResponseBody
	public byte[] multiJaryo(@RequestParam ArrayList<String> chk , 
							HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("@multiJaryo 다중파일 다운로드 :{}",chk);
		
		//서버 물리적인 경로를 가져와서 다운로드 할거임
		String path=WebUtils.getRealPath(request.getSession().getServletContext(), "/storage/") ;
		
		//http는 한번에 하나의 파일만 다운로드할 수 있음 
		
		
		
		byte[] bytes = null;
		String downPath = null;
		String fName = null;
		
		Iterator<String> iter = chk.iterator();
		while(iter.hasNext()) {
			fName = iter.next();
			downPath = path+"//"+fName;
			File file = new File(downPath);
			bytes = FileCopyUtils.copyToByteArray(file);
			
			String outputFilename =new String(file.getName().getBytes(), "8859_1");
			response.setHeader("Content-Disposition", "attachment; filename=\""+outputFilename+"\"");
			response.setContentLength(bytes.length); 
			response.setContentType("application/octet-stream");
			
			response.getOutputStream().write(bytes);;
			
			
		}
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
		return null;
	}
	

}
