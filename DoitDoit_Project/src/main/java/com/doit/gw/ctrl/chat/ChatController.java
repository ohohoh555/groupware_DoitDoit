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
		}
		
		if (!listMem.contains(cVo.getEmp_id())) {
			listMem.add(cVo.getEmp_id());
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
    public void chatMessage(ChatVo cVo){
    	logger.info("@ChatController message() : {}", cVo);
        template.convertAndSend("/sub/chat/room/" + cVo.getRoom_id(), cVo);
    }
    
    //파일 메시지
    @MessageMapping(value = "/chat/file")
    public void fileMessage(String file) {
    	logger.info("@ChatController message() : {}", file);
    	    	
    	Decoder decoder = Base64.getDecoder();
    }
}
