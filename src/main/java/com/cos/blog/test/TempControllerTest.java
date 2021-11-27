package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("temphome()");
		//파일리턴 기본경로 : src/main/resources/static
		return "home";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//파일리턴 기본경로 : src/main/resources/static
		return "test";
	}
	

}
