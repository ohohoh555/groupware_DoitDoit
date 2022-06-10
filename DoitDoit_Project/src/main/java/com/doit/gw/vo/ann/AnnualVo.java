package com.doit.gw.vo.ann;

public class AnnualVo {

	private String dept_name;
	private String emp_id;
	private String emp_name;
	private String rank_name;
	private String ann_add;
	private String ann_use;
	private String ann_rest;
	private String ann_in_time;
	
	
	public String getDept_name() {
		return dept_name;
	}
	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
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
	public String getRank_name() {
		return rank_name;
	}
	public void setRank_name(String rank_name) {
		this.rank_name = rank_name;
	}
	public String getAnn_add() {
		return ann_add;
	}
	public void setAnn_add(String ann_add) {
		this.ann_add = ann_add;
	}
	public String getAnn_use() {
		return ann_use;
	}
	public void setAnn_use(String ann_use) {
		this.ann_use = ann_use;
	}
	public String getAnn_rest() {
		return ann_rest;
	}
	public void setAnn_rest(String ann_rest) {
		this.ann_rest = ann_rest;
	}
	public String getAnn_in_time() {
		return ann_in_time;
	}
	public void setAnn_in_time(String ann_in_time) {
		this.ann_in_time = ann_in_time;
	}
	
	
	@Override
	public String toString() {
		return "AnnualVo [dept_name=" + dept_name + ", emp_id=" + emp_id + ", emp_name=" + emp_name + ", rank_name="
				+ rank_name + ", ann_add=" + ann_add + ", ann_use=" + ann_use + ", ann_rest=" + ann_rest
				+ ", ann_in_time=" + ann_in_time + "]";
	}
	
	
	public AnnualVo(String dept_name, String emp_id, String emp_name, String rank_name, String ann_add, String ann_use,
			String ann_rest, String ann_in_time) {
		super();
		this.dept_name = dept_name;
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.rank_name = rank_name;
		this.ann_add = ann_add;
		this.ann_use = ann_use;
		this.ann_rest = ann_rest;
		this.ann_in_time = ann_in_time;
	}
	
	
	public AnnualVo() {
	}
	
}
