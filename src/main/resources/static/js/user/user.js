let index= {
    init: function(){
        $("#btn-save").on("click",()=>{
            this.save();
        })
    },
    save : function(){
        //alert("user save 함수 호출됨");
        let data={
            username : $("#username").val(),
            password : $("#password").val(),
            email : $("#email").val(),   
        }
        //console.log(data);
        $.ajax().done().fail();
    }
}
index.init();