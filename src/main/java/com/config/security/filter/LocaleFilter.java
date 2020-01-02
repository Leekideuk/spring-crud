package com.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

public class LocaleFilter extends GenericFilterBean{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String lang = request.getParameter("lang");
		if(lang != null){
			LocaleContextHolder.setLocale(StringUtils.parseLocaleString(lang));
		}else{
			LocaleContextHolder.setLocale(request.getLocale());
		}
		chain.doFilter(request, response);
	}
}
