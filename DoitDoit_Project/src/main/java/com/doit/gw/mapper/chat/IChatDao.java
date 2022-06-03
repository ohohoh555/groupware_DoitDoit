package com.doit.gw.mapper.chat;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatVo;


public interface IChatDao {

	//자신의 방 목록
	public List<ChatJoinVo> selRoom(String emp_id);
	//채팅 조회
	public List<ChatVo> selChat(Map<String, String> map);
	//채팅멤버 조회
	public List<Map<String, String>> selRoomMem(String room_id);
	//채팅 입력
	public int insChat(Map<String, String> map);
	//파일 입력
	public int insFile(Map<String, String> map);
	//채팅방 생성
	public int insChatRoom(Map<String, String> map);
	//원본 파일명 조회
	public String selFileNM(String file_chat_id); 
}
