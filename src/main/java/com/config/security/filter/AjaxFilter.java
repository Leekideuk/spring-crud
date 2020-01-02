package com.config.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.GenericFilterBean;

public class AjaxFilter extends GenericFilterBean{
	private String ajaxHeader = "AJAX";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		if (isAjaxRequest(req)) {
            try {
                chain.doFilter(req, res);
            } catch (AccessDeniedException e) {
            	// 권한 필요한 ajax 요청 실패. 브라우저와 통신 때는 다른 곳에서 예외 일으켜야함.
                res.sendError(HttpServletResponse.SC_FORBIDDEN);	// 403
            } catch (AuthenticationException e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);	// 401
            }
        } else{
        	chain.doFilter(req, res);
		}
	}
	
	private boolean isAjaxRequest(HttpServletRequest req) {
        return req.getHeader(ajaxHeader) != null
                && req.getHeader(ajaxHeader).equals(Boolean.TRUE.toString());
    }
}
