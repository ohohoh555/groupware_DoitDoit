package com.doit.gw.ctrl.entr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SearchBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/unifiedSearch.do", method = RequestMethod.GET)
	public String unifiedSearch() {
		logger.info("@unifiedSearch 통합검색 페이지로 이동");
		return "search/unifiedSearch";
	}

}
