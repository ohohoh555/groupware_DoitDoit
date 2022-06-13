package com.doit.gw.ctrl.chat;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.appro.IApproLineService;
import com.doit.gw.service.chat.IChatService;
import com.doit.gw.vo.appro.ApproDeptVo;
import com.doit.gw.vo.appro.ApproEmpVo;
import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatRoomVo;
import com.doit.gw.vo.chat.ChatVo;

@Controller
@RequestMapping("/comm")
public class ChatRoomController {
	
	private Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
	
	@Autowired
	private IChatService service;
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@Autowired
	private IApproLineService treeService;
	
	@Autowired
	private ChatController chatController;
		
	private static Map<String, ChatJoinVo> mapRoomList;//방 최근 글 리스트
	private static Map<String, Map<String, String>> mapMapRoomIsc;//내가 읽었는지 안 읽었는지
	private static Map<String, List<String>> mapMyRoomList;//내가 소속 되어 있는 방
	
	ChatRoomController(){	
		mapRoomList = new HashMap<String, ChatJoinVo>();
		mapMapRoomIsc = new HashMap<String, Map<String,String>>();
		mapMyRoomList = new HashMap<String, List<String>>();
	}
	
	public Map<String, ChatJoinVo> getMapRoomList() {
		logger.info("getMapRoomList {}",mapRoomList);
		return mapRoomList;
	}

	public void setMapRoomList(Map<String, ChatJoinVo> mapRoomList) {
		this.mapRoomList = mapRoomList;
	}

	public Map<String, Map<String, String>> getMapMapRoomIsc() {
		return mapMapRoomIsc;
	}

	public void setMapMapRoomIsc(Map<String, Map<String, String>> mapMapRoomIsc) {
		this.mapMapRoomIsc = mapMapRoomIsc;
	}

	public Map<String, List<String>> getMapMyRoomList() {
		return mapMyRoomList;
	}

	public void setMapMyRoomList(Map<String, List<String>> mapMyRoomList) {
		this.mapMyRoomList = mapMyRoomList;
	}

	public void getChatRoomList(String empId) {
		//자신의 채팅방 먼저 조회
  		List<String> list = new ArrayList<String>();
  		if(mapMyRoomList.get(empId) == null) {
  			list = service.selMyRoomList(empId);
  			mapMyRoomList.put(empId, list);
  		}
  		
  		//없는 목록을 골라 새로운 방을 찾아온다
  		List<String> emptyList = new ArrayList<String>(); //찾을 목록
  		
  		//for문으로 없는 방 찾는다
  		for(int i = 0; i < list.size(); i++) {
  			if(mapRoomList.get(list.get(i)) == null) {
  				emptyList.add(list.get(i));
  			}
  		}
  		
  		//없는 방 찾아서 VO값 들고온다.
  		if(emptyList.size() > 0) {
			List<ChatJoinVo> listRoom = service.selRoom(emptyList);
			for(int k = 0; k < listRoom.size(); k++) {
				mapRoomList.put(emptyList.get(k), listRoom.get(k));
			}
		}
  		
  		if(mapMapRoomIsc.get(empId) == null) {
  			List<Map<String, String>> listMapIsc = service.selRoomIsc(empId);
  			
  			Map<String, String> mapRoomIsc = new HashMap<String, String>();
  			for(int i = 0; i < listMapIsc.size(); i++) {
  				Map<String, String> map = listMapIsc.get(i);
  				
  				String key = map.get("ROOM_ID");
  				String value = map.get("ISC");
    				
  				mapRoomIsc.put(key, value);
  			}
  			mapMapRoomIsc.put(empId, mapRoomIsc);
  		}
	}
	
