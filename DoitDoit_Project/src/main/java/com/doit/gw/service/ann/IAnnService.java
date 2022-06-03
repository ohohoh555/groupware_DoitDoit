package com.doit.gw.service.ann;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.ann.AnnualVo;
import com.doit.gw.vo.emp.EmpVo;

public interface IAnnService {

	//연차 등록(사원등록시)
	public int insAnnual();	
	
	//===매년 1월 1일
	//생성 내역 초기화
	public int delAnnAddReset();
	//사용 내역 초기화
	public int delAnnUseReset();
	//모든 연차 초기화
	public int updAnnualReset();
	//올해 이전 입사한 사원 조회
	public List<EmpVo> selEmpYear(String date);
	//올해 이전 입사한 사원 연차(15개) 부여(insert)
	public int insAnnAddYear(String emp_id);
	//올해 이전 입사한 사원 연차(15개) 부여(update)
	public int updAnnualYear(Map<String, String[]> map);	
	
	//===매월 1일
	//올해 입사한 사원 조회
	public List<EmpVo> selEmpMonth(Map<String, Object> map);
	//올해 입사한 사원 만근시 연차(1개) 부여(insert)
	public int insAnnAddMonth(String emp_id);
	//올해 입사한 사원 만근시 연차(1개) 부여(update)
	public int updAnnualMonth(Map<String, String[]> emp_ids);	
	
	//관리자 연차 조회
	public List<AnnualVo> selAnnualAdmin(String dept_no);	
	
}
