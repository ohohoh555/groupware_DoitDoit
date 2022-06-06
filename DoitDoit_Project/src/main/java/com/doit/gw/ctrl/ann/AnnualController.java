package com.doit.gw.ctrl.ann;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doit.gw.service.ann.IAnnService;
import com.doit.gw.vo.ann.AnnAddVo;
import com.doit.gw.vo.ann.AnnUseVo;
import com.doit.gw.vo.ann.AnnualVo;

@Controller
public class AnnualController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IAnnService service;

	/*
	 * 관리자 연차 조회
	 */
	@RequestMapping(value = "/annualAdmin.do", method = RequestMethod.GET)
	public String annualAdmin(String dept_no, Model model) {
		logger.info("AnnualController annualAdmin : {}", dept_no);
		if (dept_no != null) {
			List<AnnualVo> annLists = service.selAnnualAdmin(dept_no);
			logger.info("AnnualController annualAdmin : {}", annLists);
			model.addAttribute("lists", annLists);
		}
		model.addAttribute("dept_no", dept_no);
		return "/admin/annualAdmin";
	}

	/*
	 * 관리자 연차 부여
	 */
	@RequestMapping(value = "/annualAdd.do", method = RequestMethod.POST)
	public void annualAdd(@RequestParam Map<String, Object> map, HttpServletResponse response) throws IOException {
		logger.info("AnnualController annualAdd : {}", map);
		String emp_id = String.valueOf(map.get("emp_id"));
		String ann_add_content = String.valueOf(map.get("ann_add_content"));
		String ann_add_days = String.valueOf(map.get("ann_add_days"));
		AnnAddVo annAddVo = new AnnAddVo();
		String[] emp_ids = emp_id.split(",");
		for (String empId : emp_ids) {
			annAddVo.setEmp_id(empId);
			annAddVo.setAnn_add_content(ann_add_content);
			annAddVo.setAnn_add_days(ann_add_days);
			service.insAnnAdd(annAddVo);
		}
		Map<String, Object> updMap = new HashMap<String, Object>();
		updMap.put("emp_ids", emp_ids);
		updMap.put("ann_add_days", ann_add_days);
		service.updAnnualAdd(updMap);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>alert('연차가 부여되었습니다.');location.href='./annualAdmin.do';</script>");
		out.flush();
	}
	
	/*
	 * 사원 연차 조회
	 */
	@RequestMapping(value = "/annual.do", method = RequestMethod.GET)
	public String annual(Principal principal, Model model) {
		logger.info("AnnualController annual : {}", principal.getName());
		String emp_id = principal.getName();
		AnnualVo annualVo = service.selAnnualEmp(emp_id);
		model.addAttribute("annualVo", annualVo);
		List<AnnAddVo> annAddVo = service.selAnnAddEmp(emp_id);
		model.addAttribute("annAddVo", annAddVo);
		List<AnnUseVo> annUseVo = service.selAnnUseEmp(emp_id);
		model.addAttribute("annUseVo", annUseVo);
		return "/ann/annual";
	}
	
	/*
	 * 출퇴근 등록
	 */
	@RequestMapping(value = "/annualWork.do", method = RequestMethod.POST)
	public void annualWork(String emp_nfc, HttpServletResponse response) throws IOException {
		logger.info("AnnualController annualWork : {}", emp_nfc);
		service.updAnnualWorkIn(emp_nfc);
		service.updAnnualWorkOut(emp_nfc);
		service.updAnnualWorkDays(emp_nfc);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("<script>");
		out.print("alert('출/퇴근이 등록되었습니다.');");
		out.print("location.href='./loginPage.do';");
		out.print("</script>");
		out.flush();
	}
}
