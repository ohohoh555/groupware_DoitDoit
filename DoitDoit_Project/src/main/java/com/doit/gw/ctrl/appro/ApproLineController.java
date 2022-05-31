package com.doit.gw.ctrl.appro;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.appro.IApproLineService;
import com.doit.gw.vo.appro.ApproEmpVo;
import com.doit.gw.vo.emp.EmpVo;
import com.doit.gw.vo.appro.ApproDeptVo;


@Controller
public class ApproLineController {
	
	@Autowired
	private IApproLineService service;

	private static final Logger logger = LoggerFactory.getLogger(ApproLineController.class);

	//문서작성 폼으로 이동
	@RequestMapping(value = "/docWriteForm.do",method = RequestMethod.GET)
	public String docWriteForm() {
		logger.info("============== ApproController docWriteForm으로 이동! ==============");
		return"/appro/docWriteForm";
	}
	
	@RequestMapping(value = "/jstree.do", method = RequestMethod.GET)
	public String jstree() {
		logger.info("============== ApproLineController jstree로 이동! ==============");
		return "/appro/jstree";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/jstreeToJson.do",method = RequestMethod.POST)
	public JSONArray jstreeToJson() {
		logger.info("============== ApproLineController jstreeToJson으로 이동! ==============");
		List<ApproEmpVo> listEmp = service.selEmp();
		List<ApproDeptVo> listDept = service.selDept();
		logger.info("listEmp {} ",listEmp);
		logger.info("listEmp size {} ",listEmp.size());
		logger.info("listDept {} ",listDept);
		logger.info("listDept size {} ",listDept.size());
		JSONArray jsonArr = new JSONArray();
		for (ApproDeptVo approDeptVo : listDept) {
			JSONObject json = new JSONObject();
			json.put("id", approDeptVo.getDept_no());
			json.put("parent", "#");
			json.put("text", approDeptVo.getDept_name());
			json.put("icon","glyphicon glyphicon-folder-open");
			
			jsonArr.add(json);
		}
		
		for (ApproEmpVo approEmpVo : listEmp) {
			JSONObject json = new JSONObject();
			json.put("id", approEmpVo.getEmp_id());
			json.put("parent", (approEmpVo.getDept_no()!= null)?approEmpVo.getDept_no():"00");
			json.put("text", approEmpVo.getEmp_name());
			json.put("icon","glyphicon glyphicon-folder-open");
			json.put("rank", approEmpVo.getRank_no());
			
			jsonArr.add(json);
		}
		logger.info("[jsonArr 값] : {}" ,jsonArr.toJSONString());
		return jsonArr;
		
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/gyuljae.do", method = RequestMethod.POST,  produces = "application/text; charset=UTF-8;")
	public String gyuljae(@RequestParam List<String> emps) {
		logger.info("============== ApproLineController gyuljae 시작! ==============");
		

		String arr[] = emps.toArray(new String[emps.size()]);
		logger.info("[arr 값] : {}" ,Arrays.toString(arr));
		
		int checkSize = 3;
		logger.info("[emps의 사이즈 값] : {}" ,emps.size());
		
		JSONObject json = new JSONObject();
		JSONArray jArray = new JSONArray();
		
		for (int i = 0; i < emps.size(); i+=checkSize) {
			String[] lists = Arrays.copyOfRange(arr, i, Math.min(emps.size(),i+checkSize));
			logger.info("[lists 값] : {}" ,Arrays.toString(lists));
			List<String > aa = Arrays.asList(lists);

			logger.info("[aa 값] : {}" ,aa);
			JSONObject jsonObj = new JSONObject();
			for (int j = 0; j < aa.size(); j++) {
				if(j%3 == 0) {
					jsonObj.put("EMP_ID", aa.get(j));
				}else if(j%3 ==1) {
					jsonObj.put("APPRO_NAME",aa.get(j)); 
				}else if(j%3 ==2) {
					jsonObj.put("APPRO_STATUS","W");
				}
			}
			jArray.add(jsonObj);
			logger.info("[jArray 값] : {}" ,jArray);
			
		}
		
		json.put("approval", jArray);
		logger.info("[json의 진짜! 값] : {}" ,json.toJSONString());

		if(emps != null) {
//			json.toJSONString();
			return json.toJSONString();
		}else {
			return "/appro/docWriteForm";
		}
	}
}
