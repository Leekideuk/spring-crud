package com.controller.user;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.user.UserService;
import com.biz.user.UserVO;
import com.exception.validation.UpdateUserEmailValidator;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 회원가입
	@RequestMapping(value="/signup.do", method=RequestMethod.GET)
	public String signupView(HttpSession session, @ModelAttribute("user") UserVO vo){
		return "user/signup.jsp";
	}
	
	@RequestMapping(value="/signup.do", method=RequestMethod.POST)
	public String signUp(@Valid @ModelAttribute("user") UserVO vo, BindingResult brs){
		if(brs.hasErrors()){ return "user/signup.jsp"; }
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		userService.insertUser(vo);
		return "redirect:getBoardList.do";
	}
	
	// 이메일 인증
	@RequestMapping(value="emailAuth.do")
	public String emailAuth(UserVO vo){
		userService.emailAuth(vo);
		return "redirect:login.do";
	}
	
	// 로그인
	@RequestMapping(value="/login.do")
	public String loginView(HttpSession session){
		return "user/login.jsp";
	}
	
	// mypage
	@RequestMapping("/mypage.do")
	public String mypageView(Principal principal, Model model, HttpServletResponse response){
		// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@22
		// a 로그인 -> a mypage -> a 로그아웃 -> b 로그인 -> 뒤로가기 -> a mypage -> b가 a정보 변경가능.
		// 캐시 사용 안 하기.
		// WebContentInterceptor
		response.setHeader("Cache-Control","no-store");
		
		model.addAttribute("user", userService.getUser(principal.getName()));
		return "user/mypage.jsp";
	}
	
	// 회원탈퇴
	@RequestMapping("/deleteUser.do")
	public String deleteUser(Principal principal){
		userService.deleteUser(principal.getName());
		return "redirect:logout.do";
	}
	
	// 개인정보수정
	@RequestMapping("/updateUser.do")
	public String updateUser(UserVO vo){
		userService.updateUser(vo);
		return "redirect:mypage.do";
	}
	
	// 이메일 변경
	@RequestMapping(value="/updateUserEmail.do", method=RequestMethod.GET)
	public String updateUserEmail(HttpSession session){
		return "user/updateUserEmail.jsp";
	}
	
	@RequestMapping(value="/updateUserEmail.do", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateUserEmail(UserVO vo, BindingResult brs) throws MethodArgumentNotValidException{
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 이메일 유효성 체크.
		new UpdateUserEmailValidator().validate(vo, brs, userService);
		if(brs.hasErrors()){ throw new MethodArgumentNotValidException(null, brs); }
		
		userService.updateUserEmail(vo);
		map.put("location", "logout.do");
		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}
	
	// 비밀번호 변경
	@RequestMapping(value="/updateUserPassword.do", method=RequestMethod.GET)
	public String updateUserPassword(){
		return "user/updateUserPassword.jsp";
	}
	
	@RequestMapping(value="/updateUserPassword.do", method=RequestMethod.POST)
	public String updateUserPassword(UserVO vo){
		vo.setPassword(passwordEncoder.encode(vo.getPassword()));
		userService.updateUserPassword(vo);
		return "redirect:logout.do";
	}
	
	// 아이디, 비밀번호 찾기
	@RequestMapping(value="/findUserInfo.do", method=RequestMethod.GET)
	public String findUserInfo(){	
		return "user/findUserInfo.jsp";
	}
	
	@RequestMapping(value="/findUserInfo.do", method=RequestMethod.POST)
	public String findUserInfo(UserVO vo){
		userService.findUserInfo(vo);
		return "redirect:login.do";
	}
}
