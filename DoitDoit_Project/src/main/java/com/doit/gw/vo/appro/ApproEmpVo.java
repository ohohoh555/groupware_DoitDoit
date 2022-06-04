package com.doit.gw.vo.appro;

public class ApproEmpVo {
	
	private int emp_id;
	private String emp_name;
	private String dept_no;
	private String rank_no;
	private String dept_name;
	public ApproEmpVo() {
		super();
	}
	public ApproEmpVo(int emp_id, String emp_name, String dept_no, String rank_no, String dept_name) {
		super();
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.dept_no = dept_no;
		this.rank_no = rank_no;
		this.dept_name = dept_name;
	}
	public int getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getDept_no() {
		return dept_no;
	}
	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}
	public String getRank_no() {
		return rank_no;
	}
	public void setRank_no(String rank_no) {
		this.rank_no = rank_no;
	}
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}
	@Override
	public String toString() {
		return "ApproEmpVo [emp_id=" + emp_id + ", emp_name=" + emp_name + ", dept_no=" + dept_no + ", rank_no="
				+ rank_no + ", dept_name=" + dept_name + "]";
	}
	

}
