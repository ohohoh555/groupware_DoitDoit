package com.doit.gw.ctrl.cald;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
//@RequestMapping("cald/")
public class CaldController {

	@RequestMapping(value = "/moveCalendar.do", method=RequestMethod.GET)
	public String moveCalendar() {
		
		return "cald/Calendar";
	}
	
}
