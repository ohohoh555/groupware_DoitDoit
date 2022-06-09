package com.doit.gw.service.appro;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.appro.ApproVo;


public interface IApproService {
	//결재문서 인서트
	public ApproVo insApproval(ApproVo approVo);
	//결재선리스트 인서트
	public int insApproLine(ApproVo approVo);
	//임시저장 인서트
	public int insApproDraft(ApproVo approVo);
	//문서 조회 셀렉트(기안자:상신)
	public List<ApproVo> selMyDocument(int emp_id);
	//문서 조회 셀렉트(결재자:송신)
	public List<Map<String, Object>> selAllDocument(int emp_id);
	//문서 조회 셀렉트(결재상태별 조회)
	public List<ApproVo> selStatusDocument(ApproVo approVo);
	//내가 결재할 문서 셀렉트 (결재예정문서조회)
	public ApproVo selOneDocument(String appro_no);
	//문서번호 별 결재자 리스트 조회
	public List<Map<String, Object>> selLineList(String appro_no);
	//결재 승인 : 결재라인 CLOB 업데이트
	public int updApprovedApproLine(ApproVo approVo);
	//결재 승인 : 마지막 결재자까지 승인 됐다면 문서상태 완료로 변경 
	public int updApprovedAppro(int appro_line_no);
	//완료 문서 조회(결재자 기준)
	public List<ApproVo> selFinalDoc(int emp_id);
	//결재 반려 : 결재라인 CLOB 업데이트
	public int updReturnApproLine(ApproVo approVo);
	//결재 반려 : 결재상태 반려로 변경 
	public int updReturnAppro(ApproVo approVo);

}
