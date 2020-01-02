package com.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
/*
 	자바 기반 DelegatingFilterProxy 설정하기.
XML 기반의 설정에서는 web.xml에 org.springframework.web.filter.DelegatingFilterProxy라는 springSecurityFilterChain을 등록하는 것으로 시작함.
자바 기반의 설정에서는 WebSecurityConfigurerAdapter를 상속받은 클래스에 @EnableWebSecurity 어노테이션을 명시하는 것만으로도 springSecurityFilterChain을 포함한 시큐리티를 사용하는데 필요한 객체를 생성함.(root context에 bean 등록되어 있음.)
포함된 springSecurityFilterChain을 작동시키기 위해서는 AbstractSecurityWebApplicationInitializer를 상속받은 WebApplicationInitializer를 만들어두면 됨. (WebApplicationInitializer를 만들지 않으면 필터가 작동하지 않음.)

AbstractSecurityWebApplicationInitializer는 WebApplicationInitializer의 구현이므로 스프링에 의해 찾아지고 웹 컨테이너와 DelegatingFilterProxy를 등록하는데 사용됨.
DelegatingFilterProxy 설정 방식은, 애플리케이션으로 들어오는 요청을 가로채 ID가 springSecurityFilterChain인 빈에게 위임시킴.
*/
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

}
