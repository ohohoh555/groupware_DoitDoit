package com.doit.gw.service.appro;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.appro.ApproVo;


public interface IApproService {
	
	// 결재문서 인서트
	public ApproVo insApproval(ApproVo approVo);
	// 결재선리스트 인서트
	public int insApproLine(ApproVo approVo);
	// 임시저장 인서트
	public int insApproDraft(ApproVo approVo);
	// 문서 조회 셀렉트(기안자:상신)
	public List<ApproVo> selMyDocument(int emp_id);
	// 문서 조회 셀렉트(결재자:송신)
	public List<Map<String, Object>> selAllDocument(int emp_id);
	// 내가 결재할 문서 셀렉트 (결재예정문서조회)
	public ApproVo selOneDocument(String appro_no);
	// 문서번호 별 결재자 리스트 조회
	public List<Map<String, Object>> selLineList(String appro_no);

}
