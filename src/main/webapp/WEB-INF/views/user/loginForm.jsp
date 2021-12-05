<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
<form action="/auth/loginProc" method="post">
	<div class="form-group">
		<label for="username">Username:</label> 
		<input name="username" type="Text" class="form-control" placeholder="Enter username" id="username">
	</div>
	<div class="form-group">
		<label for="password">Password:</label> 
		<input name="password" type="password" class="form-control" placeholder="Enter password" id="password">
	</div>
	
	<button id="btn-login" class="btn btn-primary">로그인</button>
	<a href="https://kauth.kakao.com/oauth/authorize?client_id=bf693efead7d6dd1e8b1c733664d1e68&redirect_uri=http://localhost:8787/auth/kakao/callback&response_type=code"><img height="38px" src="/image/kakao_login_button.png" ></a>

</form>
</div>

<%@ include file="../layout/footer.jsp"%>

