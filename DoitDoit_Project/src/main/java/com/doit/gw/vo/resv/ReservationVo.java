package com.doit.gw.vo.resv;

import java.io.Serializable;

public class ReservationVo implements Serializable {

	private static final long serialVersionUID = 7221595323689017924L;
	
	private String resv_id;
	private String emp_id;
	private String resv_title;
	private String resv_writer;
	private String resv_description;
	private String resv_start;
	private String resv_end;
	private String resv_room_id;
	private String resv_room_title;
	private String resv_room_eventcolor;
	public String getResv_id() {
		return resv_id;
	}
	public void setResv_id(String resv_id) {
		this.resv_id = resv_id;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getResv_title() {
		return resv_title;
	}
	public void setResv_title(String resv_title) {
		this.resv_title = resv_title;
	}
	public String getResv_writer() {
		return resv_writer;
	}
	public void setResv_writer(String resv_writer) {
		this.resv_writer = resv_writer;
	}
	public String getResv_description() {
		return resv_description;
	}
	public void setResv_description(String resv_description) {
		this.resv_description = resv_description;
	}
	public String getResv_start() {
		return resv_start;
	}
	public void setResv_start(String resv_start) {
		this.resv_start = resv_start;
	}
	public String getResv_end() {
		return resv_end;
	}
	public void setResv_end(String resv_end) {
		this.resv_end = resv_end;
	}
	public String getResv_room_id() {
		return resv_room_id;
	}
	public void setResv_room_id(String resv_room_id) {
		this.resv_room_id = resv_room_id;
	}
	public String getResv_room_title() {
		return resv_room_title;
	}
	public void setResv_room_title(String resv_room_title) {
		this.resv_room_title = resv_room_title;
	}
	public String getResv_room_eventcolor() {
		return resv_room_eventcolor;
	}
	public void setResv_room_eventcolor(String resv_room_eventcolor) {
		this.resv_room_eventcolor = resv_room_eventcolor;
	}
	@Override
	public String toString() {
		return "ReservationVo [resv_id=" + resv_id + ", emp_id=" + emp_id + ", resv_title=" + resv_title
				+ ", resv_writer=" + resv_writer + ", resv_description=" + resv_description + ", resv_start="
				+ resv_start + ", resv_end=" + resv_end + ", resv_room_id=" + resv_room_id + ", resv_room_title="
				+ resv_room_title + ", resv_room_eventcolor=" + resv_room_eventcolor + "]";
	}
	public ReservationVo(String resv_id, String emp_id, String resv_title, String resv_writer, String resv_description,
			String resv_start, String resv_end, String resv_room_id, String resv_room_title,
			String resv_room_eventcolor) {
		this.resv_id = resv_id;
		this.emp_id = emp_id;
		this.resv_title = resv_title;
		this.resv_writer = resv_writer;
		this.resv_description = resv_description;
		this.resv_start = resv_start;
		this.resv_end = resv_end;
		this.resv_room_id = resv_room_id;
		this.resv_room_title = resv_room_title;
		this.resv_room_eventcolor = resv_room_eventcolor;
	}
	public ReservationVo() {
	}
	
	
	
	
}
