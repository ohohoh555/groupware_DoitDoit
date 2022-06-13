package com.doit.gw.ctrl.sign;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.Principal;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import com.doit.gw.service.sign.ISignService;
import com.doit.gw.vo.sign.SignVo;

@Controller
@RequestMapping("/appro")
public class SignController {
	

	@Autowired
	private ISignService service;
	
	private static final Logger logger = LoggerFactory.getLogger(SignController.class);
	

	//전자서명 메인화면으로 이동 
	@RequestMapping(value = "/signMain.do",method = RequestMethod.GET)
	public String signMain() {
		logger.info("============== SignController signMain으로 이동! ==============");
		return "/sign/signMain";
	}
	
	//서명관리 화면으로 이동 
	@RequestMapping(value = "/signModify.do",method = RequestMethod.GET)
	public String signModify() {
		logger.info("============== SignController signModify으로 이동! ==============");
		return "/sign/signModify";
	}
	
	
	//서명이미지 생성 폼으로 이동 (signature pad)
	@RequestMapping(value = "/signpadForm.do",method = RequestMethod.GET)
	public String signForm() {
		logger.info("============== SignController signpadForm으로 이동! ==============");
		return "/sign/signpadForm";
	}
	
	//서명이미지 업로드 폼으로 이동
	@RequestMapping(value = "/signUploadForm.do",method = RequestMethod.GET)
	public String signUploadForm() {
		logger.info("============== SignController signUploadForm으로 이동! ==============");
		return "/sign/signUploadForm";
	}
	
