package com.doit.gw.vo.sign;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class SignVo {
	
	private int sign_no; //사인이미지번호(시퀀스)
	private int emp_id; //사원번호
	private String sign_regdate; //생성일
	private String sign_name; //파일이름
	private byte[] sign_img; //사인이미지
	
	private MultipartFile file;

	public SignVo() {
		super();
	}

	public SignVo(int emp_id, String sign_name, byte[] sign_img) {
		super();
		this.emp_id = emp_id;
		this.sign_name = sign_name;
		this.sign_img = sign_img;
	}

	public int getSign_no() {
		return sign_no;
	}

	public void setSign_no(int sign_no) {
		this.sign_no = sign_no;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getSign_regdate() {
		return sign_regdate;
	}

	public void setSign_regdate(String sign_regdate) {
		this.sign_regdate = sign_regdate;
	}

	public String getSign_name() {
		return sign_name;
	}

	public void setSign_name(String sign_name) {
		this.sign_name = sign_name;
	}

	public byte[] getSign_img() {
		return sign_img;
	}

	public void setSign_img(byte[] sign_img) {
		this.sign_img = sign_img;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "SignVo [sign_no=" + sign_no + ", emp_id=" + emp_id + ", sign_regdate=" + sign_regdate + ", sign_name="
				+ sign_name + ", sign_img=" + Arrays.toString(sign_img) + ", file=" + file + "]";
	}
	
	

}
