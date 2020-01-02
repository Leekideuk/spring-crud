package com.biz.user;

import java.util.List;

public interface UserService {
	// 회원 등록
	void insertUser(UserVO vo);
	
	// 인증 메일 보내기
	void sendAuthMail(UserVO vo);
	
	// 이메일 인증
	void emailAuth(UserVO vo);
	
	// 개인정보 수정
	void updateUser(UserVO vo);
	
	// 이메일 수정
	void updateUserEmail(UserVO vo);
	
	// 비밀번호 수정
	void updateUserPassword(UserVO vo);
	
	// 회원 삭제
	void deleteUser(String userId);
	
	// 회원 조회
	UserVO getUser(String userId);
	
	// 아이디 중복 확인
	boolean existUserId(String userId);
	
	// 이메일 중복 확인
	boolean existEmail(String email);
	
	// 이메일 이용해서 아이디, 비밀번호 찾기
	void findUserInfo(UserVO vo);
	
	// 회원 목록 조회
	List<UserVO> getUserList(UserVO vo);
}
