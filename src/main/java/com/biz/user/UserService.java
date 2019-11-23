package com.biz.user;

import java.util.List;

public interface UserService {
	// 회원 등록
	void insertUser(UserVO vo);
	
	// 로그인
	UserVO login(UserVO vo);
	
	// 회원 수정
	UserVO updateUser(UserVO vo);
	
	// 회원 삭제
	void deleteUser(UserVO vo);
	
	// 회원 조회
	UserVO getUser(UserVO vo);
	
	// 아이디 중복 확인
	boolean existUserId(String userId);
	
	// 회원 목록 조회
	List<UserVO> getUserList(UserVO vo);
}
