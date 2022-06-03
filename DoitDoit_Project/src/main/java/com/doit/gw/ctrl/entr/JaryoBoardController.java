package com.doit.gw.ctrl.entr;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
