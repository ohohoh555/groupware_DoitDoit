package com.doit.gw.comm.emp;



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
				new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
		
		logger.info("result : " + result);

		return result;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
}
