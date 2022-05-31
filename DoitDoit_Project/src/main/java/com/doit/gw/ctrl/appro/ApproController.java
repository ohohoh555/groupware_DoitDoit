	package com.doit.gw.ctrl.appro;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.appro.IApproService;
import com.doit.gw.service.appro.IDocFormService;
import com.doit.gw.service.emp.EmpServiceImpl;
import com.doit.gw.service.emp.IEmpService;
import com.doit.gw.vo.appro.ApproVo;
import com.doit.gw.vo.appro.DocFormVo;
import com.doit.gw.vo.emp.EmpVo;

@Controller
public class ApproController {
	
	@Autowired
	private IApproService service;
	
	@Autowired
	private IDocFormService docService;
	
	private static final Logger logger = LoggerFactory.getLogger(ApproController.class);
	
	//양식 템플릿 선택
	@ResponseBody
	@RequestMapping(value = "/selDocForm.do",method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
	public String selDocForm(String doc_form_no) {
		logger.info("============== ApproController selDocForm 선택 시작! ==============");
		DocFormVo dfVo = docService.selDocForm(doc_form_no);
		String docForm = dfVo.getDoc_form_template();
		return docForm;
	}

	//문서함 메인 화면으로 이동
	@RequestMapping(value = "/approMain.do",method = RequestMethod.GET)
	public String approMain(Principal principal,Model model) {
		logger.info("============== ApproController approMain으로 이동! ==============");
		String emp_id = principal.getName();
//		int int_emp_id = Integer.parseInt(emp_id);
		model.addAttribute("emp_id",emp_id);
		return"/appro/approMain";
	}
	
	//기안문서 작성 인서트
	@RequestMapping(value = "/approval.do",method = RequestMethod.POST)
	public String insApproval(ApproVo aVo,@RequestParam(value = "appro_line") String appro_line,Principal principal) {
		logger.info("============== ApproController insApproval 시작! ==============");
		logger.info("[aVo 값] : {}" ,aVo);
		logger.info("[appro_line 값] : {}" ,appro_line);
		
		String emp_id = principal.getName();
		int int_emp_id = Integer.parseInt(emp_id);
		aVo.setEmp_id(int_emp_id);
		
		//기안문서 insert
		ApproVo approVo = service.insApproval(aVo);
		approVo.setAppro_no(aVo.getAppro_no());
		approVo.setAppro_line(appro_line);
		int cnt = approVo.getAppro_line_no();
		String str = approVo.getAppro_no();
		logger.info("[cnt 값] : {}" ,cnt);
		logger.info("[str 값] : {}" ,str);
		logger.info("[approVo 값] : {}" ,approVo);
		
		//결재자 리스트 insert
		service.insApproLine(approVo);
		return "/appro/approMain";
	}
	
	//임시저장
	@RequestMapping(value = "/draft.do",method = RequestMethod.POST)
	public String insApproDraft(ApproVo aVo) {
		logger.info("============== ApproController insApproDraft 시작! ==============");
		service.insApproDraft(aVo);
		logger.info("[aVo 값] : {}" ,aVo);
		return "/appro/approMain";
	}
	
	//결재예정문서 조회
	@RequestMapping(value = "/docAllList.do", method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
	@ResponseBody
	public String selAllDocument(Principal principal,String emp_id) {
		logger.info("ApprovalController selAllDocument 실행");
//		String emp_id = principal.getName();
		int int_emp_id = Integer.parseInt(emp_id);
		logger.info("[int_emp_id 값] : {}" ,int_emp_id);
		List<Map<String, Object>> List = service.selAllDocument(int_emp_id);
		logger.info("[List 첫번째 값] : {}" ,List.get(0));
		logger.info("[List size 값] : {}" ,List.size());
		
		List<Object> approLists = new ArrayList<Object>();
		JSONArray jsonArr = new JSONArray();
		for(int i=0; i<List.size(); i++) {
			Map<String, Object> map = List.get(i); //
//			map.get("APPRO_NO"); //문서번호 뽑아오기
			String rm = ""+ map.get("RM"); //결재순위ROW_NUMBER 
			String status = (String)map.get("APPRO_STATUS");//문서 상태 뽑아오기 (W:대기,Y:결재,N:반려)
			logger.info("[rm 값] : {}" ,rm);
			logger.info("[status 값] : {}" ,status);
			logger.info("[status 값 true여부] : {}" ,!status.equals("N")?"true":"flase");
			if(!status.equals("N")) { //대기 상태이라면
				if(rm.equals("1")) {
					if(status.equals("W")) {
						String appro_no = (String)map.get("APPRO_NO");
						logger.info("[appro_no 값] : {}" ,appro_no);
						jsonArr.add(service.selOneDocument(appro_no));
					}
					break;
				}else if(rm.equals("2")) {
					if(status.equals("W")) {
						String appro_no = (String)map.get("APPRO_NO");
						logger.info("[appro_no 값] : {}" ,appro_no);
						jsonArr.add(service.selOneDocument(appro_no));
//						approLists.add(service.selOneDocument(appro_no));
					}
					break;
				}else if(rm.equals("3")) {
					if(status.equals("W")) {
						String appro_no = (String)map.get("APPRO_NO");
						logger.info("[appro_no 값] : {}" ,appro_no);
						jsonArr.add(service.selOneDocument(appro_no));
//						approLists.add(service.selOneDocument(appro_no));
					}
					break;
				}else if(rm.equals("4")) {
					if(status.equals("W")) {
						String appro_no = (String)map.get("APPRO_NO");
						logger.info("[appro_no 값] : {}" ,appro_no);
						jsonArr.add(service.selOneDocument(appro_no));
//						approLists.add(service.selOneDocument(appro_no));
					}
					break;
				}
//				logger.info("[approLists 값] : {}" ,approLists.toArray());
				logger.info("[jsonArr 값] : {}" ,jsonArr.toJSONString());
			}
			
			
		}
		
		return jsonArr.toJSONString();
	}
}
