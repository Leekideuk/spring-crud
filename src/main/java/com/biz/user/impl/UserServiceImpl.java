package com.biz.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biz.user.UserService;
import com.biz.user.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	
	// 회원가입
	@Override
	public void insertUser(UserVO vo) {
		userDAO.inserUser(vo);
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
	
	@Override
	public List<UserVO> getUserList(UserVO vo) {
		return userDAO.getUserList(vo);
	}

	

}
