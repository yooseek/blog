package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.PrincipalDetailService;

@Configuration  // bean 으로 등록
@EnableWebSecurity  // 시큐리티 필터 등록, 컨트롤러에 가기전에 인터셉터 = 설정을 이곳에서 한다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();  //ioc가 되면서 이 값을 스프링이 관리한다.
	}
	
	// 시큐리티가 대신 로그인 할때 password를 가로채기 하는데
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교를 할 수 있음
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()  //csrf 토큰 비활성화(테스트할 때 걸어두는게 좋음)
		.authorizeRequests()
			.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**")      //auth쪽으로 들어오는건 누구나 들어올수 있다.
			.permitAll()				
			.anyRequest()				//이 외에 어떤 리퀘스트는
			.authenticated()			// 인증이 되야됭
		.and()
			.formLogin()				// 인증이 필요한 곳으로 가면
			.loginPage("/auth/loginForm") // 해당 페이지로 이동
			.loginProcessingUrl("/auth/loginProc")//시큐리티가 해당주소로 오는 로그인을 가로채서 대신 로그인 해준다.
			.defaultSuccessUrl("/");    // 석세스하면 "/"로 이동시켜준다.
		
		
			
	}
}
