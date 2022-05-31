package com.doit.gw.ctrl.entr;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
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

@Controller
public class EntrBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IEntrService service;
	
	@RequestMapping(value="/entrBoard.do", method = RequestMethod.GET)
	public String entrBoard(Model model) {
		logger.info("EntrBoardController entrBoard 공지게시판으로 이동");
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
		logger.info("EntrBoardController cgoryBoard 공지게시판 카테고리별 조회 : {}", map);
		List<EntrBoardVo> eList = service.selEboardCgoryUser(map);
		
		Gson data = new GsonBuilder().create();
		return data.toJson(eList);

	}
	
	@RequestMapping(value = "/OneBoard.do", method = RequestMethod.GET)
	public String OneBoard(String eboard_no, Model model) {
		logger.info("EntrBoardController OneBoard 공지게시글 상세조회 : {}", eboard_no);
		EntrBoardVo entrOne = service.selEboardDetail(eboard_no);
		model.addAttribute("entrOne", entrOne);
		return "/entr/entrDetail";
	}
	
	@RequestMapping(value = "/entrBoardAdmin.do", method = RequestMethod.GET)
	public String entrBoardAdmin() {
		logger.info("EntrBoardController entrBoardAdmin 관리자 게시판관리 이동");
		return "/admin/entrBoardAdmin";
	}
	
	@RequestMapping(value = "/selEboardAllAdmin.do", method = RequestMethod.POST)
	@ResponseBody
	public List<EntrBoardVo> selEboardAllAdmin() {
		logger.info("EntrBoardCtrl selEboardAllAdmin 관리자 공지게시판 전체조회");
		List<EntrBoardVo> data = service.selEboardAllAdmin();
		return data;
	}
	
	@RequestMapping(value = "/selEboardCgoryAdmin.do", method = RequestMethod.GET,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String selEboardCgoryAdmin(@RequestParam Map<String, Object> map) {
		logger.info("EntrBoardController selEboardCgoryAdmin 관리자 공지게시판 카테고리별 조회 : {}", map);
		List<EntrBoardVo> lists = service.selEboardCgoryAdmin(map);
		Gson data = new GsonBuilder().create();
		return data.toJson(lists);
	}
	
	@RequestMapping(value = "/OneBoardAdmin.do", method = RequestMethod.GET)
	public String OneBoardAdmin(String eboard_no, Model model) {
		logger.info("EntrBoardController OneBoardAdmin 관리자 공지게시글 상세조회 : {}",eboard_no);
		EntrBoardVo entrOne = service.selEboardDetail(eboard_no);
		model.addAttribute("entrOne", entrOne);
		return "/admin/oneBoardAdmin";
	}
	
	@RequestMapping(value = "/delflag.do", method = RequestMethod.GET)
	public String delflag(String eboard_no) {
		logger.info("EntrBoardController delflag 사용자 게시글 삭제처리 : {}", eboard_no);
		int cnt = service.updEboardDelflagUser(eboard_no);
		logger.info("EntrBoardController delflag 삭제갯수 : {} 개", cnt);
		return "redirect:/entrBoard.do";
	}
	
	@RequestMapping(value = "/insertBoard.do", method = RequestMethod.GET)
	public String insertBoard() {
		logger.info("EntrBoardController insertBoard 글쓰기 페이지로 이동");
		return "/entr/entrInsert";
	}

}
