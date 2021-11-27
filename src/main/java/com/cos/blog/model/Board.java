package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터일때
	private String content; //섬머노트 라이브러리 : <html> 태그가 섞여서 디자인이 됨
	
	@ColumnDefault("0")
	private int count; // 조회수
	
	@ManyToOne //Many = board , one = user /한명의 유저는 여러개의 보드를 쓸수있다.
	@JoinColumn(name="userId")
	private User user; //DB는 오브젝트 저장할수 없으니 FK 사용, 자바는 오브젝트를 저장할수있다.
	//orm 이 
	@CreationTimestamp
	private Timestamp creatDate;
	
}
