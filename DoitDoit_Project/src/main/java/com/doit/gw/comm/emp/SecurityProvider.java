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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.doit.gw.vo.emp.EmpVo;

public class SecurityProvider implements AuthenticationProvider {

	@Autowired
	private UserDetailsService uds;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = (String)authentication.getPrincipal();
		String password = (String)authentication.getCredentials();
		logger.info("id : " + username + " / pw : " + password);
		
		EmpVo user = (EmpVo)uds.loadUserByUsername(username);
		
		System.out.println(password);
		System.out.println(user.getPassword());
		
		if(!user.isEnabled()) {
			throw new BadCredentialsException(username);
		}else {
			logger.info("활성화 되어있음");
		}
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			logger.info("비밀번호 불일치");
			throw new BadCredentialsException(username);
		}else {
			logger.info("비밀번호 일치");
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
