package com.doit.gw.vo.ann;

public class AnnUseVo {

	private String ann_use_no;
	private String emp_id;
	private String emp_name;
	private String ann_use_content;
	private String ann_use_period;
	private String ann_use_date;
	private String ann_use_days;
	
	
	public String getAnn_use_no() {
		return ann_use_no;
	}
	public void setAnn_use_no(String ann_use_no) {
		this.ann_use_no = ann_use_no;
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
	public String getAnn_use_content() {
		return ann_use_content;
	}
	public void setAnn_use_content(String ann_use_content) {
		this.ann_use_content = ann_use_content;
	}
	public String getAnn_use_period() {
		return ann_use_period;
	}
	public void setAnn_use_period(String ann_use_period) {
		this.ann_use_period = ann_use_period;
	}
	public String getAnn_use_date() {
		return ann_use_date;
	}
	public void setAnn_use_date(String ann_use_date) {
		this.ann_use_date = ann_use_date;
	}
	public String getAnn_use_days() {
		return ann_use_days;
	}
	public void setAnn_use_days(String ann_use_days) {
		this.ann_use_days = ann_use_days;
	}
	
	
	@Override
	public String toString() {
		return "AnnUseVo [ann_use_no=" + ann_use_no + ", emp_id=" + emp_id + ", emp_name=" + emp_name
				+ ", ann_use_content=" + ann_use_content + ", ann_use_period=" + ann_use_period + ", ann_use_date="
				+ ann_use_date + ", ann_use_days=" + ann_use_days + "]";
	}
	
	
	public AnnUseVo(String ann_use_no, String emp_id, String emp_name, String ann_use_content, String ann_use_period,
			String ann_use_date, String ann_use_days) {
		super();
		this.ann_use_no = ann_use_no;
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.ann_use_content = ann_use_content;
		this.ann_use_period = ann_use_period;
		this.ann_use_date = ann_use_date;
		this.ann_use_days = ann_use_days;
	}
	
	
	public AnnUseVo() {
	}
	
}
