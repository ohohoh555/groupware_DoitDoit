package com.doit.gw.ctrl.emp;

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

import com.doit.gw.service.ann.IAnnService;
import com.doit.gw.service.emp.IEmpService;
import com.doit.gw.service.sign.ISignService;
import com.doit.gw.vo.emp.EmpVo;
import com.doit.gw.vo.sign.SignVo;

@Controller
@RequestMapping("/emp")
public class EmpController {
	
	@Autowired
	private IEmpService service;
	
	//연차 등록을 위한 service
	@Autowired
	private IAnnService annService;
	
	//디폴트 사인이미지 등록을 위한 service
	@Autowired
	private ISignService signService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/selAllEmp.do", method = RequestMethod.GET)
	public String selEmpAll(Model model) {
		logger.info("EmpController selEmpAll 회원 전체 조회");
		List<EmpVo> lists = service.selEmpAll();
		logger.info("lists : " + lists);
		model.addAttribute("lists", lists);
		return "emp/selEmpAll";
	}
	
	@RequestMapping(value = "/insEmpPage.do", method = RequestMethod.GET)
	public String insEmpPage() {
		logger.info("EmpController insEmpPage");
		return "emp/insEmpPage";
	}
	
	@RequestMapping(value = "/insEmp.do", method = RequestMethod.POST)
	public String insEmp(EmpVo vo) {
		logger.info("EmpController insEmp 전달 받은 값 : " + vo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("emp_name", vo.getEmp_name());
		map.put("emp_email", vo.getEmp_email());
		map.put("dept_no", vo.getDept_no());
		map.put("rank_no", vo.getRank_no());
		map.put("emp_address", vo.getEmp_address());
		map.put("emp_nfc", vo.getEmp_nfc());
		logger.info("map 에 담긴 정보" + map);
		service.insEmp(map);
		//연차 등록
		annService.insAnnual();
		//서명이미지 등록
		SignVo signVo = new SignVo();
		int emp_id = Integer.parseInt(vo.getEmp_id());
		signVo.setEmp_id(emp_id);
		signService.insDefaultSign(signVo);
		
		return "redirect:/selAllEmp.do";
	}
	
	@RequestMapping(value = "/selEmpDetail.do", method = RequestMethod.GET)
	public String selEmpDetail(String emp_id, Model model) {
		logger.info("전달 받은 emp_id : " + emp_id);
		List<EmpVo> lists = service.selEmpDetail(emp_id);
		model.addAttribute("lists", lists);
		return "emp/selEmpDetail";
	}
	
	@RequestMapping(value = "/resetPassword.do", method = RequestMethod.GET)
	@ResponseBody
	public String resetPassword(String emp_id) {
		logger.info("전달받은 emp_id : " + emp_id);
		int n = service.resetPassword(emp_id);
		return (n>0 ? "true":"false");
	}
	
	@RequestMapping(value = "/upEmpDetail.do", method = RequestMethod.POST)
	public String upEmpDetail(String emp_id, Model model) {
		logger.info("EmpController upEmpDetail 회원 정보수정 이동");
		List<EmpVo> lists = service.selEmpDetail(emp_id);
		model.addAttribute("lists", lists);
		return "emp/upEmpDetail";
	}
	
	@RequestMapping(value = "/upEmp.do", method = RequestMethod.POST)
	public String upEmp(EmpVo eVo) {
		logger.info("전달받은 수정 값 : " + eVo);
		Map<String, Object> eMap = new HashMap<String, Object>();
		eMap.put("emp_id", eVo.getEmp_id());
		eMap.put("emp_email", eVo.getEmp_email());
		eMap.put("dept_no", eVo.getDept_no());
		eMap.put("rank_no", eVo.getRank_no());
		eMap.put("emp_regdate", eVo.getEmp_regdate());
		eMap.put("emp_address", eVo.getEmp_address());
		service.upEmp(eMap);
		return "redirect:/selAllEmp.do";
	}
	
	@RequestMapping(value = "/nfcCheck.do", method = RequestMethod.POST, produces = "application/text; charset=utf-8;")
	@ResponseBody
	public String nfcCheck(@RequestParam String emp_nfc) {
		logger.info("EmpController nfcCheck 전달받은 nfc 값 : " + emp_nfc);
		//숫자 판별
		boolean isNumeric =  emp_nfc.matches("[+-]?\\d*(\\.\\d+)?");
		if(!isNumeric) {
			return "문자";
		}else {
			int n = service.selEmpNfcCheck(emp_nfc);
			if(n==0) {
				return "0";
			}else {
				return "1";
			}
		}
	}
	
}
