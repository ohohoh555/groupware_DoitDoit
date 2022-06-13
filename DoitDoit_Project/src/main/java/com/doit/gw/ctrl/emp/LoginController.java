package com.doit.gw.ctrl.emp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doit.gw.vo.emp.EmpVo;

@Controller
@RequestMapping("/comm")
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private List<String> loginMem = new ArrayList<String>();
	
	public List<String> getLoginMem() {
		return loginMem;
	}

	public void setLoginMem(List<String> loginMem) {
		this.loginMem = loginMem;
	}

	@RequestMapping(value = "/loginPage.do", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
		logger.info("LoginController loginPage");
		
		if(error != null) {
			model.addAttribute("msg", "아이디 또는 비밀번호를 확인하세요");
		}
		return "loginPage";
	}

	
	@RequestMapping(value = "/gohome.do", method = RequestMethod.GET)
	public String gohome(Authentication user, Model model){
		System.out.println("Principal 객체 :" + user.getPrincipal());
		EmpVo eVo = (EmpVo)user.getPrincipal();
		
		logger.info("goHome 접속 멤버 : {}",eVo.getEmp_id());
		
		if(eVo.getEmp_auth().equals("ROLE_ADMIN_INSA") || eVo.getEmp_auth().equals("ROLE_ADMIN_BOARD")) {
			logger.info("LoginController gohome");
			return "home";
		}
		
		// 사용자의 정보에 휴대폰 번호가 등록되어 있지 않을 경우
		// 문자 발송 페이지로 이동 2022.05.31
		// 다른분들 작업때문에 임시로 주석 처리
		
		if(eVo.getEmp_phone() == null) {
			logger.info("LoginController sendMsg");
			return "sms/sendMsg";
		}else {
			logger.info("LoginController gohome");
			return "home";
		}

	}
	
	@RequestMapping(value = "/logout.do", method = RequestMethod.GET)
	public String logout() {
		logger.info("LoginController logout");
		return "redirect:/loginPage.do";
	}
	
	@RequestMapping(value = "/accessDenied.do")
	public String accessDenied() {
		return "accessDenied";
	}
}
