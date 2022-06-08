package com.doit.gw.mapper.sign;

import java.util.List;
import java.util.Map;

import com.doit.gw.vo.sign.SignVo;

public interface ISignMapper {
	
	public int insSign(SignVo signVo);
	public List<Map<String, Object>> selSign(int emp_id);
	public int updSign(SignVo signVo);
	public int insDefaultSign(SignVo signVo);
	

}
