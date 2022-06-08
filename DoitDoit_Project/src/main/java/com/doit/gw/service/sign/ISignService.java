package com.doit.gw.service.sign;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.sign.SignVo;

public interface ISignService {

	//사인 인서트
	public int insSign(SignVo signVo);
	//사인 리스트 전체조회
	public List<Map<String, Object>> selSign(int emp_id);
	//사인 삭제
	public int updSign(SignVo signVo);
	//사원등록시 디폴트사인이미지 인서트
	public int insDefaultSign(SignVo signVo);
	
}
