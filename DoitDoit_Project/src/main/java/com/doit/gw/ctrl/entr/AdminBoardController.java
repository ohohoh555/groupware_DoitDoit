package com.doit.gw.ctrl.entr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.board.IEntrService;
import com.doit.gw.service.board.IJaryoService;
import com.doit.gw.vo.entr.EntrBoardVo;
import com.doit.gw.vo.entr.FileListVo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class AdminBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IEntrService eService;
	
	@Autowired
	private IJaryoService jService;
	
	@RequestMapping(value = "/entrBoardAdmin.do", method = RequestMethod.GET)
	public String entrBoardAdmin() {
		logger.info("EntrBoardController entrBoardAdmin 관리자 게시판관리 이동");
		return "/admin/entrBoardAdmin";
	}
	
	@RequestMapping(value = "/selEboardAllAdmin.do", method = {RequestMethod.POST})
	@ResponseBody
	public List<EntrBoardVo> selEboardAllAdmin() {
		logger.info("@selEboardAllAdmin 관리자 공지게시판 전체조회");
		List<EntrBoardVo> data = eService.selEboardAllAdmin();
		return data;
	}
	
	@RequestMapping(value = "/selEboardCgoryAdmin.do", method = RequestMethod.GET,
			produces = "application/json; charset=UTF-8")
	@ResponseBody
	public String selEboardCgoryAdmin(@RequestParam Map<String, Object> map) {
		logger.info("@selEboardCgoryAdmin 관리자 공지게시판 카테고리별 조회 : {}", map);
		List<EntrBoardVo> lists = eService.selEboardCgoryAdmin(map);
		Gson data = new GsonBuilder().create();
		return data.toJson(lists);
	}
	
	
	@RequestMapping(value = "/OneBoardAdmin.do", method = RequestMethod.GET)
	public String OneBoardAdmin(String eboard_no, Model model) {
		logger.info("EntrBoardController OneBoardAdmin 관리자 공지게시글 상세조회 : {}",eboard_no);
		EntrBoardVo entrOne = eService.selEboardDetail(eboard_no);
		model.addAttribute("entrOne", entrOne);
		return "/admin/oneBoardAdmin";
	}
	
	@RequestMapping(value = "/changeDel.do", method = RequestMethod.POST)
	public String changeDel(@RequestParam ArrayList<String> chk) {
		logger.info("@changeDel 관리자 게시글 숨김/보임 처리 : {}", chk);
		int cnt = eService.updEboardDelfAdmin(chk);
		logger.info("@changeDel 숨김/보임 처리 성공횟수 :{}", cnt);
		return "redirect:/entrBoardAdmin.do";
	}
	
	@RequestMapping(value = "/changeDelOne.do", method = RequestMethod.GET)
	public String changeDelOne(String eboard_no) {
		logger.info("@changeDelOne 관리자 상세조회에서 숨김/보임처리: {}",eboard_no);
		List<String> chk = new ArrayList<String>();
		chk.add(eboard_no);
		int cnt = eService.updEboardDelfAdmin(chk);
		logger.info("@changeDelOne 숨김/보임 처리 성공횟수: {}",cnt);
		
		return "redirect:/OneBoardAdmin.do?eboard_no="+eboard_no;
	}
	
	@RequestMapping(value = "/deletOne.do", method = RequestMethod.GET)
	public String deletOne(String eboard_no) {
		logger.info("@deletOne 관리자 상세조회에서 게시글 완전삭제: {}",eboard_no );
		int cnt = eService.delEboardRoot(eboard_no);
		logger.info("@deletOne 삭제된 게시글 갯수 : {}", cnt);
		return "redirect:/entrBoardAdmin.do";
	}
	
	@RequestMapping(value = "/jaryoBoardAdmin.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> jaryoBoardAdmin() {
		logger.info("@jaryoBoardAdmin 관리자 자료게시판 전체조회");
		List<FileListVo> jList = jService.selJaryoAllAdmin();
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("jList", jList);
		return map;
	}
	
}
