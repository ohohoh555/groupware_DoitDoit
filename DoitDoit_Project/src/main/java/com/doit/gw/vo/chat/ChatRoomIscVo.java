package com.doit.gw.vo.chat;

import java.io.Serializable;

public class ChatRoomIscVo implements Serializable{

	private static final long serialVersionUID = 1833616660417272440L;
	
	private String room_id;
	private String emp_id;
	private String isc;
	
	public ChatRoomIscVo() {
		super();
	}
	
	public ChatRoomIscVo(String id, String join, String isc) {
		super();
		this.emp_id = id;
		this.room_id = join;
		this.isc = isc;
	}

	public String getId() {
		return emp_id;
	}
	public void setId(String id) {
		this.emp_id = id;
	}
	public String getJoin() {
		return room_id;
	}
	public void setJoin(String join) {
		this.room_id = join;
	}
	public String getIsc() {
		return isc;
	}
	public void setIsc(String isc) {
		this.isc = isc;
	}

	@Override
	public String toString() {
		return "ChatRoomIscVo [id=" + emp_id + ", join=" + room_id + ", isc=" + isc + "]";
	}
}
