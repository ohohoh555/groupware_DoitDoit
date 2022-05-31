package com.doit.gw.vo.appro;

public class DocFormVo {
	private String doc_form_no; //폼 양식 번호
	private String doc_form_template; //폼 양식
	private String doc_form_date; //폼 생성일
	private String doc_form_name; //양식 이름
	public DocFormVo() {
		super();
	}
	public DocFormVo(String doc_form_no, String doc_form_template, String doc_form_date, String doc_form_name) {
		super();
		this.doc_form_no = doc_form_no;
		this.doc_form_template = doc_form_template;
		this.doc_form_date = doc_form_date;
		this.doc_form_name = doc_form_name;
	}
	public String getDoc_form_no() {
		return doc_form_no;
	}
	public void setDoc_form_no(String doc_form_no) {
		this.doc_form_no = doc_form_no;
	}
	public String getDoc_form_template() {
		return doc_form_template;
	}
	public void setDoc_form_template(String doc_form_template) {
		this.doc_form_template = doc_form_template;
	}
	public String getDoc_form_date() {
		return doc_form_date;
	}
	public void setDoc_form_date(String doc_form_date) {
		this.doc_form_date = doc_form_date;
	}
	public String getDoc_form_name() {
		return doc_form_name;
	}
	public void setDoc_form_name(String doc_form_name) {
		this.doc_form_name = doc_form_name;
	}
	@Override
	public String toString() {
		return "DocFormVo [doc_form_no=" + doc_form_no + ", doc_form_template=" + doc_form_template + ", doc_form_date="
				+ doc_form_date + ", doc_form_name=" + doc_form_name + "]";
	}
	
}
