package com.doit.gw.service.ann;

import java.util.List;
import java.util.Map;

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
	
}