	//채팅방 목록 출력(메인화면)
  	@RequestMapping(value = "/roomList.do", method = RequestMethod.GET)
  	@ResponseBody
  	public List<ChatJoinVo> getRoom(Model model, Principal principal) {
  		
  		String empId = principal.getName();
  		getChatRoomList(empId);
  		
  		List<String> list = mapMyRoomList.get(empId);
  		List<ChatJoinVo> listVo = new ArrayList<ChatJoinVo>();
  		
  		logger.info("list {}",list);
  		for(int i = 0; i < list.size(); i++) {
  			ChatJoinVo vo = mapRoomList.get(list.get(i));
  			
  			Map<String, String> map = mapMapRoomIsc.get(empId);
  			logger.info("map.get(vo.getRoom_id()) {}", map.get(vo.getRoom_id()));
  			String isc = map.get(vo.getRoom_id());
  			
  			vo.setIsc(isc);
  			
  			listVo.add(vo);
  		}
  	  		
  		return listVo;
  	}
	
    //채팅방 진입시 채팅 내역 조회
    @RequestMapping(value = "/chatRoom.do", method = RequestMethod.GET)
    public String getChatRoom(String room_id, Principal principal, Model model) {
    	
       	String emp_id = principal.getName();
       	
    	List<String> allMember = service.selMyRoomList(emp_id);
    	
    	if(!allMember.contains(room_id)) {
    		return "redirect:/comm/logout.do";
    	}
    	
    	//들어 왔을 때 읽음 처리
    	Map<String, String> mapRoomIsc = mapMapRoomIsc.get(emp_id);
    	mapRoomIsc.put(room_id, "true");
    	mapMapRoomIsc.put(emp_id, mapRoomIsc);
    	
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("room_id", room_id);
    	map.put("emp_id", emp_id);
    	
    	//채팅 조회
    	List<ChatVo> chatList = service.selChat(map);
    	
    	List<ChatVo> sendChat = ChatController.getListChat(map.get("room_id"));
    	if(sendChat != null) {
	    	for(int i = 0; i < sendChat.size(); i++) {
	    		chatList.add(sendChat.get(i));
	    	}
    	}
    	
    	model.addAttribute("room_id",room_id);
    	model.addAttribute("emp_id", emp_id);
    	model.addAttribute("chatList", chatList);
    	
    	//멤버 출력
    	List<Map<String, String>> roomMemList = service.selRoomMem(room_id);
    	model.addAttribute("room_mem_list", roomMemList);
    	
    	return "chat/chatRoom";
    }
    
    //방 생성 jsTree 동적 모달
    @RequestMapping(value = "/createJstree.do", method = RequestMethod.GET)
	public String createRoom() {
		return "/chat/createJstree";
	}
    
    @RequestMapping(value = "/inviteJstree.do", method = RequestMethod.GET)
	public String inviteRoom() {
		return "/chat/inviteJstree";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/doJstree.do",method = RequestMethod.POST)
	public JSONArray doJstree(@RequestParam List<String> emp_id) {
		logger.info("ChatRoomController doJstree {}", emp_id);
		List<ApproEmpVo> listEmp = treeService.selEmp();
		List<ApproDeptVo> listDept = treeService.selDept();
		
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
			if(emp_id.contains(String.valueOf(approEmpVo.getEmp_id())) == false) {
				JSONObject json = new JSONObject();
				JSONObject state = new JSONObject();
				json.put("id", approEmpVo.getEmp_id());
				json.put("parent", (approEmpVo.getDept_no()!= null)?approEmpVo.getDept_no():"00");
				json.put("text", approEmpVo.getEmp_name());
				json.put("icon","glyphicon glyphicon-folder-open");
				json.put("rank", approEmpVo.getRank_no());
				json.put("state", state);
				
				jsonArr.add(json);
			}
		}
		logger.info("[jsonArr 값] : {}" ,jsonArr.toJSONString());
		return jsonArr;
	}
	
