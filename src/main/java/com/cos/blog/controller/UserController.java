package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할수 있는 경로를 /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js/** , /css/** ,/img/**  허용
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	@GetMapping("/user/updateform")
	public String updateForm() {
		
		return "user/updateForm";
	}
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { //Data를 리턴해주는 컨트롤러 함수
		
		//POST 방식으로 key=value 데이터를 요청 ( 카카오 쪽으로 )
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		// httpHeader 오브젝트 생성
		headers.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		// httpBody 오브젝트 생성
		MultiValueMap<String,String> params = new LinkedMultiValueMap<String,String>();
		params.add("grant_type","authorization_code");
		params.add("client_id","bf693efead7d6dd1e8b1c733664d1e68");
		params.add("redirect_uri","http://localhost:8787/auth/kakao/callback");
		params.add("code",code);
		// httpHeader와 httpBody 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest =
				new HttpEntity<>(params,headers);
		// http 요청하기(post 방식으로) - 그리고 response 변수를 응답 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class);
		
		//Gson , Json Simple , ObjectMapper (Json 매핑 라이브러리들)
		ObjectMapper obMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = obMapper.readValue(response.getBody(),OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		// httpHeader 오브젝트 생성
		headers2.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
		headers2.add("Authorization","Bearer "+oauthToken.getAccess_token());
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest =
				new HttpEntity<>(headers2);
		// http 요청하기(post 방식으로) - 그리고 response 변수를 응답 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class);
		
		ObjectMapper obMapper2 = new ObjectMapper();
		KakaoProfile kakapProfile = null;
		try {
			kakapProfile = obMapper2.readValue(response2.getBody(),KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		// User 오브젝트 : username,passowrd,email
		//UUID tempPassword =UUID.randomUUID();
		//UUID란 중복 되지 않는 특정 값을 만들어내는 알고리즘
				
		
		System.out.println("카카오 아이디(번호) : " + kakapProfile.getId());
		System.out.println("카카오 이메일 : " + kakapProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 유저네임 : " + kakapProfile.getKakao_account().getEmail()+"_"+kakapProfile.getId());
		System.out.println("블로그서버 이메일 : " + kakapProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 비밀번호 : " + cosKey);
		
		User kakaoUser = User.builder()
				.username(kakapProfile.getKakao_account().getEmail()+"_"+kakapProfile.getId())
				.password(cosKey)
				.email(kakapProfile.getKakao_account().getEmail())
				.role(RoleType.User)
				.oauth("kakao")
				.build();
		// 가입자 혹은 비가입자 체크해서 처리하기
		User origin = userService.회원찾기(kakaoUser.getUsername());
		if(origin.getUsername()==null) {
			System.out.println("기존 회원이 아닙니다.");
			userService.회원가입(kakaoUser);	
		}
		// 로그인 처리
		Authentication authentication = 
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
	}


}
