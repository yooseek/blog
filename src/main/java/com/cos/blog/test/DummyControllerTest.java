package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repogitory.UserRepository;

@RestController //html 이 아니라 data를 리턴해주는 controller
public class DummyControllerTest {
	
	@Autowired   //(의존성 주입) 메모리에 올라온 UserRepository를 DI해서 사용하겠다
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);	
		} catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 아이디는 없는 아이디입니다.";
		}
		
		return "삭제 되었습니다. id : " + id;
	}
	
	// save 함수는 id를 전달하지 않으면 insert를 해주고 
	// id를 전달해줄때는 없으면 insert 있으면 update
	@Transactional  // (더티체킹) : 함수 종료시에 자동 커밋
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id,@RequestBody User requestUser) {
		// json데이터로 요청 했는데 스프링부트가 MessageConverter의 Jacson라이브러리로
		// 자동으로 자바 오브젝트로 변환시켜준다.
		System.out.println("id :" + id);
		System.out.println("password :" + requestUser.getPassword());
		System.out.println("eamil :" + requestUser.getEmail());
		
		// 이때 영속화 컨테이너에 영속화 진행
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(requestUser);  //더티 체킹하면 필요없음
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한페이지당 두건에 데이터를 리턴받아볼 예정
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable){
		// http://localhost:8787/blog/dummy/user?page=0
		// 페이지 별로 사이즈 정하고 부를수 있음
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}
	// {id} 주소로 파라메터 전송받을수 있음
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// 데이터베이스에서 없으면 null이 리턴되기 때문에 
		// Optional로 객체를 감싸서 가져온다.
		/*
		 * User user = userRepository.findById(id).orElseGet(new Supplier<User>() { //
		 * 데이터베이스에 값이 없으면 빈 객체를 유저에 넣어서 리턴한다.
		 * 
		 * @Override public User get() { return new User(); } });
		 */		
		
		// exception으로 처리하기 , 람다식
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("해당 사용자는 없습니다. id : " +id);
		});
		
		// 요청 : 웹 브라우저 (html)
		// 리턴 : 자바 오브젝트 (user객체)
		// 변환 : 웹브라우저가 이해할수 있는 데이터 -> json(스프링은 Gson라이브러리)
		// 스프링부트는 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jaxkson 라이브러리를 호출
		// user 오브젝트를 json으로 변환해서 브라우저에 던져준다.
		
		return user;
	}
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
