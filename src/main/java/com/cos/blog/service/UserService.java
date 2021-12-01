package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repogitory.UserRepository;

@Service //스프링이 컴포넌트 스캔을 해서 Bean에 등록해줌
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); //비밀번호
		String encPassword = encoder.encode(rawPassword); //암호화한 해쉬 비밀번호
		user.setPassword(encPassword);
		user.setRole(RoleType.User);
		userRepository.save(user);
	}
	/*
	@Transactional(readOnly=true) //select 할 때 트랜잭션 시작,서비스 종료시 트랜잭션 종료(정합성 유지)
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
	}
	*/
}
