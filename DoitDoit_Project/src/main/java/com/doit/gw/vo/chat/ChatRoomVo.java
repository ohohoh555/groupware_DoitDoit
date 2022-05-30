package com.doit.gw.vo.chat;

import java.io.Serializable;

public class ChatRoomVo implements Serializable{
	
	private static final long serialVersionUID = 8171703105272265638L;
	
	private String room_id;
	private String room_name;
	private String room_mem;
	
	public ChatRoomVo() {
		super();
	}

	public ChatRoomVo(String room_id, String room_name, String room_mem) {
		super();
		this.room_id = room_id;
		this.room_name = room_name;
		this.room_mem = room_mem;
	}
	
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getRoom_mem() {
		return room_mem;
	}
	public void setRoom_mem(String room_mem) {
		this.room_mem = room_mem;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ChatRoomVo [room_id=" + room_id + ", room_name=" + room_name + ", room_mem=" + room_mem + "]";
	}
}
