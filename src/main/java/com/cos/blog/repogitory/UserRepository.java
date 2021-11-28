package com.cos.blog.repogitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//DAO
// 자동으로 빈으로 등록되어서 @Repository 생략가능
// 스프링부트가 될때 메모리에 컴포넌트스캔해서 올릴때 자동으로 올려줌
public interface UserRepository extends JpaRepository<User,Integer>{

}
