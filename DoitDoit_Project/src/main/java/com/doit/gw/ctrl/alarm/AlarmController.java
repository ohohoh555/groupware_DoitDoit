package com.doit.gw.ctrl.alarm;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AlarmController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Map<String, Object> map;

	public AlarmController() {
		map = new HashMap<String, Object>();
	}

	@MessageMapping("/login")
	@SendTo("/sub/entr")
	public String sendGonji(String name) {

		System.out.println("#################" + name);
		return name;
	}

	@SuppressWarnings("unchecked")
	@MessageMapping("/alarm/{susin}")
	@SendTo("/sub/alarm/{susin}")
	@ResponseBody
	public JSONObject apprSend(@PathVariable(name = "susin") String susin, @RequestParam Map<String, Object> map) {
		System.out.println("session 정보" + map);
		System.out.println("@@@@@@@@@@@@@@@@@@@" + map);
		JSONObject obj = new JSONObject();
		obj.put("type", map.get("type"));
		obj.put("barsin", map.get("barsin"));
		return obj;
	}

	@SuppressWarnings("unchecked")
	@MessageMapping("/apprMem/complet/{barsin}")
	@SendTo("/sub/approval/complet/{barsin}")
	@ResponseBody
	public JSONObject apprComplet(@PathVariable(name = "barsin") String barsin,  @RequestParam Map<String, Object> map) {
		System.out.println("session 정보" + map);
		System.out.println("@@@@@@@@@@@@@@@@@@@" + "dddddd" + barsin);
		JSONObject obj = new JSONObject();
		obj.put("type", map.get("type"));
		obj.put("susin", map.get("susin"));
		return obj;
	}
}
