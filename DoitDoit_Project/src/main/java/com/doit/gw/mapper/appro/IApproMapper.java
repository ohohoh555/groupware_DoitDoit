package com.doit.gw.mapper.appro;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.appro.ApproVo;

public interface IApproMapper {
	
		public ApproVo insApproval(ApproVo approVo);
		public int insApproLine(ApproVo approVo);
		public int insApproDraft(ApproVo approVo);
		public List<Map<String, Object>> selAllDocument(int emp_id);
		public List<Map<String, Object>> selOneDocument(String appro_no);


}
