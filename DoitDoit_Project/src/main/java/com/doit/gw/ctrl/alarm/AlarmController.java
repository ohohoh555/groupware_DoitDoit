package com.doit.gw.ctrl.alarm;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.ctrl.emp.LoginController;
import com.doit.gw.service.cald.ICalendarService;
import com.doit.gw.vo.emp.EmpVo;
import com.doit.gw.vo.entr.EntrBoardVo;


@Controller
@RequestMapping("/comm")
public class AlarmController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	private List<String> memList;
	
	@Autowired
	private ICalendarService service;

	public AlarmController() {
		memList = new ArrayList<String>();
	}

	@MessageMapping("/login")
	public void login(EmpVo eVo) {
		if(!memList.contains(eVo.getEmp_id())) {
			memList.add(eVo.getEmp_id());
		}
		logger.info("로그인 된 회원 : {}", memList);
	}
	
	@SuppressWarnings("unchecked")
	@MessageMapping("/alarm/{susin}")
	@SendTo("/sub/alarm/{susin}")
	@ResponseBody
	public JSONObject apprSend(@PathVariable(name = "susin") String susin, @RequestParam Map<String, Object> map) {
		
		String barsin = service.selEmpName((String) map.get("barsin"));
		Object susinUser = map.get("susin");
		String strUser = susinUser.toString();
		
		Map<String, Object> mapAlarm = new HashMap<String, Object>();
		
		JSONObject obj = new JSONObject();
		
		logger.info("AlarmController apprSend 현재 로그인 한 회원 : {} ",memList);
		logger.info("AlarmController apprSend 상대방의 사원번호 : {} ",strUser.trim());
		logger.info("AlarmController apprSend 상대방이 로그인 한지 여부 : {}",memList.contains(strUser.trim()));
		
		if(!memList.contains(strUser.trim())) {
			mapAlarm.put("emp_id", map.get("susin"));
			mapAlarm.put("eboard_title", barsin+" 님이 결재를 요청하였습니다.");
			logger.info("미로그인 상태의 상대에게 값 전송 : {}",mapAlarm);
			boolean isc = service.insAlarm(mapAlarm);
			logger.info("결재상황 일정 등록 여부 : {}",isc?"성공":"실패");
			return null;
		}else { 
			System.out.println("@@@@@@@@@@@@@@@@@@@" + map);
			obj.put("type", map.get("type"));
			obj.put("barsin", barsin);
			return obj;
		}
	}

	@SuppressWarnings("unchecked")
	@MessageMapping("/apprMem/complet/{barsin}")
	@SendTo("/sub/approval/complet/{barsin}")
	@ResponseBody
	public JSONObject apprComplet(@PathVariable(name = "barsin") String barsin,  @RequestParam Map<String, Object> map) {
		
		logger.info("받아온 값 : {}", map);
		Object susinUser = map.get("susin");
		String strUser2 = susinUser.toString();
		
		String susin = service.selEmpName((String) strUser2.trim());
		System.out.println(susin);
		Map<String, Object> mapAlarm = new HashMap<String, Object>();
		
		Object barsinUser = map.get("barsin");
		String strUser = barsinUser.toString();
		
		logger.info("apprComplet 담겨있는 Map 값 {}",map);
		logger.info("AlarmController apprComplet 현재 로그인 한 회원 : {} ",memList);
		logger.info("AlarmController apprComplet 상대방의 사원번호 : {} ",strUser.trim());
		logger.info("AlarmController apprComplet 상대방이 로그인 한지 여부 : {}",memList.contains(strUser.trim()));
		
		JSONObject obj = new JSONObject();

		if(!memList.contains(strUser.trim())){
			if(map.get("type").equals("comp")) {
				mapAlarm.put("emp_id",strUser.trim());
				mapAlarm.put("eboard_title", " 결재가 완료되었습니다.");
				boolean isc = service.insAlarm(mapAlarm);
				logger.info("결재완료상황 일정 등록 여부 : {}",isc?"성공":"실패");
			}else if(map.get("type").equals("banryu")) {
				
				mapAlarm.put("emp_id",strUser.trim());
				mapAlarm.put("eboard_title", susin+" 님이 기안을 반려하였습니다.");
				boolean isc = service.insAlarm(mapAlarm);
				logger.info("반려상황 일정 등록 여부 : {}",isc?"성공":"실패");
			}
			return null;
		}else {
			logger.info("로그인 한 회원 에게 알림 전송");
			obj.put("type", map.get("type"));
			obj.put("susin", susin);
			
			return obj;
		}
	}
	
	@MessageMapping("/logout")
	public void logout(EmpVo eVo) {
		logger.info("퇴장할 회원 aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa: {}", eVo.getEmp_id());
		if(memList.contains(eVo.getEmp_id())) {
			memList.remove(eVo.getEmp_id());
		}
		logger.info("로그인 된 회원 : {}", memList);
	}
	
//	@PostMapping(value = "/selAlaram.do")
	@RequestMapping(value = "/selAlaram.do", method = RequestMethod.POST)
	@ResponseBody
	public List<EntrBoardVo> selApprAlarm(String emp_id, Principal prin) {
//		List<EntrBoardVo> lists = service.selApprAlarm(emp_id);
		List<EntrBoardVo> lists = service.selApprAlarm(prin.getName());
		logger.info("출력된 결재 알림 결과 여기 맞지? : {}", lists);
		return lists;
	}
	
	@PostMapping(value = "/delAlarm.do")
	@ResponseBody
	public boolean delAlarm(String cald_id) {
		logger.info("AlarmController delAlarm: {}", cald_id);
		boolean isc = service.delAlarm(cald_id);
		logger.info("AlarmController delAlarm 삭제 성공여부: {}", isc);
		return isc;
	}
	
	@MessageMapping("/beforeLogout")
	public void delSession(String emp_id) {
		logger.info("AlarmController delSession: {}", emp_id);
		boolean isc = false;
		logger.info("로그인 된 회원 ㄴㄴㄴㄴㄴㄴㄴㄴ: {}", memList);
		if(memList.contains(emp_id)) {
			if(memList.remove(emp_id)) {
				isc = true;
			}
		}
		logger.info("회원이 세션에 있는지 확인 여부 : {}",memList.contains(emp_id));
		logger.info("회원이 세션에서 삭제됐는지 여부 : {}",isc);
		logger.info("로그인 된 회원 ㅁㅁㅁㅁㅁㅁㅁㅁㅁㅁ: {}", memList);
	}
}
