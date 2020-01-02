package com.biz.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.board.impl.BoardDAO;
import com.biz.user.UserService;
import com.biz.user.UserVO;
import com.common.mail.MailHandler;
import com.common.util.FileUtil;
import com.common.util.TempKey;
import com.common.vo.FileVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	MailHandler mailHandler;
	@Resource(name="mailAddress")
	String mailAddress;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	// 회원가입
	@Override
	public void insertUser(UserVO vo){
		String authKey = new TempKey().getKey(20, false);
		vo.setEmailAuthKey(authKey);
		userDAO.inserUser(vo);
		sendAuthMail(vo);
	}
	
	// 회원탈퇴.
	@Override
	public void deleteUser(String userId) {
		List<FileVO> fileList = boardDAO.getBoardFileListByUserId(userId);
		userDAO.deleteUser(userId);
		// 서버에 업로드한 파일 삭제.
		for(FileVO file : fileList){
			FileUtil.deleteFile(file);
		}
	}
	
	// 개인정보수정.
	@Override
	public void updateUser(UserVO vo) {
		userDAO.updateUser(vo);
	}
	
	// 이메일수정
	@Override
	public void updateUserEmail(UserVO vo){
		String authKey = new TempKey().getKey(20, false);
		vo.setEmailAuthKey(authKey);
		userDAO.updateUserEmail(vo);
		sendAuthMail(vo);
	}
	
	// 비밀번호수정
	@Override
	public void updateUserPassword(UserVO vo){
		userDAO.updateUserPassword(vo);
	}
	
	// 회원정보조회
	@Override
	public UserVO getUser(String userId) {
		return userDAO.getUser(userId);
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
	
	// 이메일 이용해서 아이디, 비밀번호 찾기
	@Override
	public void findUserInfo(UserVO vo){
		UserVO user = userDAO.findUserInfo(vo);
		if(user == null){
			System.out.println("존재하지 않는 이메일 입니다.");
			return;
		}
		
		String tempPassword = new TempKey().getKey(10, false);
		user.setPassword(passwordEncoder.encode(tempPassword));
		userDAO.updateUserPassword(user);
		user.setPassword(tempPassword);
		
		try{
			mailHandler.setSubject("[Spring Test Test] 회원 정보");
			mailHandler.setText(new StringBuffer().append("<h1>회원 정보</h1>")
					.append("<p>아이디 : ")
					.append(user.getUserId()+"</p>")
					.append("<p>비밀번호 : ")
					.append(user.getPassword()+"</p>")
					.toString());
			mailHandler.setFrom(mailAddress, "Spring Board Test");
			mailHandler.setTo(vo.getEmail(), vo.getUserId());
			mailHandler.send();
		}catch(Exception e){
			System.out.println("UserService.findUserInfo() ::: " + e);
		}
		
	}
	
	// 인증메일 보내기
	@Override
	public void sendAuthMail(UserVO vo){
		try{
			mailHandler.setSubject("[Spring Test Test] 이메일 인증");
			mailHandler.setText(new StringBuffer().append("<h1>이메일 인증</h1>")
					.append("<a href='http://172.30.1.42:8080/emailAuth.do?userId=")
					.append(vo.getUserId())
					.append("&email=")
					.append(vo.getEmail())
					.append("&emailAuthKey=")
					.append(vo.getEmailAuthKey())
					.append("' target='_blank'>이메일 인증 확인</a>")
					.toString());
			mailHandler.setFrom(mailAddress, "Spring Board Test");
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
	
	@Override
	public List<UserVO> getUserList(UserVO vo) {
		return userDAO.getUserList(vo);
	}
}
