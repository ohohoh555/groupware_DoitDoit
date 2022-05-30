package com.doit.gw.mapper.emp;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.doit.gw.vo.emp.EmpVo;

@Repository
public class EmpAuthMapper {
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private final String NS = "com.doit.gw.mapper.emp.EmpMapperImpl.";
	 
	// 시큐리티 로그인 로직
    public EmpVo login(String username) {
        return sqlSession.selectOne(NS + "login", username);
    }
}
