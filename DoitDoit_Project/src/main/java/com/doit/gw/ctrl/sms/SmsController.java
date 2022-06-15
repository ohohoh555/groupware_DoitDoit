package com.doit.gw.ctrl.sms;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.emp.EmpServiceImpl;
import com.doit.gw.service.sms.SmsService;
import com.doit.gw.vo.emp.EmpVo;


@Controller
@RequestMapping(value = "/comm")
public class SmsController {
	
	@Autowired
	private SmsService service;
	
	@Autowired
	private EmpServiceImpl eService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/sendSMS.do",method = RequestMethod.POST)
	@ResponseBody
	public String sendSMS(String phoneNumber) {
	
        Random rand  = new Random(); // 인증번호를 위한 난수 생성
        String numStr = "";
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr+=ran;
        }

        System.out.println("수신자 번호 : " + phoneNumber);
        System.out.println("인증번호 : " + numStr);
        service.certifiedPhoneNumber(phoneNumber,numStr);
        return numStr;
    }
	
	@RequestMapping(value = "/updPassPhoneGo.do")
	public String updPassPhoneGo(Authentication user) {
		logger.info("SmsController updPassPhone 비밀번호/휴대폰 번호 설정 페이지로 이동");
		logger.info("2. principal : " + user.getPrincipal());
		
		return "sms/updPassPhone";
	}
	
	@RequestMapping(value = "/updPassPhone.do", method = RequestMethod.POST)
	public String updPassPhone(EmpVo eVo, Principal principal) {
		Map<String, Object> empMap = new HashMap<String, Object>();
		empMap.put("emp_password", eVo.getEmp_password());
		empMap.put("emp_phone", eVo.getEmp_phone());
		empMap.put("emp_id", principal.getName());
		eService.updPassPhone(empMap);
		return "loginPage";
	}

	
}
