package com.doit.gw.ctrl.entr;

import org.slf4j.Logger;


import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * @since 2022.06.14
 * @author 오지혜
 * 사용자의 통합검색 기능을 위한 Controller
 */
@Controller
@RequestMapping("/comm")
public class SearchBoardController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 사용자의 통합검색 페이지로 이동 
	 * @return 통함검색 페이지 jsp
	 */
	@RequestMapping(value = "/unifiedSearch.do", method = RequestMethod.GET)
	public String unifiedSearch() {
		logger.info("@unifiedSearch 통합검색 페이지로 이동");
		return "search/unifiedSearch";
	}

}
