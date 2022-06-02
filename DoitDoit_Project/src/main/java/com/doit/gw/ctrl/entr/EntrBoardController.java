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
	
	@RequestMapping(value = "/entrBoardAdmin.do", method = RequestMethod.GET)
	public String entrBoardAdmin() {
		logger.info("EntrBoardController entrBoardAdmin 관리자 게시판관리 이동");
		return "/admin/entrBoardAdmin";
	}
	
	@RequestMapping(value = "/selEboardAllAdmin.do", method = {RequestMethod.POST})
	@ResponseBody
	public List<EntrBoardVo> selEboardAllAdmin() {
		logger.info("@selEboardAllAdmin 관리자 공지게시판 전체조회");
		List<EntrBoardVo> data = service.selEboardAllAdmin();
		return data;
	}
	
	@RequestMapping(value = "/selEboardCgoryAdmin.do", method = RequestMethod.GET,
					produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String selEboardCgoryAdmin(@RequestParam Map<String, Object> map) {
		logger.info("@selEboardCgoryAdmin 관리자 공지게시판 카테고리별 조회 : {}", map);
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
		int cnt = service.insEboardRoot(eVo);
		logger.info("@insertFrm 새글 입력에 성공한 횟수 : {}", cnt);
		return "redirect:/entrBoard.do";
	}
	
	@RequestMapping(value = "/modify.do", method = RequestMethod.GET)
	public String modify(String eboard_no, Model model) {
		logger.info("@modify 글수정 페이지로 이동 : {}", eboard_no);
		EntrBoardVo eVo = service.selEboardDetail(eboard_no);
		model.addAttribute("eVo", eVo);
		return "/entr/entrModify";
	}
	
	@RequestMapping(value = "/changeDel.do", method = RequestMethod.POST)
	public String changeDel(@RequestParam ArrayList<String> chk) {
		logger.info("@changeDel 관리자 게시글 숨김/보임 처리 : {}", chk);
		int cnt = service.updEboardDelfAdmin(chk);
		logger.info("@changeDel 숨김/보임 처리 성공횟수 :{}", cnt);
		return "redirect:/entrBoardAdmin.do";
	}
	
	@RequestMapping(value = "/changeDelOne.do", method = RequestMethod.GET)
	public String changeDelOne(String eboard_no) {
		logger.info("@changeDelOne 관리자 상세조회에서 숨김/보임처리: {}",eboard_no);
		List<String> chk = new ArrayList<String>();
		chk.add(eboard_no);
		int cnt = service.updEboardDelfAdmin(chk);
		logger.info("@changeDelOne 숨김/보임 처리 성공횟수: {}",cnt);
		
		return "redirect:/OneBoardAdmin.do?eboard_no="+eboard_no;
	}
	
	@RequestMapping(value = "/deletOne.do", method = RequestMethod.GET)
	public String deletOne(String eboard_no) {
		logger.info("@deletOne 관리자 상세조회에서 게시글 완전삭제: {}",eboard_no );
		int cnt = service.delEboardRoot(eboard_no);
		logger.info("@deletOne 삭제된 게시글 갯수 : {}", cnt);
		return "redirect:/entrBoardAdmin.do";
	}

}
