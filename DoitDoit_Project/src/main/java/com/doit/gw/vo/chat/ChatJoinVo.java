package com.doit.gw.vo.chat;

import java.io.Serializable;

public class ChatJoinVo implements Serializable{

	private static final long serialVersionUID = 3600230939640177995L;
	
	private String room_id;
	private String room_name;
	private String chat_time;
	private String chat_id;
	private String emp_id;
	private String chat_con;
	private String chat_type;
	
	public ChatJoinVo() {
		super();
	}
	
	public ChatJoinVo(String room_id, String room_name, String chat_time, String chat_id, String emp_id, String chat_con,
			String chat_type) {
		super();
		this.room_id = room_id;
		this.room_name = room_name;
		this.chat_time = chat_time;
		this.chat_id = chat_id;
		this.emp_id = emp_id;
		this.chat_con = chat_con;
		this.chat_type = chat_type;
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
	
	public String getChat_time() {
		return chat_time;
	}
	
	public void setChat_time(String chat_time) {
		this.chat_time = chat_time;
	}
	
	public String getChat_id() {
		return chat_id;
	}
	
	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}
	
	public String getEmp_id() {
		return emp_id;
	}
	
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	
	public String getChat_con() {
		return chat_con;
	}
	
	public void setChat_con(String chat_con) {
		this.chat_con = chat_con;
	}
	
	public String getChat_type() {
		return chat_type;
	}
	
	public void setChat_type(String chat_type) {
		this.chat_type = chat_type;
	}
	
	@Override
	public String toString() {
		return "ChatJoin [room_id=" + room_id + ", room_name=" + room_name + ", chat_time=" + chat_time + ", chat_id="
				+ chat_id + ", emp_id=" + emp_id + ", chat_con=" + chat_con + ", chat_type=" + chat_type + "]";
	}
}
