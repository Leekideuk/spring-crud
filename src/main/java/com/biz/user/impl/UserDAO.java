package com.biz.user.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.biz.user.UserVO;

@Repository("userDAO")
public class UserDAO {
	@Autowired
	private SqlSessionTemplate mybatis;
	
	// 회원 등록
	public void inserUser(UserVO vo){
		System.out.println("===> UserDAO.insertUser()");
		mybatis.insert("UserDAO.insertUser", vo);
	}
	
	// 로그인 결과 반환
	public UserVO getLoginResult(UserVO vo){
		System.out.println("===> UserDAO.getLoginResult()");
		return (UserVO) mybatis.selectOne("UserDAO.getLoginResult", vo);
	}
	
	// 회원 탈퇴
	public void deleteUser(UserVO vo){
		System.out.println("===> UserDAO.deleteUser()");
		mybatis.delete("UserDAO.deleteUser", vo);
	}
	
	// 회원 정보 수정
	public void updateUser(UserVO vo){
		System.out.println("===> UserDAO.updateUser()");
		mybatis.update("UserDAO.updateUser", vo);
	}
	
	// 회원 정보 조회
	public UserVO getUser(UserVO vo){
		System.out.println("===> UserDAO.getUser()");
		return (UserVO) mybatis.selectOne("UserDAO.getUser", vo);
	}
	
	// userId 중복 확인
	public int existUserId(String userId){
		System.out.println("===> UserDAO.existUerId()");
		return mybatis.selectOne("UserDAO.existUserId", userId);
	}
	
	// email 중복 확인
	public int existEmail(String email){
		System.out.println("===> UserDAO.existEmail()");
		return mybatis.selectOne("UserDAO.existEmail", email);
	}
	
	
	
	
	public List<UserVO> getUserList(UserVO vo){
		System.out.println("===> UserDAO.getUserList()");
		return mybatis.selectList("UserDAO.getUserList", vo);
	}
}
