package com.controller.user;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.user.UserService;
import com.biz.user.UserVO;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	// 회원가입
	@RequestMapping(value="/signup.do", method=RequestMethod.GET)
	public String signupView(HttpSession session, @ModelAttribute("user") UserVO vo){
		if( session.getAttribute("user") != null) { return "redirect:getBoardList.do"; }	// 로그인 상태에서 회원가입 불가.
		return "user/signup.jsp";
	}
	
	@RequestMapping(value="/signup.do", method=RequestMethod.POST)
	public String signUp(@Valid @ModelAttribute("user") UserVO vo, BindingResult brs){
		if(brs.hasErrors()){ return "user/signup.jsp"; }
		userService.insertUser(vo);
		return "redirect:getBoardList.do";
	}
	
	// 회원가입 이메일 인증
	@RequestMapping(value="emailAuth.do")
	public String emailAuth(UserVO vo){
		userService.emailAuth(vo);
		return "redirect:login.do";
	}
	
	// 로그인
	@RequestMapping(value="/login.do", method=RequestMethod.GET)
	public String loginView(HttpSession session){
		if( session.getAttribute("user") != null) { return "redirect:getBoardList.do"; }	// 로그인 상태에서 로그인 불가.
		return "user/login.jsp";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String login(UserVO vo, HttpSession session){
		UserVO user = userService.login(vo);
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2 아이디 X or 비밀번호 X or 이메일 인증 X 상황에 따라 응답 달리하기.
		if(user == null || user.isEmailCert() == false){ return "user/login.jsp"; }
		session.setAttribute("user", user);
		return "redirect:getBoardList.do";
	}
	
	// 로그아웃
	@RequestMapping("/logout.do")
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:getBoardList.do";
	}
	
	// mypage
	@RequestMapping("/mypage.do")
	public String mypageView(HttpSession session, HttpServletResponse response){
		if( session.getAttribute("user") == null) { return "redirect:getBoardList.do"; }	// 로그아웃 상태에서 마이페이지 이동 불가.
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22
		// a 로그인 -> a mypage -> a 로그아웃 -> b 로그인 -> 뒤로가기 -> a mypage -> b가 a정보 변경가능.
		// 캐시 사용 안 하기.
		// WebContentInterceptor
		response.setHeader("Cache-Control","no-store"); 
		return "user/mypage.jsp";
	}
	
	// 회원탈퇴
	@RequestMapping("/deleteUser.do")
	public String deleteUser(HttpSession session){
		if( session.getAttribute("user") == null) { return "redirect:getBoardList.do"; }	// 로그아웃 상태에서 회원탈퇴 불가.
		userService.deleteUser((UserVO) session.getAttribute("user"));
		session.invalidate();
		return "redirect:getBoardList.do";
	}
	
	// 회원정보수정
	@RequestMapping("/updateUser.do")
	public String updateUser(UserVO vo, HttpSession session){
		if( session.getAttribute("user") == null) { return "redirect:getBoardList.do"; }	// 로그아웃 상태에서 회원수정 불가.
		session.setAttribute("user", userService.updateUser(vo));
		return "redirect:mypage.do";
	}
}
