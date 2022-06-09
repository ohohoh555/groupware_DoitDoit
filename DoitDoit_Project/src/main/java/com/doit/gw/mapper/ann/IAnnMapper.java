package com.doit.gw.mapper.ann;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.ann.AnnAddVo;
import com.doit.gw.vo.ann.AnnUseVo;
import com.doit.gw.vo.ann.AnnualVo;
import com.doit.gw.vo.appro.ApproVo;
import com.doit.gw.vo.emp.EmpVo;

public interface IAnnMapper {

	//연차 등록(사원등록시)
	public int insAnnual();
	
	//===매년 1월 1일
	//모든 연차 초기화
	public int updAnnualReset();
	//올해 이전 입사한 사원 조회
	public List<EmpVo> selEmpYear(String date);
	//올해 이전 입사한 사원 연차(15개) 부여(insert)
	public int insAnnAddYear(String emp_id);
	//올해 이전 입사한 사원 연차(15개) 부여(update)
	public int updAnnualYear(Map<String, String[]> emp_ids);
	
	//===매월 1일
	//올해 입사한 사원 조회
	public List<EmpVo> selEmpMonth(Map<String, Object> map);
	//올해 입사한 사원 만근시 연차(1개) 부여(insert)
	public int insAnnAddMonth(String emp_id);
	//올해 입사한 사원 만근시 연차(1개) 부여(update)
	public int updAnnualMonth(Map<String, String[]> emp_ids);
	
	//매일
	//매일 출퇴근 시간 초기화
	public int updAnnualEveryDay();
	
	//관리자 연차 조회
	public List<AnnualVo> selAnnualAdmin(String dept_no);
	
	//관리자 연차 부여
	//관리자 연차 부여(insert)
	public int insAnnAdd(AnnAddVo annAddVo);
	//관리자 연차 부여(update)
	public int updAnnualAdd(Map<String, Object> map);
	
	//사원 연차 조회
	//사원 연차 조회(ANNUAL)
	public AnnualVo selAnnualEmp(String emp_id);
	//사원 연차 조회(ANN_ADD)
	public List<AnnAddVo> selAnnAddEmp(String emp_id);
	//사원 연차 조회(ANN_USE)
	public List<AnnUseVo> selAnnUseEmp(String emp_id);
	
	//출퇴근등록
	//출근
	public int updAnnualWorkIn(String emp_nfc);
	//퇴근
	public int updAnnualWorkOut(String emp_nfc);
	//근무일
	public int updAnnualWorkDays(String emp_nfc);
	
	//연차 사용
	//연차 사용(select)
	public ApproVo searchAppro(int appro_line_no);
	//연차 사용(insert)
	public int insAnnUse(Map<String, Object> map);
	//연차 사용(update)
	public int updAnnualUse(Map<String, Object> map);
	
}
