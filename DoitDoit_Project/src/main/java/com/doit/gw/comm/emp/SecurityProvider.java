package com.doit.gw.comm.emp;



import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.doit.gw.vo.emp.EmpVo;

public class SecurityProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService uds;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		logger.info("id : " + username + " / pw : " + password);
		
		EmpVo user = (EmpVo)uds.loadUserByUsername(username);
		
		if(!user.isEnabled()) {
			throw new BadCredentialsException(username);
		}
		
		
		UsernamePasswordAuthenticationToken result =
				new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dept_no", user.getDept_no());
		map.put("rank_no", user.getRank_no());
		map.put("emp_name", user.getEmp_name());
		
		result.setDetails(map);
		logger.info("담긴 값 : " + result);
		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
