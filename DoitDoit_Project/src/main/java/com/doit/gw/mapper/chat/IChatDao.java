package com.doit.gw.mapper.chat;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.chat.ChatFileVo;
import com.doit.gw.vo.chat.ChatJoinVo;
import com.doit.gw.vo.chat.ChatRoomVo;
import com.doit.gw.vo.chat.ChatVo;


public interface IChatDao {
	//자신의 방 목록
	public List<ChatJoinVo> selRoom(List<String> roomList);
	//채팅방의 isc(읽었는지 안읽었는지 확인)
	public List<Map<String, String>> selRoomIsc(String emp_id);
	//내 방 목록 조회
	public List<String> selMyRoomList(String emp_id);
	//채팅 조회
	public List<ChatVo> selChat(Map<String, String> map);
	//채팅멤버 조회
	public List<Map<String, String>> selRoomMem(String room_id);
	//채팅 입력
	public int insChat(Map<String, String> map);
	//파일 입력
	public int insFile(ChatFileVo vo);
	//채팅방 생성
	public int insChatRoom(Map<String, String> map);
	//원본 파일명 조회
	public String selFileNM(String file_chat_uuid); 
	//최신 방 조회
	public String selLastRoom();
	//방 나갈려고 할때 멤버 조회
	public ChatRoomVo selRoomMember(String room_id);
	//방 이름들을 조회
	public List<Map<String, String>> selRoomNames();
	//방 나가고 나서 남은 멤버 업데이트
	public int updRoomMember(ChatRoomVo room_id);
	//해당 방의 파일 삭제
	public int delChatFile(String room_id);
	//해당방의 채팅 삭제
	public int delChat(String room_id);
	//해당 방의 삭제
	public int delChatRoom(String room_id);
}
