package com.doit.gw.vo.ann;

public class AnnAddVo {

	private String ann_add_no;
	private String emp_id;
	private String emp_name;
	private String ann_add_content;
	private String ann_add_date;
	private String ann_add_days;
	
	
	public String getAnn_add_no() {
		return ann_add_no;
	}
	public void setAnn_add_no(String ann_add_no) {
		this.ann_add_no = ann_add_no;
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
	public String getAnn_add_content() {
		return ann_add_content;
	}
	public void setAnn_add_content(String ann_add_content) {
		this.ann_add_content = ann_add_content;
	}
	public String getAnn_add_date() {
		return ann_add_date;
	}
	public void setAnn_add_date(String ann_add_date) {
		this.ann_add_date = ann_add_date;
	}
	public String getAnn_add_days() {
		return ann_add_days;
	}
	public void setAnn_add_days(String ann_add_days) {
		this.ann_add_days = ann_add_days;
	}
	
	
	@Override
	public String toString() {
		return "AnnAddVo [ann_add_no=" + ann_add_no + ", emp_id=" + emp_id + ", emp_name=" + emp_name
				+ ", ann_add_content=" + ann_add_content + ", ann_add_date=" + ann_add_date + ", ann_add_days="
				+ ann_add_days + "]";
	}
	
	
	public AnnAddVo(String ann_add_no, String emp_id, String emp_name, String ann_add_content, String ann_add_date,
			String ann_add_days) {
		super();
		this.ann_add_no = ann_add_no;
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.ann_add_content = ann_add_content;
		this.ann_add_date = ann_add_date;
		this.ann_add_days = ann_add_days;
	}
	
	
	public AnnAddVo() {
	}
	
}
