package com.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biz.user.UserVO;
import com.biz.user.impl.UserDAO;

@Service("CustomUserDetailservice")
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	MessageSource messageSource;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserVO user = userDAO.getUser(username);
		if(user == null){
			throw new UsernameNotFoundException(messageSource.getMessage("CustomUserDetailsService.username", null, LocaleContextHolder.getLocale()));
		}
		
		return new SecurityUser(user);
	}
}
