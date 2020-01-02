package com.config.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Autowired
	CustomUserDetailsService customUserDetailsService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	MessageSource messageSource;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		Collection<? extends GrantedAuthority> authorities;
		
		SecurityUser user = (SecurityUser) customUserDetailsService.loadUserByUsername(username);
		authorities = user.getAuthorities();
		
		if(!passwordEncoder.matches(password, user.getPassword())){
			throw new BadCredentialsException(messageSource.getMessage("CustomAuthenticationProvider.password",null, LocaleContextHolder.getLocale()));
		}else if(!user.isEmailCert()){
			throw new DisabledException(messageSource.getMessage("CustomAuthenticationProvider.email",null, LocaleContextHolder.getLocale()));
		}else if(!user.isEnabled()){
			throw new DisabledException(messageSource.getMessage("CustomAuthenticationProvider.enabled",null, LocaleContextHolder.getLocale()));
		}
		return new UsernamePasswordAuthenticationToken(user, password, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
