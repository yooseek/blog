package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//orm - > java(다른언어) object -> table로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert //null 값은 빼고 인서트하게 해줌
@Entity //User 클래스가 mysql에 테이블이 생성됨
public class User {
	
	@Id  // primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id;  //시퀀스, auto_increment
	
	@Column(nullable = false, length =30, unique = true)
	private String username;   //아이디
	
	@Column(nullable = false, length =100) // 1234 => 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length =50)
	private String email;
	
	//@ColumnDefault("'user'") //디폴트값을 user로 한다
	// DB는 롤타입이 없기때문에 String값이 들어가야하니까 아래 어노테이션 추가
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋다. // Admin,User,Manager
	
	private String oauth;  //kakao, google...
	
	//내가 직접 시간을 넣으려면 Timestamp.valueOf(LocalDateTime.now())
	@CreationTimestamp //시간이 자동입력
	private Timestamp createDate;	
}
