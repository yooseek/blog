let index = {
	init: function() {
		$("#btn-save").on("click", () => { //function(){}, ()=>{} this 바인딩을 위해서!
			this.save();
		});
		/*$("#btn-login").on("click", () => { 
			this.login();
		})*/
	},
	save: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
		};
		
		//ajax는 default가 비동기 호출
		//ajax 통신을 이용해서 3개의 데이터를 JSON으로 변경하여 insert 요청
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바오브젝트로 변환시켜줌
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //js 오브젝트인 data를 json타입으로 변환해서 보냄
			contentType: "application/json; charset=utf-8" //보내는 바디 데이터가 무슨 타입인지(MIME)
			//dataType: "json"   //응답 데이터 타입(기본은 String) json형식으로 받으면 스프링이 js오브젝트로 변환시켜준다.
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			console.log(resp);
			location.href ="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}
	
	/*login: function() {
		//alert("user save 함수 호출됨");
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val(),
		};
		//console.log(data);
		
		//ajax는 default가 비동기 호출
		//ajax 통신을 이용해서 3개의 데이터를 JSON으로 변경하여 insert 요청
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바오브젝트로 변환시켜줌
		$.ajax({
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), //js 오브젝트인 data를 json타입으로 변환해서 보냄
			contentType: "application/json; charset=utf-8" //보내는 바디 데이터가 무슨 타입인지(MIME)
			//dataType: "json"   //응답 데이터 타입(기본은 String) json형식으로 받으면 스프링이 js오브젝트로 변환시켜준다.
		}).done(function(resp){
			alert("로그인이 완료되었습니다.");
			//console.log(resp);
			location.href ="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});
	}*/
}
index.init();