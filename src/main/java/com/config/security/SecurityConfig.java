package com.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.config.security.exception.exceptionHandler.LoginFailureHandler;
import com.config.security.filter.AjaxFilter;
import com.config.security.filter.LocaleFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private CustomAuthenticationProvider customAuthenticationProvider;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
			.antMatchers("/mypage.do","/updateUser.do","/updateUserEmail.do","/updateUserPassword.do","/deleteUser.do").authenticated()
			.antMatchers("/signup.do").hasAuthority("ROLE_ANONYMOUS")
			.antMatchers("/insertBoard.do","/updateBoard.do","/deleteBoard.do").authenticated()
			.antMatchers("/insertComment.do","/setDeleteFlagTrue.do").authenticated()
		.and()
		.formLogin()
			.loginPage("/login.do")
			.usernameParameter("userId")
			.passwordParameter("password")
			.defaultSuccessUrl("/getBoardList.do")
			.failureHandler(new LoginFailureHandler())
		.and()
		.logout()
			.logoutUrl("/logout.do")
			.invalidateHttpSession(true)
			.logoutSuccessUrl("/getBoardList.do");
		
		// 필터 추가
		http
			// LocaleContextHolder 설정.
			.addFilterBefore(new LocaleFilter(), SecurityContextPersistenceFilter.class)
			// 인코딩.
			.addFilterBefore(new CharacterEncodingFilter("UTF-8", true),CsrfFilter.class)
			// Ajax 처리. CommentController.insertComment() , setDeleteFlagTrue()
			.addFilterAfter(new AjaxFilter(), ExceptionTranslationFilter.class);
	}
	
	@Override protected void configure(AuthenticationManagerBuilder auth) throws Exception { 
		auth.authenticationProvider(customAuthenticationProvider);
	}
}
