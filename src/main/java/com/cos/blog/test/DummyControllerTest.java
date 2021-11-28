package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repogitory.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired   //(의존성 주입) 메모리에 올라온 UserRepository를 DI해서 사용하겠다
	private UserRepository userRepository;
	
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+ user.getPassword());
		System.out.println("email: "+ user.getEmail());
		
		
		user.setRole(RoleType.User);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}

}
