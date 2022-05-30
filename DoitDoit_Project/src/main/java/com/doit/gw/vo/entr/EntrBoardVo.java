package com.doit.gw.vo.entr;

public class EntrBoardVo {

	private String eboard_no;
	private String gubun_no;
	private String cgory_no;
	private String emp_id;
	private String emp_name;
	private String eboard_title;
	private String eboard_content;
	private String eboard_readcount;
	private String eboard_regdate;
	private String eboard_delflag;
	private String cald_id;
	private String cald_start;
	private String cald_end;
	private String cald_color;

	public EntrBoardVo() {
	}

	public EntrBoardVo(String eboard_no, String gubun_no, String cgory_no, String emp_id, String emp_name,
			String eboard_title, String eboard_content, String eboard_readcount, String eboard_regdate,
			String eboard_delflag, String cald_id, String cald_start, String cald_end, String cald_color) {
		this.eboard_no = eboard_no;
		this.gubun_no = gubun_no;
		this.cgory_no = cgory_no;
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.eboard_title = eboard_title;
		this.eboard_content = eboard_content;
		this.eboard_readcount = eboard_readcount;
		this.eboard_regdate = eboard_regdate;
		this.eboard_delflag = eboard_delflag;
		this.cald_id = cald_id;
		this.cald_start = cald_start;
		this.cald_end = cald_end;
		this.cald_color = cald_color;
	}

	public String getEboard_no() {
		return eboard_no;
	}

	public void setEboard_no(String eboard_no) {
		this.eboard_no = eboard_no;
	}

	public String getGubun_no() {
		return gubun_no;
	}

	public void setGubun_no(String gubun_no) {
		this.gubun_no = gubun_no;
	}

	public String getCgory_no() {
		return cgory_no;
	}

	public void setCgory_no(String cgory_no) {
		this.cgory_no = cgory_no;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEboard_title() {
		return eboard_title;
	}

	public void setEboard_title(String eboard_title) {
		this.eboard_title = eboard_title;
	}

	public String getEboard_content() {
		return eboard_content;
	}

	public void setEboard_content(String eboard_content) {
		this.eboard_content = eboard_content;
	}

	public String getEboard_readcount() {
		return eboard_readcount;
	}

	public void setEboard_readcount(String eboard_readcount) {
		this.eboard_readcount = eboard_readcount;
	}

	public String getEboard_regdate() {
		return eboard_regdate;
	}

	public void setEboard_regdate(String eboard_regdate) {
		this.eboard_regdate = eboard_regdate;
	}

	public String getEboard_delflag() {
		return eboard_delflag;
	}

	public void setEboard_delflag(String eboard_delflag) {
		this.eboard_delflag = eboard_delflag;
	}

	public String getCald_id() {
		return cald_id;
	}

	public void setCald_id(String cald_id) {
		this.cald_id = cald_id;
	}

	public String getCald_start() {
		return cald_start;
	}

	public void setCald_start(String cald_start) {
		this.cald_start = cald_start;
	}

	public String getCald_end() {
		return cald_end;
	}

	public void setCald_end(String cald_end) {
		this.cald_end = cald_end;
	}

	public String getCald_color() {
		return cald_color;
	}

	public void setCald_color(String cald_color) {
		this.cald_color = cald_color;
	}

	@Override
	public String toString() {
		return "EntrBoardVo [eboard_no=" + eboard_no + ", gubun_no=" + gubun_no + ", cgory_no=" + cgory_no + ", emp_id="
				+ emp_id + ", emp_name=" + emp_name + ", eboard_title=" + eboard_title + ", eboard_content="
				+ eboard_content + ", eboard_readcount=" + eboard_readcount + ", eboard_regdate=" + eboard_regdate
				+ ", eboard_delflag=" + eboard_delflag + ", cald_id=" + cald_id + ", cald_start=" + cald_start
				+ ", cald_end=" + cald_end + ", cald_color=" + cald_color + "]";
	}

}
