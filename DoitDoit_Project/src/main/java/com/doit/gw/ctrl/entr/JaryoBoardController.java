package com.doit.gw.ctrl.entr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class JaryoBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/jaryoBoard.do", method = RequestMethod.GET)
	public String jaryoBoard() {
		logger.info("@jaryoBoard 자료게시판 이동");
		return "/jaryo/jaryoBoard";
	}

}
