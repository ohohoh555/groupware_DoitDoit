package com.doit.gw.service.emp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.doit.gw.mapper.emp.EmpAuthMapper;
import com.doit.gw.vo.emp.EmpVo;

public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private EmpAuthMapper mMapper;
	
   @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EmpVo user = mMapper.login(username);
        if(user==null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}