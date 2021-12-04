package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repogitory.BoardRepository;
import com.cos.blog.repogitory.UserRepository;

@Service //스프링이 컴포넌트 스캔을 해서 Bean에 등록해줌
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void 글쓰기(Board board,User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);	
	}
	@Transactional(readOnly = true)
	public Board 상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});

	}
	@Transactional
	public void 삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	@Transactional
	public void 글수정하기(int id,Board requestboard) {
		// 영속화 해서 영속성컨텍스트로 가져오기
		Board board = boardRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		});
		// 해당 정보를 바꾸고 함수를 종료하면 트랜잭션이 종료된다. 이때 더티체킹 발생 - 자동업데이트됨
		board.setTitle(requestboard.getTitle());
		board.setContent(requestboard.getContent());		
	}
}
