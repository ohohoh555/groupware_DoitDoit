	package com.doit.gw.ctrl.appro;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
import com.doit.gw.vo.appro.ApproVo;
import com.doit.gw.vo.appro.DocFormVo;

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
	public String insApproval(ApproVo aVo,@RequestParam(value = "appro_line") String appro_line,int emp_id) {
		logger.info("============== ApproController insApproval 시작! ==============");
		logger.info("[aVo 값] : {}" ,aVo);
		logger.info("[appro_line 값] : {}" ,appro_line);
		
	//	String emp_id = principal.getName();
	//	int int_emp_id = Integer.parseInt(emp_id);
		aVo.setEmp_id(emp_id);
		
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
	
	//결재문서 조회(기안자)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/myDocList.do", method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
	public String selMyDocument(String emp_id) {
		logger.info("ApprovalController selMyDocument 실행");
		int int_emp_id = Integer.parseInt(emp_id);
		logger.info("[int_emp_id 값] : {}" ,int_emp_id);
		List<ApproVo> List = service.selMyDocument(int_emp_id);
		logger.info("[List size 값] : {}" ,List.size());
		JSONObject json = new JSONObject();
		JSONArray jArr = new JSONArray();
		for (int i = 0; i < List.size(); i++) {
			JSONObject obj = new JSONObject();
			ApproVo aVo = List.get(i);
			obj.put("appro_no", aVo.getAppro_no());
			obj.put("appro_line_no",aVo.getAppro_line_no());
		//	obj.put("appro_line",aVo.getAppro_line());
			obj.put("appro_status",aVo.getAppro_status());
		//	obj.put("appro_status_no",aVo.getAppro_status_no());
			obj.put("emp_id",aVo.getEmp_id());
			obj.put("appro_empname",aVo.getAppro_empname());
			obj.put("appro_title",aVo.getAppro_title());
		//	obj.put("appro_content",aVo.getAppro_content());
			obj.put("appro_regdate",aVo.getAppro_regdate());
			obj.put("appro_type",aVo.getAppro_type());
		//	obj.put("appro_refer",aVo.getAppro_refer());
		//	obj.put("appro_returnreason",aVo.getAppro_returnreason());
			jArr.add(obj);
		}
		json.put("lists",jArr);
		return json.toString();
	}
	
	
	//결재문서 조회 (결재자)
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/docAllList.do", method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
	@ResponseBody
	public String selAllDocument(String emp_id) {
		logger.info("ApprovalController selAllDocument 실행");
		int int_emp_id = Integer.parseInt(emp_id);
		logger.info("[int_emp_id 값] : {}" ,int_emp_id);
		List<Map<String, Object>> List = service.selAllDocument(int_emp_id);
		logger.info("[List 값] : {}" ,List);
		logger.info("[List size 값] : {}" ,List.size());
		
		JSONObject obj = new JSONObject();
		JSONArray jArr = new JSONArray();
		ApproVo aVo = new ApproVo();
		
		for(int i=0; i<List.size(); i++) {
			Map<String, Object> map = List.get(i); 
			String rm = ""+ map.get("RM"); //결재순위ROW_NUMBER 
			String status = (String)map.get("APPRO_STATUS");//문서 상태 뽑아오기 (W:대기,Y:결재,N:반려)
			String appro_no = (String)map.get("APPRO_NO");
			logger.info("[rm 값] : {}" ,rm);
			logger.info("[status 값] : {}" ,status);
			logger.info("[status 값 true여부] : {}" ,!status.equals("N")?"true":"flase");
			logger.info("[appro_no 값] : {}" ,appro_no);
			
			List<Map<String, Object>> LineList = service.selLineList(appro_no);//문서번호 별 결재자 리스트 뽑아내기 
			
			if(!status.equals("N")) { //대기 상태이라면
				JSONObject jsonVo = new JSONObject();
				if(rm.equals("1")) {
					if(status.equals("W")) {
						aVo = service.selOneDocument(appro_no);
						logger.info("[aVo 값] : {}" ,aVo.toString());
						jsonVo.put("appro_no", aVo.getAppro_no());
						jsonVo.put("appro_status", aVo.getAppro_status());
						jsonVo.put("appro_title", aVo.getAppro_title());
						jsonVo.put("emp_id", aVo.getEmp_id());
						jsonVo.put("appro_empname", aVo.getAppro_empname());
						jsonVo.put("appro_regdate", aVo.getAppro_regdate());
						jArr.add(jsonVo);
					}
				}else if(rm.equals("2")) {
					Map<String, Object> first = LineList.get(0);
					logger.info("[first 값] : {}" ,first);
					String firstStatus = (String)first.get("APPRO_STATUS");
					logger.info("[firstStatus 값] : {}" ,firstStatus);
					if(firstStatus.equals("Y")) {//첫 결재자가 결재를 했다면
						if(status.equals("W")) {
							aVo = service.selOneDocument(appro_no);
							logger.info("[aVo 값] : {}" ,aVo.toString());
							jsonVo.put("appro_no", aVo.getAppro_no());
							jsonVo.put("appro_status", aVo.getAppro_status());
							jsonVo.put("appro_title", aVo.getAppro_title());
							jsonVo.put("emp_id", aVo.getEmp_id());
							jsonVo.put("appro_empname", aVo.getAppro_empname());
							jsonVo.put("appro_regdate", aVo.getAppro_regdate());
							jArr.add(jsonVo);
						}
					}
				}else if(rm.equals("3")) {
					Map<String, Object> second = LineList.get(1);
					logger.info("[second 값] : {}" ,second);
					String secondStatus = (String)second.get("APPRO_STATUS");
					logger.info("[secondStatus 값] : {}" ,secondStatus);
					if(secondStatus.equals("Y")) {//두번째 결재자가 결재를 했다면
						if(status.equals("W")) {
							aVo = service.selOneDocument(appro_no);
							logger.info("[aVo 값] : {}" ,aVo.toString());
							jsonVo.put("appro_no", aVo.getAppro_no());
							jsonVo.put("appro_status", aVo.getAppro_status());
							jsonVo.put("appro_title", aVo.getAppro_title());
							jsonVo.put("emp_id", aVo.getEmp_id());
							jsonVo.put("appro_empname", aVo.getAppro_empname());
							jsonVo.put("appro_regdate", aVo.getAppro_regdate());
							jArr.add(jsonVo);						
						}
					}
				}else if(rm.equals("4")) {
					Map<String, Object> third = LineList.get(2);
					logger.info("[Third 값] : {}" ,third);
					String thirdStatus = (String)third.get("APPRO_STATUS");
					logger.info("[secondStatus 값] : {}" ,thirdStatus);
					if(thirdStatus.equals("Y")) {//두번째 결재자가 결재를 했다면
						if(status.equals("W")) {
							aVo = service.selOneDocument(appro_no);
							logger.info("[aVo 값] : {}" ,aVo.toString());
							jsonVo.put("appro_no", aVo.getAppro_no());
							jsonVo.put("appro_status", aVo.getAppro_status());
							jsonVo.put("appro_title", aVo.getAppro_title());
							jsonVo.put("emp_id", aVo.getEmp_id());
							jsonVo.put("appro_empname", aVo.getAppro_empname());
							jsonVo.put("appro_regdate", aVo.getAppro_regdate());
							jArr.add(jsonVo);
						}
					}
				}
				obj.put("lists", jArr);
				logger.info("[obj 최종 값] : {}" , obj.toString());
			}
		}
		return obj.toString();
	}
	
	//결재문서 조회(결재상태별 조회)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/statusDocList.do", method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
	public String selStatusDocument(int emp_id,int appro_status_no) {
		logger.info("ApprovalController selMyDocument 실행");
//		int int_emp_id = Integer.parseInt(emp_id);
		logger.info("[emp_id 값] : {}" ,emp_id);
		logger.info("[appro_status_no 값] : {}" ,appro_status_no);
		ApproVo approVo = new ApproVo();
		approVo.setEmp_id(emp_id);
		approVo.setAppro_status_no(appro_status_no);
		List<ApproVo> List = service.selStatusDocument(approVo);
		logger.info("[List size 값] : {}" ,List.size());
		JSONObject json = new JSONObject();
		JSONArray jArr = new JSONArray();
		for (int i = 0; i < List.size(); i++) {
			JSONObject obj = new JSONObject();
			ApproVo aVo = List.get(i);
			obj.put("appro_no", aVo.getAppro_no());
			obj.put("appro_line_no",aVo.getAppro_line_no());
		//	obj.put("appro_line",aVo.getAppro_line());
			obj.put("appro_status",aVo.getAppro_status());
		//	obj.put("appro_status_no",aVo.getAppro_status_no());
			obj.put("emp_id",aVo.getEmp_id());
			obj.put("appro_empname",aVo.getAppro_empname());
			obj.put("appro_title",aVo.getAppro_title());
		//	obj.put("appro_content",aVo.getAppro_content());
			obj.put("appro_regdate",aVo.getAppro_regdate());
			obj.put("appro_type",aVo.getAppro_type());
		//	obj.put("appro_refer",aVo.getAppro_refer());
		//	obj.put("appro_returnreason",aVo.getAppro_returnreason());
			jArr.add(obj);
		}
		json.put("lists",jArr);
		return json.toString();
	}
	
	//문서 상세조회
	@RequestMapping(value = "/selDocDetail.do",method = RequestMethod.GET)
	public String selDocDetail(String appro_no,Model model,int emp_id) {
		logger.info("ApprovalController selDocDetail 실행");
		logger.info("[appro_no 값] : {}" ,appro_no);
		logger.info("[emp_id 값] : {}" ,emp_id);
		ApproVo aVo = service.selOneDocument(appro_no);
//		JSONParser jsonParser = new JSONParser();
//		try {
//			JSONObject obj = (JSONObject)jsonParser.parse(aVo.getAppro_line());
//		//	aVo.setAppro_line(obj.toJSONString());
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		logger.info("[aVo 값] : {}" ,aVo);
		model.addAttribute("aVo", aVo);
		model.addAttribute("loginEmp_id", emp_id);
		return "/appro/docDetail";
	}
	//결재자 승인 클릭시
	@ResponseBody
	@RequestMapping(value = "/guyljaejaApprove.do",method = RequestMethod.GET,produces = "application/text; charset=utf-8;")
	public String guyljaejaApprove(int appro_line_no, String approlineList, int emp_id) throws ParseException {
		logger.info("[appro_line_no 값] : {}" ,appro_line_no);
		logger.info("[approlineList 값] : {}" ,approlineList);
		logger.info("[emp_id 값] : {}" ,emp_id);
		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject) parser.parse(approlineList);
		JSONArray jArr = (JSONArray) parser.parse(obj.get("approval").toString());
		logger.info("[수정 전 화면에서 받은 JSON 값] : {}" ,jArr.toJSONString());
		ApproVo approVo = new ApproVo();
		
		for(int i=0; i<jArr.size(); i++) {
			int length = jArr.size();
			JSONObject tmp = (JSONObject) jArr.get(i);
			if(tmp.get("EMP_ID").equals(String.valueOf(emp_id)) && length-1 == i) {
				tmp.replace("APPRO_STATUS", "Y");
				jArr.set(i, tmp);
				service.updApprovedAppro(appro_line_no);
				approVo.setAppro_line_no(appro_line_no);
				logger.info("[if문의 approVo 값] : {}" ,approVo);
			}else if(tmp.get("EMP_ID").equals(String.valueOf(emp_id))) {
				tmp.replace("APPRO_STATUS", "Y");
				jArr.set(i, tmp);
				approVo.setAppro_line_no(appro_line_no);
				logger.info("[else if문의 approVo 값] : {}" ,approVo);
			}
		}
		obj.replace("approval", jArr);
		logger.info("[수정 후 변경한 JSON 값] : {}" ,jArr.toJSONString());
		approVo.setAppro_line(obj.toJSONString());
		logger.info("[for 문 밖의 approVo 값] : {}" ,approVo);
		int n = service.updApprovedApproLine(approVo);
		
		return (n==1)?"true":"flase";
	}	
}
