let index = {
	init: function() {
		$("#btn-board-save").on("click", () => { //function(){}, ()=>{} this 바인딩을 위해서!
			this.save();
		});
		$("#btn-delete").on("click", () => { 
			this.deleteById();
		});
		$("#btn-board-update").on("click", () => { 
			this.update();
		});
		$("#btn-reply-save").on("click", () => { 
			this.replySave();
		});
	},
	save: function() {
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};
		
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data), //js 오브젝트인 data를 json타입으로 변환해서 보냄
			contentType: "application/json; charset=utf-8" //보내는 바디 데이터가 무슨 타입인지(MIME)
			//dataType: "json"   //응답 데이터 타입(기본은 String) json형식으로 받으면 스프링이 js오브젝트로 변환시켜준다.
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			console.log(resp);
			location.href ="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	deleteById: function() {
		let id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/"+id,
			//dataType: "json"   //응답 데이터 타입(기본은 String) json형식으로 받으면 스프링이 js오브젝트로 변환시켜준다.
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			console.log(resp);
			location.href ="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	update: function() {
		let id = $("#id").text();
		let data = {
			title: $("#title").val(),
			content: $("#content").val(),
		};		
		$.ajax({
			type: "PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data), //js 오브젝트인 data를 json타입으로 변환해서 보냄
			contentType: "application/json; charset=utf-8" //보내는 바디 데이터가 무슨 타입인지(MIME)
			//dataType: "json"   //응답 데이터 타입(기본은 String) json형식으로 받으면 스프링이 js오브젝트로 변환시켜준다.
		}).done(function(resp){
			alert("글수정이 완료되었습니다.");
			console.log(resp);
			location.href ="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	},
	replySave: function() {
		let data = {
			content: $("#reply-content").val(),
		};
		let boardId = $("#boardId").val();
		$.ajax({
			type: "POST",
			url: `/api/board/${boardId}/reply`,
			data: JSON.stringify(data), //js 오브젝트인 data를 json타입으로 변환해서 보냄
			contentType: "application/json; charset=utf-8" //보내는 바디 데이터가 무슨 타입인지(MIME)
			//dataType: "json"   //응답 데이터 타입(기본은 String) json형식으로 받으면 스프링이 js오브젝트로 변환시켜준다.
		}).done(function(resp){
			alert("댓글 등록이 완료되었습니다.");
			console.log(resp);
			location.href =`/board/${boardId}`;
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
}
index.init();