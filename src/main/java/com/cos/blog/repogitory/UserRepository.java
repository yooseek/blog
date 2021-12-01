package com.cos.blog.repogitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;

//DAO
// 자동으로 빈으로 등록되어서 @Repository 생략가능
// 스프링부트가 될때 메모리에 컴포넌트스캔해서 올릴때 자동으로 올려줌
public interface UserRepository extends JpaRepository<User,Integer>{

	//select * from user where username = 1?;
	Optional<User> findByUsername(String username);
}


// JPA Naming 전략
// 자동으로 select * from where username =? And password =?; 
// 이런 쿼리가 동작한다.
//User findByUsernameAndPassword(String username,String password);

/*
// 위에랑 같은 함수
@Query(value="select * from where username =?1 And password =?2;", nativeQuery = true)
User login(String username,String password);
*/
