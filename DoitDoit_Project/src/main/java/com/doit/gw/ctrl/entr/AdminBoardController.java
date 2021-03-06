package com.doit.gw.ctrl.entr;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.entr.IEntrService;
import com.doit.gw.service.entr.IJaryoService;
import com.doit.gw.vo.entr.EntrBoardVo;
import com.doit.gw.vo.entr.FileListVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @since 2022.06.14
 * @author 오지혜
 * 관리자의 게시판관리 기능을 위한 Controller
 */

@Controller
@RequestMapping("/admin")
public class AdminBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IEntrService eService;
	
	@Autowired
	private IJaryoService jService;
	
	
	/**
	 * @return 관리자 게시판 관리의 기본화면인 공지게시판 관리로 이동
	 */
	@RequestMapping(value = "/entrBoardAdmin.do", method = RequestMethod.GET)
	public String entrBoardAdmin() {
		logger.info("@entrBoardAdmin 관리자 게시판관리 이동");
		return "/admin/entrAdmin";
	}
	
	
	/**
	 * 
	 * @return 공지게시판 관리 화면에서 공지게시글 목록 전체조회
	 */
	@RequestMapping(value = "/selEboardAllAdmin.do", method = {RequestMethod.POST})
	@ResponseBody
	public List<EntrBoardVo> selEboardAllAdmin() {
		logger.info("@selEboardAllAdmin 관리자 공지게시판 전체조회");
		List<EntrBoardVo> data = eService.selEboardAllAdmin();
		return data;
	}
	
	/**
	 * 
	 * @param map {cgory : "카테고리번호"}
	 * @return 각 카테고리별 공지게시글 목록 조회
	 */
	@RequestMapping(value = "/selEboardCgoryAdmin.do", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String selEboardCgoryAdmin(@RequestParam Map<String, Object> map) {
		logger.info("@selEboardCgoryAdmin 관리자 공지게시판 카테고리별 조회 : {}", map);
		List<EntrBoardVo> lists = eService.selEboardCgoryAdmin(map);
		Gson data = new GsonBuilder().create();
		return data.toJson(lists);
	}
	
	/**
	 * 
	 * @param eboard_no 글번호
	 * @param model 조회된 게시글
	 * @return 글번호로 조회된 게시글 상세조회
	 */
	@RequestMapping(value = "/OneBoardAdmin.do", method = RequestMethod.GET)
	public String OneBoardAdmin(String eboard_no, Model model) {
		logger.info("EntrBoardController OneBoardAdmin 관리자 공지게시글 상세조회 : {}",eboard_no);
		EntrBoardVo entrOne = eService.selEboardDetail(eboard_no);
		model.addAttribute("entrOne", entrOne);
		return "/admin/oneAdmin";
	}
	
	/**
	 * 
	 * @param chk 체크박스를 통해서 선택한 글번호 리스트
	 * @return 숨김여부인 Delflag가 변경된 성공횟수 
	 */
	@RequestMapping(value = "/changeEntrDel.do", method = RequestMethod.POST)
	public String changeDel(@RequestParam ArrayList<String> chk) {
		logger.info("@changeDel 관리자 게시글 숨김/보임 처리 : {}", chk);
		int cnt = eService.updEboardDelfAdmin(chk);
		logger.info("@changeDel 숨김/보임 처리 성공횟수 :{}", cnt);
		return "redirect:./entrBoardAdmin.do";
	}
	
	/**
	 * 
	 * @param eboard_no 글번호
	 * @return 변경된 내용을 확인하기 위해 해당 글의 상세조회로 이동
	 */
	@RequestMapping(value = "/changeDelOne.do", method = RequestMethod.GET)
	public String changeDelOne(String eboard_no) {
		logger.info("@changeDelOne 관리자 상세조회에서 숨김/보임처리: {}",eboard_no);
		List<String> chk = new ArrayList<String>();
		chk.add(eboard_no);
		int cnt = eService.updEboardDelfAdmin(chk);
		logger.info("@changeDelOne 숨김/보임 처리 성공횟수: {}",cnt);
		
		return "redirect:/OneBoardAdmin.do?eboard_no="+eboard_no;
	}
	
	/**
	 * 관리자가 상세조회에서 게시글을 완전히 삭제하는 기능
	 * @param eboard_no 글번호
	 * @return 게시글 전체조회로 이동
	 */
	@RequestMapping(value = "/deletOne.do", method = RequestMethod.GET)
	public String deletOne(String eboard_no) {
		logger.info("@deletOne 관리자 상세조회에서 게시글 완전삭제: {}",eboard_no );
		int cnt = eService.delEboard(eboard_no);
		logger.info("@deletOne 삭제된 게시글 갯수 : {}", cnt);
		return "redirect:/entrBoardAdmin.do";
	}
	
	/**
	 * 관리자의 자료게시판 관리로 이동
	 * @return 관리자 자료게시판 페이지
	 */
	@RequestMapping(value = "/jaryoBoardAdmin.do", method=RequestMethod.GET)
	public String jaryoBoardAdmin() {
		logger.info("@jaryoBoardAdmin 관리자 자료게시판 이동");
		return "admin/jaryoAdmin";
	}
	
	/**
	 * 관리자의 자료게시글 전체조회
	 * @return 전체조회된 리스트를 json형태로 넘겨줌
	 */
	@RequestMapping(value = "/selJaryoAllAdmin.do", method = RequestMethod.GET,
					produces = "application/html; charset=UTF-8")
	@ResponseBody
	public String selJaryoAllAdmin() {
		logger.info("@jaryoBoardAdmin 관리자 자료게시판 전체조회");
		List<FileListVo> jList = jService.selJaryoAllAdmin();
		Gson gson = new GsonBuilder().create();
		return gson.toJson(jList);
	}
	
	/**
	 * 선택한 글들의 Delflag를 변경하는 기능 
	 * @param chk 체크박스를 통해서 선택된 글 번호 
	 * @param response JSP에 html 태그를 넘겨주기 위한 HttpServletResponse객체
	 * @throws IOException 
	 */
	@RequestMapping(value="/changeJaryoDel.do", method = RequestMethod.POST)
	public void changeJaryoDel(@RequestParam ArrayList<String> chk, HttpServletResponse response) throws IOException {
		logger.info("@changeJaryoDel 관리자 자료글 숨김/보임 처리 :{}", chk);
		int cnt =jService.updJaryoDelflagAdmin(chk);
		logger.info("@changeJaryoDel 숨김/보임 변경성공 횟수 : {}", cnt);
		
		
		response.setContentType("text/html; charset=utf-8");
		PrintWriter printWriter =response.getWriter();
		
		if(cnt >0) {
			printWriter.println("<script>alert('선택한 자료들의 숨김여부를 변경하였습니다!'); location.href='./jaryoBoardAdmin.do'</script>");
		}else {
			printWriter.println("<script>alert('선택한 자료들의 숨김여부 변경에 실패하였습니다!')</script>");
		}
		
		return;
	}
	
	/**
	 * 선택한 글을 완전히 DB에서 삭제하는 기능
	 * @param chk 체크박스를 통해서 선택한 글 번호 
	 * @param response JSP에 html 태그를 넘겨주기 위한 HttpServletResponse객체
	 * @throws IOException
	 */
	@RequestMapping(value = "/deleteJaryo.do", method = RequestMethod.POST)
	public void deleteJaryo(@RequestParam ArrayList<String> chk, HttpServletResponse response) throws IOException{
		logger.info("deleteJaryo 관리자 자료글 삭제 :{}",chk);
		int cnt = jService.delJaryo(chk);
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter printWriter = response.getWriter();
		
		if(cnt>0) {
			printWriter.println("<script>alert('선택한 자료들을 완전히 삭제하였습니다!'); location.href='./jaryoBoardAdmin.do'</script>");
		}else {
			printWriter.println("<script>alert('선택한 자료들 삭제에 실패하였습니다.');location.href='./jaryoBoardAdmin.do'</script>");
		}
		
	}
	

	
}
