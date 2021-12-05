<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form action="/user/join">
		<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">Username:</label> <input type="Text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly>
		</div>
		<c:choose>
			<c:when test="${empty principal.user.oauth}">
				<div class="form-group">
					<label for="email">Email address:</label> <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
				</div>
			</c:when>
			<c:otherwise>
				<div class="form-group">
					<label for="email">Email address:</label> <input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email" readonly>
				</div>
			</c:otherwise>
		</c:choose>
		<c:if test="${empty principal.user.oauth}">
			<div class="form-group">
				<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>
	</form>
	
	<button class="btn btn-secondary" onclick="history.back()">돌아가기</button>
	<button id="btn-update" class="btn btn-primary">회원수정완료</button>
</div>
<script src="/js/user/user.js"></script>

<%@ include file="../layout/footer.jsp"%>

