package com.doit.gw.mapper.sign;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.sign.SignVo;


@Repository
public class SignMapperImpl implements ISignMapper {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	private final String NS = "com.doit.gw.mapper.sign.SignMapperImpl.";
	
	@Override
	public int insSign(SignVo signVo) {
		return sqlSession.insert(NS+"insSign",signVo);
	}

	@Override
	public List<Map<String, Object>> selSign(int emp_id) {
		return sqlSession.selectList(NS+"selSign",emp_id);
	}

	@Override
	public int updSign(SignVo signVo) {
		return sqlSession.update(NS+"updSign",signVo);
	}

	@Override
	public int insDefaultSign(SignVo signVo) {
		return sqlSession.insert(NS+"insDefaultSign",signVo);
	}
	
	
}
