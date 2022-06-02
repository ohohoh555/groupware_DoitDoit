package com.doit.gw.ctrl.chat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doit.gw.vo.chat.ChatVo;


@Controller
public class ChatController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SimpMessagingTemplate template;
		
	private List<String> listMem;
	private List<ChatVo> listChat;
	
	private Map<String, List<String>> mapMem;
	private Map<String, List<ChatVo>> mapChat;
	
	public ChatController() {
		listChat = new ArrayList<ChatVo>();
		listMem = new ArrayList<String>();
		
		mapMem = new HashMap<String,List<String>>();
		mapChat = new HashMap<String,List<ChatVo>>();
	}
		
	//채팅방에 들어왔을때
	@MessageMapping(value = "/chat/enter")
    public void enter(ChatVo cVo){
		logger.info("@ChatController enter() : {}", cVo);
		logger.info("enterMem {}", mapMem.get(cVo.getRoom_id()));
		
		logger.info("^^^^ 해당 방 멤버 {} ^^^^",mapMem.get(cVo.getRoom_id()));
		if(mapMem.get(cVo.getRoom_id()) != null) {
			listMem = mapMem.get(cVo.getRoom_id());
			logger.info("멤버 있음 {}",listMem);
		}else {
			listMem = new ArrayList<String>();
			logger.info("멤버 없음 {}",listMem);
		}
		
		if(!listMem.contains(cVo.getEmp_id())) {
			listMem.add(cVo.getEmp_id());
			logger.info("멤버 추가 {}",listMem);
			mapMem.put(cVo.getRoom_id(), listMem);
		}
	
		logger.info("^^^^^^ 멤버 확인 {} ^^^^^^",mapMem);
        template.convertAndSend("/sub/chatMem/room/" + cVo.getRoom_id(), mapMem.get(cVo.getRoom_id()));
    }

	//채팅방 나갔을 때
	@MessageMapping(value = "/chat/out")
	public void out(ChatVo cVo) {
		logger.info("@ChatController out() : {}", cVo);
		
		listMem = mapMem.get(cVo.getRoom_id());
		listMem.remove(cVo.getEmp_id());
		
		mapMem.remove(cVo.getRoom_id());
		mapMem.put(cVo.getRoom_id(), listMem);
		
		logger.info("^^^^^ 남아 있는 멤버는 {} ^^^^^",listMem);
		template.convertAndSend("/sub/chatMem/room/" + cVo.getRoom_id(), mapMem.get(cVo.getRoom_id()));
	}
	
	//채팅 메시지
    @MessageMapping(value = "/chat/message")
    public void chatMessage(@RequestParam Map<String, String> map){
    	logger.info("@ChatController message() : {}", map);
    	
    	String html = "";
    	html += "<span class=\"Name\">"+map.get("user_name")+"</span>";
    	html += "<span class=\"msg\">"+map.get("chat_con")+"</span>";
    	
    	if(mapChat.get(map.get("room_id")) != null) {
			listChat = mapChat.get(map.get("room_id()"));
			logger.info("해당 방의 채팅 있음 {}",listChat);
		}else {
			listChat = new ArrayList<ChatVo>();
			logger.info("해당 방의 채팅 없음 {}",listChat);
		}
    	
    	ChatVo vo = new ChatVo(null, map.get("room_id"), map.get("emp_id"), html, null, "T");
    	listChat.add(vo);
    	
    	logger.info("보낼 채팅 {}",listChat);
    	
    	logger.info("보낼 html {}", html);
        template.convertAndSend("/sub/chat/room/" + map.get("room_id"), html);
    }
    
    //파일 메시지
    @MessageMapping(value = "/chat/file")
    public void fileMessage(String file) {
    	logger.info("@ChatController message() : {}", file);
    	    	
    	Decoder decoder = Base64.getDecoder();
    }
    
    @RequestMapping(value = "/saveChat.do", method = RequestMethod.POST)
    @ResponseBody
    public void name(String room_id) {
		
	}
}
