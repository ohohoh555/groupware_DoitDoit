package com.doit.gw.ctrl.cald;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.doit.gw.service.resv.IReservationService;
import com.doit.gw.vo.emp.EmpVo;
import com.doit.gw.vo.entr.EntrBoardVo;
import com.doit.gw.vo.resv.ReservationVo;

@Controller
//@RequestMapping(value = "/cald")
public class CaldController {

	@Autowired
	private ICalendarService service;

	@Autowired
	private IReservationService rService;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "/moveCalendar.do", method = RequestMethod.GET)
	public String moveCalendar() {

		return "cald/Calendar";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/calendarAjax.do", method = RequestMethod.GET)
	@ResponseBody
	public JSONArray calendarSelectAjax(Principal principal, HttpServletRequest req) {
		logger.info("CaldController calendarSelectAjax 시큐리티 사원 번호 : {}", principal.getName());

		String[] getHeader = req.getHeader("referer").split("/");
		System.out.println(Arrays.toString(getHeader));
		System.out.println(getHeader[getHeader.length - 1]);
		if (getHeader[getHeader.length - 1].equals("moveCalendar.do")) {
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
		} else if (getHeader[getHeader.length - 1].equals("goResv.do")) {
			// 타임라인의 이벤트 리스트
			List<ReservationVo> lists = rService.selResvAll();
			logger.info("####{}", lists);
			JSONArray arr = new JSONArray();
			for (ReservationVo vo : lists) {
				JSONObject obj = new JSONObject();
				obj.put("id", vo.getResv_id());
				obj.put("resourceId", vo.getResv_room_id());
				obj.put("title", vo.getResv_title());
				obj.put("start", vo.getResv_start());
				obj.put("end", vo.getResv_end());
				obj.put("writer", vo.getResv_writer());
				arr.add(obj);
			}
			JSONObject roomRes = new JSONObject();
			roomRes.put("events", arr);
			// 타임라인의 회의실 값
			List<ReservationVo> lists2 = rService.selResvRoom();
			JSONArray arr2 = new JSONArray();
			for (ReservationVo vo : lists2) {
				JSONObject obj = new JSONObject();
				obj.put("id", vo.getResv_room_id());
				obj.put("title", vo.getResv_room_title());
				obj.put("eventColor", vo.getResv_room_eventcolor());

				arr2.add(obj);
			}

			JSONObject room = new JSONObject();
			room.put("resources", arr2);
			JSONArray lastArr = new JSONArray();
			lastArr.add(roomRes);
			lastArr.add(room);
			logger.info("JSONArray 파싱한 값 : {}", lastArr);

			// return 형태
			// [{events:[{},{}]},{resources:[{},{}]}]
			return lastArr;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param map
	 * @param req
	 * @return true / false true 성공 : false 실패
	 */
	// 인서트
	@RequestMapping(value = "/calendarInsert.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean calendarInsert(@RequestParam Map<String, Object> map, Principal principal, HttpServletRequest req,
			EmpVo vo) {
		String[] getHeader = req.getHeader("referer").split("/");
		boolean isc = false;
		map.put("emp_id", principal.getName());

		logger.info("스타트 값 : {}", map.get("start"));
		if (getHeader[getHeader.length - 1].equals("moveCalendar.do")) {
			logger.info("CalendarController calendarInsert 받아온 값 : {}", map);
			isc = service.insCald(map);
			logger.info("CalendarController calendarInsert 성공여부 : {}", isc);
			return isc;
		} else if (getHeader[getHeader.length - 1].equals("goResv.do")) {
			List<ReservationVo> lists = rService.selResvInsDate();
			logger.info("CalendarController calendarInsert 받아온 값 : {}", map);
			isc = rService.insResvRoom(map);
			logger.info("CalendarController timeLineInsert 성공여부 : {}", isc);
			return isc;
		} else {
			return false;
		}

	}

	// 업데이트
	@RequestMapping(value = "/uadateDragAjax.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateDragAjax(@RequestParam Map<String, Object> map, Principal principal, HttpServletRequest req) {
		String[] getHeader = req.getHeader("referer").split("/");
		boolean isc = false;
		map.put("emp_id", principal.getName());
		if (getHeader[getHeader.length - 1].equals("moveCalendar.do")) {
			logger.info("CalendarController updateAjax 받아온 값 : {}", map);
			isc = service.updCaldDate(map);
			logger.info("CalendarController updateAjax boolean : {}", isc);
			return isc;
		} else {
			logger.info("CalendarController updateAjax 받아온 값 : {}", map);
			isc = rService.updResvDate(map);
			logger.info("CalendarController updateAjax boolean : {}", isc);
			return isc;
		}
	}

	// 업데이트
	@RequestMapping(value = "/uadateAjax.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean updateAjax(@RequestParam Map<String, Object> map, Principal principal, HttpServletRequest req) {
		logger.info("CalendarController updateAjax 받아온 값 : {}", map);

		String[] getHeader = req.getHeader("referer").split("/");
		boolean isc = false;
		map.put("emp_id", principal.getName());
		if (getHeader[getHeader.length - 1].equals("moveCalendar.do")) {
			isc = service.updCaldDate(map);
			System.out.println(isc);
			if (isc) {
				isc = service.updCaldContent(map);
				System.out.println(isc);
			}
			return isc;
		} else {
			isc = rService.updResv(map);
			logger.info("CalendarController updateAjax boolean : {}", isc);
			return isc;
		}

	}

	@RequestMapping(value = "/deleteAjax.do", method = RequestMethod.POST)
	@ResponseBody
	public boolean deleteAjax(String cald_id, @RequestParam Map<String, Object> map, Principal principal,
			HttpServletRequest req) {
		logger.info("CalendarController deleteAjax 받아온 값 : {}", cald_id);
		logger.info("CalendarController deleteAjax 받아온 값 : {}", map);

		String[] getHeader = req.getHeader("referer").split("/");
		boolean isc = false;

		map.put("emp_id", principal.getName());

		logger.info("CalendarController updateAjax 받아온 값 : {}", map);
		if (getHeader[getHeader.length - 1].equals("moveCalendar.do")) {
			isc = service.delCaldDate(cald_id);
			System.out.println(isc);
			return isc;
		} else {
			isc = rService.delResv(map);

			System.out.println(isc);
			return isc;
		}

	}

}
