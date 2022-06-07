package com.doit.gw.service.chat;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.chat.IChatDao;
import com.doit.gw.vo.chat.ChatFileVo;
import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatRoomVo;
import com.doit.gw.vo.chat.ChatVo;
import com.doit.gw.vo.emp.EmpVo;

@Service
public class ChatServiceImpl implements IChatService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private IChatDao dao;
			
	@Override
	public List<ChatJoinVo> selRoom(String emp_id) {
		logger.info("$$$$$ selRoom {} $$$$$",emp_id);
		return dao.selRoom(emp_id);
	}

	@Override
	public List<ChatVo> selChat(Map<String, String> map) {
		logger.info("$$$$$ selChat {} $$$$$",map);
		return dao.selChat(map);
	}

	@Override
	public List<Map<String, String>> selRoomMem(String room_id) {
		logger.info("$$$$$ selRoomMem {} $$$$$", room_id);
		return dao.selRoomMem(room_id);
	}
	
	@Override
	public int insChat(Map<String, String> map) {
		logger.info("$$$$$ insChat {} $$$$$",map);
		return dao.insChat(map);
	}

	@Override
	public int insFile(ChatFileVo vo) {
		logger.info("$$$$$ insFile $$$$$",vo);
		
		return dao.insFile(vo);
	}

	@Override
	public String insChatRoom(Map<String, String> chat_room) {
		logger.info("$$$$$ insChatRoom $$$$$",chat_room);
		int n = dao.insChatRoom(chat_room);
		if(n > 0) {
			return dao.selLastRoom();
		}else {
			return "0";
		}
	}

	@Override
	public String selFileNM(String file_chat_id) {
		logger.info("$$$$$ selFileNM $$$$$",file_chat_id);
		return dao.selFileNM(file_chat_id);
	}

	@Override
	public ChatRoomVo selGetOut(String room_id) {
		return dao.selGetOut(room_id);
	}

	@Override
	public int updGetOut(ChatRoomVo room_id) {
		return dao.updGetOut(room_id);
	}

	@Override
	public boolean delChatRoom(String room_id) {
		int i = dao.delChatFile(room_id);
		int k = dao.delChat(room_id);
		int j = dao.delChatRoom(room_id);
		if(i > 0 && k > 0 && j > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	
}
