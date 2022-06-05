package com.doit.gw.ctrl.resv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReservationController {
	
	@RequestMapping(value = "/goResv.do", method = RequestMethod.GET)
	public String goResv() {
		
		return "resv/reservation";
	}
}
