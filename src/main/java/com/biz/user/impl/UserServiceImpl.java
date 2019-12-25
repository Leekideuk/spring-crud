package com.biz.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.user.UserService;
import com.biz.user.UserVO;
import com.common.mail.MailHandler;
import com.common.mail.TempKey;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	MailHandler mailHandler;
	
	// 회원가입
	@Override
	public void insertUser(UserVO vo){
		String authKey = new TempKey().getKey(20, false);
		vo.setEmailAuthKey(authKey);
		userDAO.inserUser(vo);
		
		try{
			mailHandler.setSubject("[Spring Test Test] 회원가입 이메일 인증");
			mailHandler.setText(new StringBuffer().append("<h1>이메일 인증</h1>")
					.append("<a href='http://172.30.1.42:8080/emailAuth.do?userId=")
					.append(vo.getUserId())
					.append("&email=")
					.append(vo.getEmail())
					.append("&emailAuthKey=")
					.append(vo.getEmailAuthKey())
					.append("' target='_blank'>이메일 인증 확인</a>")
					.toString());
			mailHandler.setFrom("weguooo@gmail.com","Spring Board Test");
			mailHandler.setTo(vo.getEmail(), vo.getUserId());
			mailHandler.send();
		}catch(Exception e){
			System.out.println("UserService.insertUser() ::: " + e);
		}
	}
	
	// 이메일 인증
	public void emailAuth(UserVO vo){
		if(userDAO.CheckEmailAuthKey(vo) == 1){
			userDAO.updateEmailCertTrue(vo);
		}else{
			// 이메일 인증 실패
			System.out.println("이메일 인증 실패");
		}
	}
	
	// 로그인
	@Override
	public UserVO login(UserVO vo){
		return userDAO.getLoginResult(vo);
	}
	
	//회원탈퇴
	@Override
	public void deleteUser(UserVO vo) {
		userDAO.deleteUser(vo);
	}
	
	// 회원정보수정. 변경된 정보 반환.
	@Override
	public UserVO updateUser(UserVO vo) {
		userDAO.updateUser(vo);
		return userDAO.getUser(vo);
	}
	
	// 회원정보조회
	@Override
	public UserVO getUser(UserVO vo) {
		return userDAO.getUser(vo);
	}

	// 아이디 중복 확인.
	@Override
	public boolean existUserId(String userId) {
		if(userDAO.existUserId(userId) == 0){ return false; }	
		return true;
	}
	
	// 이메일 중복 확인
	@Override
	public boolean existEmail(String email){
		if(userDAO.existEmail(email) == 0) return false;
		return true;
	}
	
	
	@Override
	public List<UserVO> getUserList(UserVO vo) {
		return userDAO.getUserList(vo);
	}

	

}
