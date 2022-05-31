package com.doit.gw.vo.appro;

public class ApproDeptVo {
	
	//jstree 결재선 생성을 위한 EMP,DEPT VO 생성
	private String dept_no;
	private String dept_name;
	
	public ApproDeptVo() {
		super();
	}

	public ApproDeptVo(String dept_no, String dept_name) {
		super();
		this.dept_no = dept_no;
		this.dept_name = dept_name;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	@Override
	public String toString() {
		return "ApproDeptVo [dept_no=" + dept_no + ", dept_name=" + dept_name + "]";
	}
	
	
}
