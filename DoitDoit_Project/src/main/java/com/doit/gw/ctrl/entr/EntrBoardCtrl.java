package com.doit.gw.ctrl.entr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class EntrBoardCtrl {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/entrBoard.do", method = RequestMethod.GET)
	public String entrBoard() {
		logger.info("EntrBoardCtrl entrBoard 공지게시판으로 이동");
		return "entrBoard";
	}

}
