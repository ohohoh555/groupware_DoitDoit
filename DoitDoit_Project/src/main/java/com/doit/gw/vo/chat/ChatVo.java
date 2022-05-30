package com.doit.gw.vo.chat;

import java.io.Serializable;

public class ChatVo implements Serializable{

	private static final long serialVersionUID = -4500636193082675060L;

	private String chat_id;
	private String room_id;
	private String emp_id;
	private String chat_con;
	private String chat_time;
	private String chat_type;

	public ChatVo() {
		super();
	}
	
	public ChatVo(String chat_id, String room_id, String emp_id, String chat_con, String chat_time, String chat_type) {
		super();
		this.chat_id = chat_id;
		this.room_id = room_id;
		this.emp_id = emp_id;
		this.chat_con = chat_con;
		this.chat_time = chat_time;
		this.chat_type = chat_type;
	}

	public String getChat_id() {
		return chat_id;
	}

	public void setChat_id(String chat_id) {
		this.chat_id = chat_id;
	}

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
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

	public String getChat_time() {
		return chat_time;
	}

	public void setChat_time(String chat_time) {
		this.chat_time = chat_time;
	}

	public String getChat_type() {
		return chat_type;
	}

	public void setChat_type(String chat_type) {
		this.chat_type = chat_type;
	}

	@Override
	public String toString() {
		return "ChatVo [chat_id=" + chat_id + ", room_id=" + room_id + ", emp_id=" + emp_id + ", chat_con=" + chat_con
				+ "]";
	}
}
