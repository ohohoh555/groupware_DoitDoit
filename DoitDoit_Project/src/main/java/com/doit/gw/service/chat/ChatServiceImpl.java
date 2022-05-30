package com.doit.gw.service.chat;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.doit.gw.mapper.chat.IChatDao;
import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatVo;

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
	public List<String> selRoomMem(String room_id) {
		logger.info("$$$$$ selRoomMem {} $$$$$", room_id);
		return dao.selRoomMem(room_id);
	}

	@Override
	public int insChat(Map<String, String> map) {
		logger.info("$$$$$ insChat {} $$$$$",map);
		return dao.insChat(map);
	}

	@Override
	public int insFile(Map<String, String> map) {
		logger.info("$$$$$ insFile $$$$$",map);
		
		int m = dao.insFile(map);
		int n = dao.insChat(map);
		logger.info("???? {} ????", n);
		logger.info("???? {} ????", m);
		
		return (n > 0 && m > 0)?1:0;
	}

	@Override
	public int insChatRoom(Map<String, String> chat_room) {
		logger.info("$$$$$ insChatRoom $$$$$",chat_room);
		return dao.insChatRoom(chat_room);
	}

	@Override
	public String selFileNM(String file_chat_id) {
		logger.info("$$$$$ selFileNM $$$$$",file_chat_id);
		return dao.selFileNM(file_chat_id);
	}
}
