package com.doit.gw.ctrl.ann;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doit.gw.service.ann.IAnnService;
import com.doit.gw.vo.ann.AnnualVo;



@Controller
public class AnnualController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IAnnService service;
	
	@RequestMapping(value = "/annualAdmin.do", method = RequestMethod.GET)
	public String annualAdmin(String dept_no, Model model) {
		logger.info("AnnualController annualAdmin : {}", dept_no);
		if(dept_no != null) {
			List<AnnualVo> annLists = service.selAnnualAdmin(dept_no);
			logger.info("AnnualController annualAdmin : {}",  annLists);
			model.addAttribute("lists", annLists);
		}
		model.addAttribute("dept_no", dept_no);
		return "/admin/annualAdmin";
	}
	
}
