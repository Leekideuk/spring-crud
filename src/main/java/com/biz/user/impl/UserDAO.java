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
	
	// 회원 탈퇴
	public void deleteUser(String userId){
		System.out.println("===> UserDAO.deleteUser()");
		mybatis.delete("UserDAO.deleteUser", userId);
	}
	
	// 개인 정보 수정
	public void updateUser(UserVO vo){
		System.out.println("===> UserDAO.updateUser()");
		mybatis.update("UserDAO.updateUser", vo);
	}
	
	// 이메일 수정
	public void updateUserEmail(UserVO vo){
		System.out.println("===> UserDAO.updateUserEmail()");
		mybatis.update("UserDAO.updateUserEmail", vo);
	}
	
	// 비밀번호 수정
	public void updateUserPassword(UserVO vo){
		System.out.println("===> UserDAO.updateUserPassword()");
		mybatis.update("UserDAO.updateUserPassword", vo);
	}
	
	// 회원 정보 조회
	public UserVO getUser(String userId){
		System.out.println("===> UserDAO.getUser()");
		return (UserVO) mybatis.selectOne("UserDAO.getUser", userId);
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
	
	// 이메일 인증 키 일치 여부 체크
	public int CheckEmailAuthKey(UserVO vo){
		System.out.println("===> UserDAO.CheckEmailAuthKey()");
		return mybatis.selectOne("UserDAO.checkEmailAuthKey", vo);
	}
	
	// emailCert true로 변경
	public void updateEmailCertTrue(UserVO vo){
		System.out.println("===> UserDAO.updateEmailCertTrue()");
		mybatis.update("UserDAO.updateEmailCertTrue", vo);
	}
	
	// 이메일 이용해서 아이디, 비밀번호 찾기
	public UserVO findUserInfo(UserVO vo){
		System.out.println("===> UserDAO.findUserInfo()");
		return mybatis.selectOne("UserDAO.findUserInfo", vo);
	}
	
	
	public List<UserVO> getUserList(UserVO vo){
		System.out.println("===> UserDAO.getUserList()");
		return mybatis.selectList("UserDAO.getUserList", vo);
	}
}
