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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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
	private IApproLineService treeService;
	
	@Autowired
	private ChatController chatController;
	
	Map<String, List<ChatJoinVo>> mapListChatRoomList;//채팅방 리스트
	List<ChatJoinVo> listChatRoomList;
	
	//room_id=54, room_name=isc테스트, chat_time=2022-06-13 01:20:57, chat_id=153, emp_id=0, chat_con=<span class="msg">isc테스트을 생성 하였습니다.</span>, chat_type=I, isc=false
	ChatRoomController(){	
		mapListChatRoomList = new HashMap<String, List<ChatJoinVo>>();
		listChatRoomList = new ArrayList<ChatJoinVo>();
	}
	
	//채팅방 목록 출력(메인화면)
  	@RequestMapping(value = "/roomList.do", method = RequestMethod.GET)
  	@ResponseBody
  	public List<ChatJoinVo> getRoom(Model model, Principal principal) {
  		
  		String empId = principal.getName();
  		
  		if(mapListChatRoomList.get(empId) == null) {
  			logger.info("@ChatController, GET Chat / Username : " + empId);
  			
//  			listChatRoomList = service.selRoom(empId);
  			mapListChatRoomList.put(empId, listChatRoomList);
  		}
  		
  		listChatRoomList = mapListChatRoomList.get(empId);
  		logger.info("listChatRoomList {}",listChatRoomList);
  		return listChatRoomList;
  	}
	
    //채팅방 진입시 채팅 내역 조회
    @RequestMapping(value = "/chatRoom.do", method = RequestMethod.GET)
    public String getChatRoom(String room_id, Principal principal, Model model) {
    	logger.info("ChatController getRoom {}",room_id);
		String emp_id = principal.getName();
    	
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
    	logger.info("{}",roomMemList);
    	model.addAttribute("room_mem_list", roomMemList);
    	
    	return "chat/chatRoom";
    }
    
    //방 생성 jsTree 동적 모달
    @RequestMapping(value = "/createJstree.do", method = RequestMethod.GET)
	public String createRoom() {
		logger.info("============== ApproLineController jstree로 이동! ==============");
		return "/chat/createJstree";
	}
    
    @RequestMapping(value = "/inviteJstree.do", method = RequestMethod.GET)
	public String inviteRoom() {
		logger.info("============== ApproLineController jstree로 이동! ==============");
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
		
		logger.info("emp_id {}",emp_id);
		
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
	public String makeRoom(String emp_id, @RequestParam List<String> mems, String roomName){
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
		logger.info("{}",json);
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("room_name", roomName);
		map.put("room_mem", json.toString());
		
		String room_id = service.insChatRoom(map);
		logger.info("room_id", room_id);
		
		Map<String, String> chat = new HashMap<String, String>();
		
		chat.put("room_id", room_id);
		chat.put("emp_id", "0");
		chat.put("chat_con", "<span class=\"msg\">"+roomName + "을 생성 하였습니다.</span>");
		chat.put("chat_time", formatedNow);
		chat.put("chat_type", "T");
		
		service.insChat(chat);
		
		return "{\"room_id\":"+room_id+"}";
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
		
		logger.info("vo는 {}",vo);
		
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
		logger.info("list {}", list);
		
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
				logger.info("초대자 있음 {}",inviteEmp);
			}
			
			if(mems.contains(String.valueOf(map.get("EMP_ID")))) {
				inviteEmps.add(map.get("EMP_NAME") + " " + map.get("RANK_NAME"));
				logger.info("초대 받은 사람들 {}",inviteEmps);
			}
		}
		
		List<String> memList = ChatController.getListMem(room_id);
		
		logger.info("들어온 최종적인 값 html : {} / mem : {}",html,memList);
		
		logger.info("inviteEmp {}",inviteEmp);
		logger.info("inviteEmps {}",inviteEmps);
		
		String inviteHtml = "<span class=\"msg\">"+inviteEmp+"님이 ";
		for (int i = 0; i < inviteEmps.size(); i++) {
			if(i > 0) {
				inviteHtml += ", ";
			}
			inviteHtml += inviteEmps.get(i) +"님";
		}
		
		inviteHtml += "을 초대하였습니다.</span>";
		
		logger.info("inviteHtml {} ",inviteHtml);
		
		JSONObject jsons = new JSONObject();
		jsons.put("room_id", room_id);
		jsons.put("html", html);
		jsons.put("memList", memList);
		jsons.put("inviteHtml", inviteHtml);
		
		return jsons;
	}
	
	public String localDatetime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formatedNow = now.format(formatter);
		
		return formatedNow;
	}
}
