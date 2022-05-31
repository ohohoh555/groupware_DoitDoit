package com.doit.gw.vo.appro;

public class ApproVo {
	private String appro_no; //문서 번호
	private int appro_line_no; //문서 결재번호(시퀀스)
	private String doc_form_no; //양식폼 번호
	private int emp_id; //기안자 사원번호
	private String appro_empname; //기안자
	private String appro_title; //문서 제목
	private String appro_content; //문서 내용
	private String appro_regdate; //문서 작성일
	private String appro_status; //문서 상태
	private int appro_status_no; //문서 상태 번호
	private String appro_type; //디폴트(상신)
	private String appro_refer; //참조
	private String appro_returnreason; //반려사유
	private String appro_line; //결재선 리스트 
	public ApproVo() {
		super();
	}
	public ApproVo(String appro_no, int appro_line_no, String doc_form_no, int emp_id, String appro_empname,
			String appro_title, String appro_content, String appro_regdate, String appro_status, int appro_status_no,
			String appro_type, String appro_refer, String appro_returnreason, String appro_line) {
		super();
		this.appro_no = appro_no;
		this.appro_line_no = appro_line_no;
		this.doc_form_no = doc_form_no;
		this.emp_id = emp_id;
		this.appro_empname = appro_empname;
		this.appro_title = appro_title;
		this.appro_content = appro_content;
		this.appro_regdate = appro_regdate;
		this.appro_status = appro_status;
		this.appro_status_no = appro_status_no;
		this.appro_type = appro_type;
		this.appro_refer = appro_refer;
		this.appro_returnreason = appro_returnreason;
		this.appro_line = appro_line;
	}
	public String getAppro_no() {
		return appro_no;
	}
	public void setAppro_no(String appro_no) {
		this.appro_no = appro_no;
	}
	public int getAppro_line_no() {
		return appro_line_no;
	}
	public void setAppro_line_no(int appro_line_no) {
		this.appro_line_no = appro_line_no;
	}
	public String getDoc_form_no() {
		return doc_form_no;
	}
	public void setDoc_form_no(String doc_form_no) {
		this.doc_form_no = doc_form_no;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getAppro_empname() {
		return appro_empname;
	}
	public void setAppro_empname(String appro_empname) {
		this.appro_empname = appro_empname;
	}
	public String getAppro_title() {
		return appro_title;
	}
	public void setAppro_title(String appro_title) {
		this.appro_title = appro_title;
	}
	public String getAppro_content() {
		return appro_content;
	}
	public void setAppro_content(String appro_content) {
		this.appro_content = appro_content;
	}
	public String getAppro_regdate() {
		return appro_regdate;
	}
	public void setAppro_regdate(String appro_regdate) {
		this.appro_regdate = appro_regdate;
	}
	public String getAppro_status() {
		return appro_status;
	}
	public void setAppro_status(String appro_status) {
		this.appro_status = appro_status;
	}
	public int getAppro_status_no() {
		return appro_status_no;
	}
	public void setAppro_status_no(int appro_status_no) {
		this.appro_status_no = appro_status_no;
	}
	public String getAppro_type() {
		return appro_type;
	}
	public void setAppro_type(String appro_type) {
		this.appro_type = appro_type;
	}
	public String getAppro_refer() {
		return appro_refer;
	}
	public void setAppro_refer(String appro_refer) {
		this.appro_refer = appro_refer;
	}
	public String getAppro_returnreason() {
		return appro_returnreason;
	}
	public void setAppro_returnreason(String appro_returnreason) {
		this.appro_returnreason = appro_returnreason;
	}
	public String getAppro_line() {
		return appro_line;
	}
	public void setAppro_line(String appro_line) {
		this.appro_line = appro_line;
	}
	@Override
	public String toString() {
		return "ApproVo [appro_no=" + appro_no + ", appro_line_no=" + appro_line_no + ", doc_form_no=" + doc_form_no
				+ ", emp_id=" + emp_id + ", appro_empname=" + appro_empname + ", appro_title=" + appro_title
				+ ", appro_content=" + appro_content + ", appro_regdate=" + appro_regdate + ", appro_status="
				+ appro_status + ", appro_status_no=" + appro_status_no + ", appro_type=" + appro_type
				+ ", appro_refer=" + appro_refer + ", appro_returnreason=" + appro_returnreason + ", appro_line="
				+ appro_line + "]";
	}

}
