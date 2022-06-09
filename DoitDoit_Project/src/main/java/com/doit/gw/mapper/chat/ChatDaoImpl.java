package com.doit.gw.mapper.chat;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.chat.ChatFileVo;
import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatRoomVo;
import com.doit.gw.vo.chat.ChatVo;


@Repository
public class ChatDaoImpl implements IChatDao{
	
	private String NS = "com.doit.gw.mapper.chat.ChatDaoImpl.";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<ChatJoinVo> selRoom(String emp_id) {
		return sqlSession.selectList(NS+"selRoom", emp_id);
	}

	@Override
	public List<ChatVo> selChat(Map<String, String> map) {
		return sqlSession.selectList(NS+"selChat",map);
	}

	@Override
	public List<Map<String, String>> selRoomMem(String room_id) {
		return sqlSession.selectList(NS+"selRoomMem",room_id);
	}
	
	@Override
	public String selLastRoom() {
		return sqlSession.selectOne(NS+"selLastRoom");
	}

	@Override
	public int insChat(Map<String, String> chat) {
		logger.info("CCCCC insChat CCCCC",chat);
		return sqlSession.insert(NS+"insChat", chat);
	}

	@Override
	public int insFile(ChatFileVo vo) {
		logger.info("CCCCC insFile CCCCC", vo);
		return sqlSession.insert(NS+"insFile", vo);
	}

	@Override
	public int insChatRoom(Map<String, String> map) {
		logger.info("daoImpl {}",map);
		return sqlSession.insert(NS+"insChatRoom", map);
	}

	@Override
	public String selFileNM(String file_chat_id) {
		return sqlSession.selectOne(NS+"selFileNM",file_chat_id);
	}
	
	//채팅방의 멤버 변경을 위해서 선언
	@Override
	public ChatRoomVo selRoomMember(String room_id) {
		return sqlSession.selectOne(NS+"selRoomMember", room_id);
	}
	
	@Override
	public int updRoomMember(ChatRoomVo vo) {
		return sqlSession.update(NS+"updRoomMember", vo);
	}
	
	@Override
	public int delChatFile(String room_id) {
		return sqlSession.delete(NS+"delChatFile", room_id);
	}
	
	@Override
	public int delChat(String room_id) {
		return sqlSession.delete(NS+"delChat", room_id);
	}
	
	@Override
	public int delChatRoom(String room_id) {
		return sqlSession.delete(NS+"delChatRoom", room_id);
	}
}
