package com.doit.gw.ctrl.emp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/loginPage.do", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
		logger.info("LoginController loginPage");
		if(error != null) {
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
		}
		return "loginPage";
	}

	
	@RequestMapping(value = "/gohome.do", method = RequestMethod.GET)
	public String gohome(Authentication user, Model model) {
		logger.info("LoginController gohome");
		return "home";
	}
	
	@RequestMapping(value = "/nfcRead.do")
	public String nfcRead() {
	
		return "nfcRead";
	}
}
