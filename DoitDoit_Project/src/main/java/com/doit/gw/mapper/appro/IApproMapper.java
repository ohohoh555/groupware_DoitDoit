package com.doit.gw.mapper.appro;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.appro.ApproVo;

public interface IApproMapper {
	
	public ApproVo insApproval(ApproVo approVo);
	public int insApproLine(ApproVo approVo);
	public int insApproDraft(ApproVo approVo);
	public List<ApproVo> selMyDocument(int emp_id);
	public List<Map<String, Object>> selAllDocument(int emp_id);
	public List<ApproVo> selStatusDocument(ApproVo approVo);
	public ApproVo selOneDocument(String appro_no);
	public List<Map<String, Object>> selLineList(String appro_no);
	public int updApprovedApproLine(ApproVo approVo);
	public int updApprovedAppro(int appro_line_no);
	public List<ApproVo> selFinalDoc(int emp_id);
}
