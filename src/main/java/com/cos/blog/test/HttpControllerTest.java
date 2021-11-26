package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(Data) = RestController
//사용자가 요청 -> 응답(Http) = Controller

@RestController
public class HttpControllerTest {
	//http://localhost:8787/http/get(select)
	@GetMapping("/http/get")
	public String getTest(Member m) { //자동으로 멤버클래스 생성(MessageConverter)
		return "get 요청 : " +m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//http://localhost:8787/http/post(insert)
	@PostMapping("/http/post") //text/plain , application/json
	public String postTest(@RequestBody Member m) { //MessageConverter(스프링부트) 가 자동 매핑해줌
		return "post 요청: " +m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//http://localhost:8787/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청: "+m.getId()+","+m.getUsername()+","+m.getPassword()+","+m.getEmail();
	}
	//http://localhost:8787/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
