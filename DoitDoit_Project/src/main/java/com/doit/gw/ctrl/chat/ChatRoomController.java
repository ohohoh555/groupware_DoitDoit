package com.doit.gw.ctrl.chat;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.service.appro.IApproLineService;
import com.doit.gw.service.chat.IChatService;
import com.doit.gw.vo.appro.ApproDeptVo;
import com.doit.gw.vo.appro.ApproEmpVo;
import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatVo;
import com.doit.gw.vo.emp.EmpVo;

@Controller
public class ChatRoomController {
	
	private Logger logger = LoggerFactory.getLogger(ChatRoomController.class);
	
	@Autowired
	private IChatService service;
	
	@Autowired
	private IApproLineService treeService;
	
	//채팅방 목록 출력(메인화면)
  	@RequestMapping(value = "/roomList.do", method = RequestMethod.GET)
  	@ResponseBody
  	public List<ChatJoinVo> getRoom(Model model, Principal principal) {
  		String empId = principal.getName();
  		logger.info("@ChatController, GET Chat / Username : " + empId);
  		
  		List<ChatJoinVo> rooms = service.selRoom(empId);
  		
  		return rooms;
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
    
    @RequestMapping(value = "/chatJstree.do", method = RequestMethod.GET)
	public String chatJstree() {
		logger.info("============== ApproLineController jstree로 이동! ==============");
		return "/chat/chatJstree";
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/doJstree.do",method = RequestMethod.POST)
	public JSONArray doJstree(String emp_id) {
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
			JSONObject json = new JSONObject();
			JSONObject state = new JSONObject();
			json.put("id", approEmpVo.getEmp_id());
			json.put("parent", (approEmpVo.getDept_no()!= null)?approEmpVo.getDept_no():"00");
			json.put("text", approEmpVo.getEmp_name());
			json.put("icon","glyphicon glyphicon-folder-open");
			json.put("rank", approEmpVo.getRank_no());
			
			state.put("opened", false);
			if(emp_id.equals(Integer.toString(approEmpVo.getEmp_id()))) {
				state.put("disabled", true);
				state.put("selected", true);
				logger.info("이미 있음 {}",approEmpVo.getEmp_id());
			}else {
				state.put("disabled", false);
				state.put("selected", false);
			}
			json.put("state", state);
			
			jsonArr.add(json);
		}
		logger.info("[jsonArr 값] : {}" ,jsonArr.toJSONString());
		return jsonArr;
	}
}
