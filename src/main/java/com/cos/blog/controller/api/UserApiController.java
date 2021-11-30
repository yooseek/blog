package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) {
		System.out.println("save 호출완료");
		// 실제로 DB에 insert 하고 return 하면 됨
		user.setRole(RoleType.User);
		int result = userService.회원가입(user);
		if(result == 1) {
			
		}else {
			
		}
		return new ResponseDto<Integer>(HttpStatus.OK,result); //자바 오브젝트를 Json으로 변환해서 리턴(Jackson)
	}

}
