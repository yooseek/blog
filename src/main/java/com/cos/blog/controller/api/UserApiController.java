package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("save 호출완료");
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바 오브젝트를 Json으로 변환해서 리턴(Jackson)
	}
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user,@AuthenticationPrincipal PrincipalDetail principal) {
		userService.회원수정(user);

		//세션등록
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바 오브젝트를 Json으로 변환해서 리턴(Jackson)
	}
	
	// 스프링 시큐리티 이용해서 로그인 할 것
	/* 
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user,HttpSession session) {
		System.out.println("login 호출완료");
		user.setRole(RoleType.User);
		User pricipal = userService.로그인(user); //접근주체
		if(pricipal != null) {
			session.setAttribute("principal", pricipal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //자바 오브젝트를 Json으로 변환해서 리턴(Jackson)
	}
	*/

}
