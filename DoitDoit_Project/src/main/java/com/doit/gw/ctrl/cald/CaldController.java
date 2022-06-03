package com.doit.gw.ctrl.cald;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.cald.ICalendarService;
import com.doit.gw.vo.entr.EntrBoardVo;

@Controller
//@RequestMapping(value = "/cald")
public class CaldController {

	@Autowired
	private ICalendarService service;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "/moveCalendar.do", method=RequestMethod.GET)
	public String moveCalendar() {
		
		return "cald/Calendar";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/calendarAjax.do", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray calendarSelectAjax(Principal principal) {
		logger.info("CaldController calendarSelectAjax 시큐리티 사원 번호 : {}",principal.getName());
		List<EntrBoardVo> list = service.selCaldAll(principal.getName());
		JSONArray jsonArr = new JSONArray();
		for (EntrBoardVo entrBoardVo : list) {
			JSONObject obj = new JSONObject();
			obj.put("id", entrBoardVo.getCald_id());
			obj.put("title", entrBoardVo.getEboard_title());
			obj.put("description", entrBoardVo.getEboard_content());
			obj.put("start", entrBoardVo.getCald_start());
			obj.put("end", entrBoardVo.getCald_end());
			obj.put("color", entrBoardVo.getCald_color());
			
			jsonArr.add(obj);
		}
		return jsonArr;
	}
	
	/**
	 * 
	 * @param map
	 * @param req
	 * @return true / false  true 성공 : false 실패
	 */
	// 인서트
	@RequestMapping(value = "/calendarInsert.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean calendarInsert(@RequestParam Map<String, Object> map, Principal principal) {
		map.put("emp_id", principal.getName());
		logger.info("CalendarController calendarInsert 받아온 값 : {}", map);
		boolean isc = service.insCald(map);
		logger.info("CalendarController calendarInsert 성공여부 : {}", isc);
		return false;
	}
	
}
