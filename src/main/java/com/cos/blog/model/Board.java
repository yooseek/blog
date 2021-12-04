package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터일때
	private String content; //섬머노트 라이브러리 사용할꺼 : <html> 태그가 섞여서 디자인이 됨
	// 그래서 용량이 대용량이 필요함
	
	//@ColumnDefault("0")
	private int count; // 조회수
	
	@ManyToOne(fetch = FetchType.EAGER) //Many = board , one = user /한명의 유저는 여러개의 보드를 쓸수있다.
	@JoinColumn(name="userId")
	private User user; //DB는 오브젝트 저장할수 없으니 FK 사용, 자바는 오브젝트를 저장할수있다.
	//orm 이 알아서 포링키 설정해줌
	
	@OneToMany(mappedBy = "board",fetch = FetchType.LAZY)  //mappedby는 연관관계의 주인이 아니다.(포링키가 아니다.)
	private List<Reply> reply;  //데이터테이블에는 안만들고 연관된 reply 객체들만 가져온다.
	// 그냥 조인문을 통해 reply객체들을 가져오기위한 객체입니다.
	
	@CreationTimestamp
	private Timestamp creatDate;
	
}
