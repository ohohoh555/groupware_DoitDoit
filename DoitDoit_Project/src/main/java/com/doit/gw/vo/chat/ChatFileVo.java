package com.doit.gw.vo.chat;

import java.io.Serializable;

import org.springframework.web.socket.BinaryMessage;

public class ChatFileVo implements Serializable{
	
	private static final long serialVersionUID = -7650295930042901810L;
	
	private String file_chat_id;
	private String file_chat_uuid;
	private String file_chat_originnm;
	private String file_chat_type;
	private String file_uploadpath;
	private String room_id;
	
	public ChatFileVo() {
		super();
	}

	public ChatFileVo(String file_chat_id, String file_chat_uuid, String file_chat_originnm, 
			String file_chat_type, String file_uploadpath, String room_id) {
		super();
		this.file_chat_id = file_chat_id;
		this.file_chat_uuid = file_chat_uuid;
		this.file_chat_originnm = file_chat_originnm;
		this.file_chat_type = file_chat_type;
		this.file_uploadpath = file_uploadpath;
		this.room_id = room_id;
	}

	public String getFile_chat_id() {
		return file_chat_id;
	}

	public void setFile_chat_id(String file_chat_id) {
		this.file_chat_id = file_chat_id;
	}

	public String getFile_chat_uuid() {
		return file_chat_uuid;
	}

	public void setFile_chat_uuid(String file_chat_uuid) {
		this.file_chat_uuid = file_chat_uuid;
	}

	public String getFile_chat_originnm() {
		return file_chat_originnm;
	}

	public void setFile_chat_originnm(String file_chat_originnm) {
		this.file_chat_originnm = file_chat_originnm;
	}

	public String getFile_chat_type() {
		return file_chat_type;
	}

	public void setFile_chat_type(String file_chat_type) {
		this.file_chat_type = file_chat_type;
	}

	public String getFile_uploadpath() {
		return file_uploadpath;
	}

	public void setFile_uploadpath(String file_uploadpath) {
		this.file_uploadpath = file_uploadpath;
	}

	public String getRoom_id() {
		return room_id;
	}

	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}

	@Override
	public String toString() {
		return "ChatFileVo [file_chat_id=" + file_chat_id + ", file_chat_uuid=" + file_chat_uuid
				+ ", file_chat_originnm=" + file_chat_originnm + ", file_chat_type=" + file_chat_type
				+ ", file_uploadpath=" + file_uploadpath + ", room_id=" + room_id + "]";
	}
}
