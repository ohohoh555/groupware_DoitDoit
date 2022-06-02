package com.doit.gw.vo.entr;

public class FileListVo {

	private String eboard_no;
	private String gubun_no;
	private String cgory_no;
	private String emp_id;
	private String emp_name;
	private String eboard_regdate;
	private String eboard_delflag;
	private String flist_seq;
	private String flist_uuid;
	private String flist_originname;
	private String flist_size;
	private String flist_uploadpath;
	
	public FileListVo() {
	}

	public FileListVo(String eboard_no, String gubun_no, String cgory_no, String emp_id, String emp_name,
			String eboard_regdate, String eboard_delflag, String flist_seq, String flist_uuid, String flist_originname,
			String flist_size, String flist_uploadpath) {
		super();
		this.eboard_no = eboard_no;
		this.gubun_no = gubun_no;
		this.cgory_no = cgory_no;
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.eboard_regdate = eboard_regdate;
		this.eboard_delflag = eboard_delflag;
		this.flist_seq = flist_seq;
		this.flist_uuid = flist_uuid;
		this.flist_originname = flist_originname;
		this.flist_size = flist_size;
		this.flist_uploadpath = flist_uploadpath;
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

	public String getFlist_seq() {
		return flist_seq;
	}

	public void setFlist_seq(String flist_seq) {
		this.flist_seq = flist_seq;
	}

	public String getFlist_uuid() {
		return flist_uuid;
	}

	public void setFlist_uuid(String flist_uuid) {
		this.flist_uuid = flist_uuid;
	}

	public String getFlist_originname() {
		return flist_originname;
	}

	public void setFlist_originname(String flist_originname) {
		this.flist_originname = flist_originname;
	}

	public String getFlist_size() {
		return flist_size;
	}

	public void setFlist_size(String flist_size) {
		this.flist_size = flist_size;
	}

	public String getFlist_uploadpath() {
		return flist_uploadpath;
	}

	public void setFlist_uploadpath(String flist_uploadpath) {
		this.flist_uploadpath = flist_uploadpath;
	}

	@Override
	public String toString() {
		return "FileListVo [eboard_no=" + eboard_no + ", gubun_no=" + gubun_no + ", cgory_no=" + cgory_no + ", emp_id="
				+ emp_id + ", emp_name=" + emp_name + ", eboard_regdate=" + eboard_regdate + ", eboard_delflag="
				+ eboard_delflag + ", flist_seq=" + flist_seq + ", flist_uuid=" + flist_uuid + ", flist_originname="
				+ flist_originname + ", flist_size=" + flist_size + ", flist_uploadpath=" + flist_uploadpath + "]";
	}
	
	
	

}
