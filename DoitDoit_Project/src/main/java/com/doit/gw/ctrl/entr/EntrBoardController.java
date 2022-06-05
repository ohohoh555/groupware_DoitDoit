package com.doit.gw.ctrl.entr;

import java.util.ArrayList;
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

import com.doit.gw.service.board.IEntrService;
import com.doit.gw.vo.entr.EntrBoardVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class EntrBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IEntrService service;
	
	@RequestMapping(value="/entrBoard.do", method = RequestMethod.GET)
	public String entrBoard(Model model) {
		logger.info("@entrBoard 공지게시판으로 이동");
		List<EntrBoardVo> eList = service.selEboardAllUser();
		List<EntrBoardVo> fList = service.selEboardFildocThree();
		
		model.addAttribute("eList", eList);
		model.addAttribute("fList", fList);
		
		return "/entr/entrBoard";
	}
	
	@RequestMapping(value="/cgoryBoard.do", method = RequestMethod.GET,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String cgoryBoard(@RequestParam Map<String,Object>map) {
		logger.info("@cgoryBoard 공지게시판 카테고리별 조회 : {}", map);
		List<EntrBoardVo> eList = service.selEboardCgoryUser(map);
		
		Gson data = new GsonBuilder().create();
		return data.toJson(eList);

	}
	
	@RequestMapping(value = "/OneBoard.do", method = RequestMethod.GET)
	public String OneBoard(String eboard_no, Model model) {
		logger.info("@OneBoard 공지게시글 상세조회 : {}", eboard_no);
		EntrBoardVo entrOne = service.selEboardDetail(eboard_no);
		model.addAttribute("entrOne", entrOne);
		return "/entr/entrDetail";
	}
	

	@RequestMapping(value = "/delflag.do", method = RequestMethod.GET)
	public String delflag(String eboard_no) {
		logger.info("@delflag 사용자 게시글 삭제처리 : {}", eboard_no);
		int cnt = service.updEboardDelflagUser(eboard_no);
		logger.info("EntrBoardController delflag 삭제갯수 : {} 개", cnt);
		return "redirect:/entrBoard.do";
	}
	
	@RequestMapping(value = "/insertBoard.do", method = RequestMethod.GET)
	public String insertBoard() {
		logger.info("@insertBoard 글쓰기 페이지로 이동");
		return "/entr/entrInsert";
	}
	
	@RequestMapping(value = "/insertFrm.do", method = RequestMethod.POST)
	public String insertFrm(EntrBoardVo eVo) {
		logger.info("@insertFrm 새글입력 및 저장 : {}", eVo);
		String Cgory_no = eVo.getCgory_no();
		String cald_start=eVo.getCald_start();
		String cald_end = eVo.getCald_end();
		int cnt=0;
		if(Cgory_no.equals("302")) {
			eVo.setCald_start(cald_start.replace("T", " "));
			eVo.setCald_end(cald_end.replace("T", " "));
			cnt = service.insEboardCald(eVo);
			logger.info("@insertFrm 일정 등록 성공한 횟수: {}", cnt);
		}else {
			cnt = service.insEboardRoot(eVo);
			logger.info("@insertFrm 새글 입력에 성공한 횟수 : {}", cnt);
		}

		return "redirect:/entrBoard.do";
	}
	
	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String modify(String eboard_no, Model model) {
		logger.info("@modify 글수정 페이지로 이동 : {}", eboard_no);
		EntrBoardVo eVo = service.selEboardDetail(eboard_no);
		model.addAttribute("eVo", eVo);
		return "/entr/entrModify";
	}
	

	@RequestMapping(value = "/FildocAll.do", method = RequestMethod.GET,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public List<EntrBoardVo> FildocAll(){
		logger.info("@FildocAll 공지게시판 필독전체보기 클릭");
		List<EntrBoardVo> data= service.selEboardFildocAll();
		return data;
	}

}
