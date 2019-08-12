var data;// 保存所有职位的名称
var flag=[false,false,false]; // 表单的输入 是否全部符合格式
$(function() {
	connectServerGetPost()
	genePostHtml()
	$('input').val('')
	$("#name").blur(function(){
		var pat=new RegExp(/^[\u4e00-\u9fa5a-zA-Z]{2,10}$/)
		if(pat.test($("#name").val())){
			flag[0]=true
			$("#tip-name").hide()
		}else{
			flag[0]=false
			$("#tip-name").show()
		}
	})
	$("#email").blur(function(){
		if($("#email").val().length>50){
			$("#tip-email").text("邮箱最大长度不能超过50个字符").show()
			return 
		}
		var pat=new RegExp(/^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/)
		if(pat.test($("#email").val())){	
			$("#tip-email").hide()	
			flag[1]=true
		}else{
			flag[1]=false
			$("#tip-email").show()
		}
		
	})
	$("#tele").blur(function(){
		if($("#tele").val().length==11){
			flag[2]=true
			$("#tip-tele").hide()
		}else{
			flag[2]=false
			$("#tip-tele").show()
		}		
	})

})
// 生成职位下拉列表
function genePostHtml(){
	$("#post").append("<option id='tip-post'>请选择测评职位</option>")
	for(var i=0;i<data.length;i++){
		$("#post").append("<option value='"+data[i]+"'>"+data[i]+"</option>")
	}
}
// 从服务器获取所有的职位//
function connectServerGetPost() {
	$.ajax({
		url : 'getPost',
		async : false,
		type : 'post',
		success : function(all) {
			data=all
		}
	})
}
// 验证表单
function check(){
	if($("#tip-post").is(":checked")){
		$("#post").css("color","red")
		return false
	}else if(flag[0]==true && flag[1]==true &&flag[2]==true){
		$.ajax({
			url : 'into',
			async : false,
			type : 'post',
			data: $("#register-info").serialize(),
			success : function(all) {
				if(all.flag==1){
					window.location.href="head"
				}
				if(all.flag==0){
					alert("网络超时,请刷新界面！")
				}
			},
			error:function(){
				alert("网络超时,请刷新界面！")
			}
		})
	}else {
		if(flag[0]==false){
			$("#tip-name").show()
		}
		if(flag[1]==false){
			$("#tip-email").show()
		}
		if(flag[2]==false){
			$("#tip-tele").show()
		}
	}
}

window.onload = function (){
	//解决IE10 以下的游览器对placeholder的兼容性问题
	if( !('placeholder' in document.createElement('input')) ){   
	    $('input[placeholder],textarea[placeholder]').each(function(){   
	      var that = $(this),    
	      text= that.attr('placeholder');    
	      if(that.val()===""){    
	        that.val(text).addClass('placeholder');    
	      }    
	      that.focus(function(){    
	        if(that.val()===text){    
	          that.val("").removeClass('placeholder');    
	        }    
	      })    
	      .blur(function(){    
	        if(that.val()===""){    
	          that.val(text).addClass('placeholder');    
	        }    
	      })    
	      .closest('form').submit(function(){    
	        if(that.val() === text){    
	          that.val('');    
	        }    
	      });    
	    });    
	 }
}