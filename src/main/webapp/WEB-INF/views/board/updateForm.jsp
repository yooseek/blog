<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
		글 번호 : <span id ="id"><i>${board.id}</i></span>
		작성자 : <span><i> ${board.user.username} </i></span>	
		</div>
		<div class="form-group">
			<label for="title">Title</label> 
			<input value="${board.title}" type="Text" class="form-control" placeholder="Enter title" id="title">
		</div>

		<div class="form-group">
			<label for="content">Content:</label>
			<textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
		</div>

	</form>
	<button id="btn-board-update" class="btn btn-primary">글수정 완료</button>
	
</div>
<script>
      $('.summernote').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 100
      });
    </script>
<script src="/js/board/board.js"></script>
    
<%@ include file="../layout/footer.jsp"%>