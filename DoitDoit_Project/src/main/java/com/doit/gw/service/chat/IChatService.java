package com.doit.gw.service.chat;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.chat.ChatFileVo;
import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatRoomVo;
import com.doit.gw.vo.chat.ChatVo;
import com.doit.gw.vo.emp.EmpVo;


public interface IChatService {

	//자신의 방 목록
//	public List<ChatJoinVo> selRoom(String emp_id);
	//채팅 조회
	public List<ChatVo> selChat(Map<String, String> map);
	//채팅멤버 조회
	public List<Map<String, String>> selRoomMem(String room_id);
	//원본 파일명 조회
	public String selFileNM(String file_chat_uuid);
	//채팅 입력
	public int insChat(Map<String, String> map);
	//파일 입력
	public int insFile(ChatFileVo vo);
	//채팅방 생성
	public String insChatRoom(Map<String, String> chat_room);
	//방 이름들을 조회
	public List<Map<String, String>> selRoomNames();
	//방 나갈려고 할때 멤버 조회
	public ChatRoomVo selRoomMember(String room_id); 
	//방 나가고 나서 남은 멤버 업데이트
	public int updRoomMember(ChatRoomVo vo);
	//해당 방의 삭제
	public boolean delChatRoom(String room_id);
}