	//채팅방 생성
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/makeRoom.do",method = RequestMethod.POST)
	@ResponseBody
	public String makeRoom(String emp_id, @RequestParam List<String> mems, String roomName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, ParseException{
		logger.info("ChatRoomController makeRoom {} / {}", mems, roomName);

		String formatedNow = localDatetime();
		
		JSONObject json = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		
		for(int i = 0; i < mems.size(); i++) {
			JSONObject valJson = new JSONObject();
			valJson.put("id", mems.get(i));
			valJson.put("join", formatedNow);
			valJson.put("isc", "false");
			jsonArr.add(valJson);
		}
		
		json.put("ROOM", jsonArr);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("room_name", roomName);
		map.put("room_mem", json.toString());
		
		String room_id = service.insChatRoom(map);
		
		Map<String, String> chat = new HashMap<String, String>();
		
		chat.put("room_id", room_id);
		chat.put("emp_id", "0");
		chat.put("html", "<span class=\"msg\">"+roomName + "을 생성 하였습니다.</span>");
		chat.put("chat_time", formatedNow);
		chat.put("type", "I");
		
		chatController.chatMessage(chat);
		
		getChatRoomList(emp_id);
		
		for(int i = 0; i < mems.size(); i++) {
			getChatRoomList(mems.get(i));
		}
		
		return "{\"room_id\":\""+room_id+"\"}";
	}
	
	//초대
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inviteRoom.do",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject inviteRoom(String emp_id,@RequestParam List<String> mems, String room_id) 
			throws ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException{
		logger.info("ChatRoomController inviteRoom mems : {} / room_id",mems);
		
		//초대시 멤버 변경이 있으므로 해당 방 전체 멤버 조회 삭제
		Map<String, List<String>> roomAllMem = ChatController.getRoomAllMem();
		
		roomAllMem.remove(room_id);
		
		ChatController.setRoomAllMem(roomAllMem);
		
		ChatRoomVo vo = service.selRoomMember(room_id);
				
		String formatedNow = localDatetime();
		
		JSONObject json = chatController.jsonParser(vo.getRoom_mem());
		JSONArray jsonRoom = (JSONArray) json.get("ROOM");
		
		for(int i = 0; i < mems.size(); i++) {
			JSONObject jsonVal = new JSONObject();
			jsonVal.put("isc", "false");
			jsonVal.put("join", formatedNow.toString());
			jsonVal.put("id", mems.get(i));
			
			jsonRoom.add(jsonVal);
		}
		json.clear();
		json.put("ROOM", jsonRoom);
		
		vo.setRoom_mem(json.toString());
		service.updRoomMember(vo);
		
		List<Map<String, String>> list = service.selRoomMem(room_id);
		
		List<String> inviteEmps = new ArrayList<String>();//초대 받은 EMP
		String inviteEmp = ""; //초대한 EMP
		
		String html = "";
		for (int i = 0; i < list.size(); i++) {
			Map<String, String> map = list.get(i);
			
			html += "<div id="+String.valueOf(map.get("EMP_ID"))+">";
			html += 	"<span class=\"memList\"></span>";
			html +=  	"<span class=\"memList\">"+map.get("EMP_NAME")+"</span>";
			html += 	"<span class=\"memList memListRank\">"+map.get("RANK_NAME")+"</span>";
			html += "</div>";
			html += "<hr>";
			
			if(emp_id.equals(String.valueOf(map.get("EMP_ID")))) {
				inviteEmp = map.get("EMP_NAME") + " " + map.get("RANK_NAME");
			}
			
			if(mems.contains(String.valueOf(map.get("EMP_ID")))) {
				inviteEmps.add(map.get("EMP_NAME") + " " + map.get("RANK_NAME"));
			}
		}
		
		List<String> memList = ChatController.getListMem(room_id);
		
		String inviteHtml = "<span class=\"msg\">"+inviteEmp+"님이 ";
		for (int i = 0; i < inviteEmps.size(); i++) {
			if(i > 0) {
				inviteHtml += ", ";
			}
			inviteHtml += inviteEmps.get(i) +"님";
		}
		
		inviteHtml += "을 초대하였습니다.</span>";
				
		JSONObject jsons = new JSONObject();
		jsons.put("room_id", room_id);
		jsons.put("html", html);
		jsons.put("memList", memList);
		jsons.put("inviteHtml", inviteHtml);
		
		for(int i = 0; i < mems.size(); i++) {
			getChatRoomList(mems.get(i));
		}
		
		return jsons;
	}
	
	public String localDatetime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formatedNow = now.format(formatter);
		
		return formatedNow;
	}
}