	//서명이미지 파일 업로드
	@RequestMapping(value = "/signUpload.do",method = RequestMethod.POST)
	public String signUpload(SignVo signVo,@RequestParam(value = "filename")MultipartFile filename,
							@RequestParam(value = "sign_img")String textimage,Principal principal, 
							HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		logger.info("============== SignController signUpload 시작! ==============");
		logger.info("[오리지널 파일명] : {}" ,filename.getOriginalFilename());
		
		//화면의 파일 정보를 vo에 넣어주기 
		signVo.setFile(filename);
		signVo.setSign_name(filename.getOriginalFilename());
		
		String emp_id = principal.getName();
		logger.info("[시큐리티 emp_id의 값] : {}",emp_id);
		
		signVo.setEmp_id(Integer.parseInt(emp_id));
		logger.info("[signVo의 값] : {}",signVo);
		
		//서버 저장을 위해 화면에서 사용자가 업로드한 파일을 받아옴
		MultipartFile file = signVo.getFile();
		
		//파일명 설정
		String fileName = filename.getOriginalFilename();
		
		//파일을 서버에 업로드하기 위한 IO 객체 생성
		//IO객체와 path값 초기화 
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String path = "";
		
		try {
			//file에 담겨진 값을 InputStream 객체에 넣기(예외처리 필수)
			//1. 파일 읽기 
			inputStream = filename.getInputStream();
			
			//2. 저장위치 생성 (temp폴더)
			path = WebUtils.getRealPath(request.getSession().getServletContext(), "/temp");
			logger.info("[실제로 업로드 된 file의 저장 경로] : {}",path);
			
			//3. 만약 저장위치가 없다면 생성해주고 있다면 오버라이드 해주기
			File temp = new File(path);
			if(!temp.exists()) { //exists 존재하는지 확인하는 메소드
				temp.mkdir(); //없다면 디렉토리(폴더) 생성한다
			}
			
			//4. 저장할 파일이 없다면 생성해주고 있따면 오버라이드 해주기
			File newFile = new File(path+"/"+fileName);
			if(newFile.exists()) {
				newFile.createNewFile();
			}
			logger.info("[실제로 업로드 된 file의 길이] : {}",newFile.length());
			
			//5. 파일을 쓸 위치 정해주기
			outputStream = new FileOutputStream(newFile);
			
			//6. 파일을 읽고 써주기(byte로 변환)
			int read = 0;
			byte[] b = new byte[(int)file.getSize()]; //파일의 size만큼 byte배열 선언
			while ((read = inputStream.read(b)) != -1) {//inputStream.read를 byte배열을 읽어서 read 변수에 대입함(이 read가 != -1이라면)
				outputStream.write(b,0,read);//b의 0부터 read만큼 써달라
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//IO 객체 닫아주기
			try {
				inputStream.close();
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// DB저장 START
		// DATA URL을 바이트로 변환
		byte[] imageArray = null;
		final String BASE_64_PREFIX = "data:image/png;base64,";
		try {
		String base64Url = String.valueOf(textimage);
		logger.info("[base64Url의 값] : {}",base64Url);
			if(base64Url.startsWith(BASE_64_PREFIX)) {
				imageArray =  Base64.getDecoder().decode(base64Url.substring(BASE_64_PREFIX.length()));
				logger.info("[imageArray의 값] : {}",new String(imageArray));
			}
		 }
        catch (Exception e){
            e.printStackTrace();
        }
		// 모델 객체에 idx 및 byte 지정 실시 [오라클 blob 컬럼은 byte 로 되어있다]
		SignVo userImg = new SignVo(signVo.getEmp_id(), fileName, imageArray);
		if(service.insSign(userImg)>0) {
			model.addAttribute("fileObj", userImg);
			model.addAttribute("path", path);
			
			response.setContentType("text/html;charset=utf-8;");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('서명이미지 업로드 완료!');</script>");
	        out.flush();
	        return "/sign/signModify";
		}else {
			PrintWriter out = response.getWriter();
			out.print("<script>alert('서명이미지 업로드 실패!');</script>");
			out.flush();
			return "/sign/signUploadForm";
		}
	}
	
	//결재칸에 기안자 서명이미지 보여주기 
	@RequestMapping(value = "/viewImg.do",method = RequestMethod.GET)
	@ResponseBody
	public String viewImg(int emp_id) {
		logger.info("============== SignController viewImg 시작! ==============");
		
		logger.info("[emp_id의 값] : {}",emp_id);
		//DB에 있는 blob이미지 select
		List<Map<String, Object>> lists = service.selSign(emp_id);
		logger.info("[lists의 값] : {}",lists);
		
		//여러개의 사인 이미지 중에 최근에 등록한 이미지를 보여줌
		Map<String, Object> map = lists.get(0); 
		byte arr[] = blobToBytes((Blob) map.get("SIGN_IMG"));
		logger.info("[byte arr[]값 확인] : {}",Arrays.toString(arr));
		
		if(arr.length > 0 && arr != null){ //데이터가 들어 있는 경우
			//바이트를 base64인코딩 실시
			String base64Encode = Base64Utils.encodeToString(arr);
			base64Encode = "data:image/png;base64," + base64Encode;
			logger.info("[base64Encode값 확인] : {}",base64Encode);
			return base64Encode;
		}
		else {
            return "";
        }
	}
	
	//[blob 데이터를 바이트로 변환해주는 메소드]
	private static byte[] blobToBytes(Blob blob) {
		BufferedInputStream is = null;
		byte[] bytes = null;
			try {
				is = new BufferedInputStream(blob.getBinaryStream());
		        bytes = new byte[(int) blob.length()];
		        int len = bytes.length;
		        int offset = 0;
		        int read = 0;
		
		        while (offset < len
		                && (read = is.read(bytes, offset, len - offset)) >= 0) {
		            offset += read;
		        }
			
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		return bytes;
	}
	
	//서명이미지 전체조회
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/signList.do",method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
	@ResponseBody
	public String signList(Principal principal) {
		logger.info("============== SignController signList 시작! ==============");
		String emp_id = principal.getName();
		int int_emp_id = Integer.parseInt(emp_id);
		//DB에 있는 blob이미지 select
				List<Map<String, Object>> lists = service.selSign(int_emp_id);
				logger.info("[lists의 값] : {}",lists);
				List<String> images = new ArrayList();
				List<String> names = new ArrayList();
				JSONObject obj = new JSONObject();
				for(int i=0; i<lists.size(); i++) {
					Map<String, Object> getImg =lists.get(i);
					names.add((String) getImg.get("SIGN_NAME"));
					
					byte blobArr[] = blobToBytes((Blob)getImg.get("SIGN_IMG"));
					if(blobArr.length >0 && blobArr != null) {
						String base64Encode = Base64Utils.encodeToString(blobArr);
						base64Encode = "data:image/png;base64," + base64Encode;
						images.add(base64Encode);
					}
				}
				obj.put("images", images);
				obj.put("names", names);
				logger.info("[names 값 확인] : {}",names.toString());
				logger.info("[images 값 확인] : {}",images.toString());
				
				return obj.toJSONString();
	}
	//서명 삭제
		@RequestMapping(value = "/delSign.do",method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
		@ResponseBody
		public String updSign(String names,String empIds) {
			logger.info("[names 값 확인] : {}",names);
			logger.info("[empIds 값 확인] : {}",empIds);
			SignVo signVo = new SignVo();
			signVo.setEmp_id(Integer.parseInt(empIds));
			signVo.setSign_name(names);
			int n = service.updSign(signVo);
			if(n == 1) {
				return "true";
			}else {
				return "false";
			}
		}
}
