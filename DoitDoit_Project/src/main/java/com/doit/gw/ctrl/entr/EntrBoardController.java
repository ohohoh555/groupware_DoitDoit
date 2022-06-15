package com.doit.gw.ctrl.entr;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.entr.IEntrService;
import com.doit.gw.vo.entr.EntrBoardVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 사용자의 공지게시판을 위한 Controller
 * @author 오지혜
 * @since 2022.06.14 
 */
@Controller
@RequestMapping("/comm")
public class EntrBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IEntrService service;
	
	/**
	 *  사용자 로그인하면 첫 홈화면에 뿌려지는 최신글 리스트
	 * @return 숨김여부가 N인 공지게시글을 최신순으로 3개 조회
	 */
	@RequestMapping(value="/resentBoard.do", method = RequestMethod.GET,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String resentBoard() {
		logger.info("@resentBoard 홈화면 최근글 조회");
		List<EntrBoardVo> rList = service.selEboardResent();
		
		Gson data = new GsonBuilder().create();
		return data.toJson(rList);
	}
	
	/**
	 * 카테고리 중 필독을 제외하고 Delflag가 N인 공지게시글 목록 전체조회 + 필독인 공지게시글 목록 3개 조회
	 * @param model 
	 * @return 공지게시판 화면
	 */
	@RequestMapping(value="/entrBoard.do", method = RequestMethod.GET)
	public String entrBoard(Model model) {
		logger.info("@entrBoard 공지게시판으로 이동");
		List<EntrBoardVo> eList = service.selEboardAllUser();
		List<EntrBoardVo> fList = service.selEboardFildocThree();
		
		model.addAttribute("eList", eList);
		model.addAttribute("fList", fList);
		
		return "/entr/entrBoard";
	}
	
	/**
	 * 사용자 공지게시판에서 카테고리별 전체조회
	 * @param map {cgory : 카테고리번호}
	 * @return
	 */
	@RequestMapping(value="/cgoryBoard.do", method = RequestMethod.GET,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String cgoryBoard(@RequestParam Map<String,Object>map) {
		logger.info("@cgoryBoard 공지게시판 카테고리별 조회 : {}", map);
		List<EntrBoardVo> eList = service.selEboardCgoryUser(map);
		
		Gson data = new GsonBuilder().create();
		return data.toJson(eList);

	}
	
	/**
	 * 카테고리 중 필독게시글 전체보기
	 * @return 필독 게시글 전체조회
	 */
	@RequestMapping(value = "/FildocAll.do", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<EntrBoardVo> FildocAll(){
		logger.info("@FildocAll 공지게시판 필독전체보기 클릭");
		List<EntrBoardVo> data= service.selEboardFildocAll();
		return data;
	}
	
	/**
	 * 홈화면에서 뿌려지는 Delflag가 N인 필독게시글을 최신순으로 3개 조회
	 * @return 조회된 필독게시글 3개 리스트
	 */
	@RequestMapping(value = "/FildocThree.do", method = RequestMethod.GET,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String FildocThree() {
		logger.info("@FildocThree 공지게시판 필독3개보기");
		List<EntrBoardVo> fList = service.selEboardFildocThree();
		
		Gson data = new GsonBuilder().create();
		return data.toJson(fList);
		
	}
	
	/**
	 * 사용자의 게시글 상세조회
	 * @param eboard_no 선택된 글번호
	 * @param model 선택된 글의 상세조회된 내용을 전달하기 위한 model 객체 
	 * @return 상세조회 페이지
	 */
	@RequestMapping(value = "/OneBoard.do", method = RequestMethod.GET)
	public String OneBoard(String eboard_no, Model model) {
		logger.info("@OneBoard 공지게시글 상세조회 : {}", eboard_no);
		EntrBoardVo entrOne = service.selEboardDetail(eboard_no);
		model.addAttribute("entrOne", entrOne);
		return "/entr/entrDetail";
	}
	

	/**
	 * 사용자가 상세조회에서 게시글 삭제처리
	 * 실제로 DB삭제되지는 않고, Delflag가 Y->N 으로 변경처리되면서 전체조회에서 확인할 수 없게함
	 * @param eboard_no 글번호
	 * @return 전체조회로 이동
	 */
	@RequestMapping(value = "/delflag.do", method = RequestMethod.GET)
	public String delflag(String eboard_no) {
		logger.info("@delflag 사용자 게시글 삭제처리 : {}", eboard_no);
		int cnt = service.updEboardDelflagUser(eboard_no);
		logger.info("EntrBoardController delflag 삭제갯수 : {} 개", cnt);
		return "redirect:./entrBoard.do";
	}
	
	/**
	 * 사용자의 입력페이지로 이동 
	 * @return 입력페이지
	 */
	@RequestMapping(value = "/insertBoard.do", method = RequestMethod.GET)
	public String insertBoard() {
		logger.info("@insertBoard 글쓰기 페이지로 이동");
		return "/entr/entrInsert";
	}
	
	/**
	 * 입력페이지에서 입력받은 데이터를 실제 DB에 insert하는 기능 
	 * @param map 입력받은 값
	 * @return 전체조회로 이동 
	 */
	@RequestMapping(value = "/insertFrm.do", method = RequestMethod.POST)
	public String insertFrm(/*EntrBoardVo eVo*/ @RequestParam Map<String, Object>map) {
		logger.info("@insertFrm 새글입력 및 저장 : {}", map);
		String cgory_no = (String) map.get("cgory_no");
		String cald_start=(String) map.get("cald_start");
		String cald_end = (String) map.get("cald_end");
		int cnt=0;
		if(cgory_no.equals("302")) {
			map.put("cald_start", cald_start.replace("T", " "));
			map.put("cald_end", cald_end.replace("T", " "));
			cnt = service.insEboardCald(map);
			logger.info("@insertFrm 일정 등록 성공한 횟수: {}", cnt);
		}else {
			cnt = service.insEboardRoot(map);
			logger.info("@insertFrm 새글 입력에 성공한 횟수 : {}", cnt);
		}

		return "redirect:./entrBoard.do";
	}
	
	/**
	 * 글 수정 페이지로 이동 
	 * @param eboard_no 글번호 
	 * @param model 원본글의 상세조회 값을 담을 model객체
	 * @return 글 수정 페이지
	 */
	@RequestMapping(value = "/modifyBoard.do", method = RequestMethod.GET)
	public String modify(String eboard_no, Model model) {
		logger.info("@modify 글수정 페이지로 이동 : {}", eboard_no);
		EntrBoardVo eVo = service.selEboardDetail(eboard_no);
		model.addAttribute("eVo", eVo);
		return "/entr/entrModify";
	}
	
	/**
	 * 사용자로부터 입력받은 값으로 글을 수정하는 기능 
	 * @param map 새로 입력받은 값
	 * @return 수정된 글의 상세조회
	 */
	@RequestMapping(value = "/modifyFrm.do", method = RequestMethod.POST)
	public String modifyFrm(@RequestParam Map<String, Object> map) {
		logger.info("@modifyFrm 수정된 내용 저장 : {}",map);
		String cgory_no = (String) map.get("cgory_no");
		String eboard_no = (String) map.get("eboard_no");
		System.out.println("카테고리"+ cgory_no+","+"글번호"+eboard_no);
		if(cgory_no.equals("302")) {
			int cnt1 = service.updEboardCald(map);
			int cnt2=service.updEboardRoot(map);
			logger.info("@modifyFrm 일정수정완료:{}, 내용수정완료:{}",cnt1,cnt2);
		}else {
			int cnt = service.updEboardRoot(map);
			logger.info("@modifyFrm 게시글 수정완료:{}",cnt);
		}
		
		return "redirect:./OneBoard.do?eboard_no="+eboard_no;
	}


}
