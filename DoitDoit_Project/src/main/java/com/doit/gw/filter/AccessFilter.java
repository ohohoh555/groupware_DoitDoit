package com.doit.gw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessFilter implements Filter{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static int i;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("===== AccessFilter init() =====");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		logger.info("===== FullUrl : {} =====",(req.getRemoteAddr()
				+ StringUtils.defaultIfEmpty(req.getQueryString(), null) != null?("?"+req.getQueryString()):"")+i++);
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		logger.info("===== AccessFilter destory() =====");
	}

}
