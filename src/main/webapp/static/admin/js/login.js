function login() {
		$("#loginID").text("登 录 中**");
		$("#loginID").attr('disabled', true);
		var user = $("#user").val()
		var pass = $("#pass").val()
		var code = $("#code").val()
		var data = {
			"user" : user,
			"pass" : pass,
			"code" : code
		}
		$.ajax({
			type : "post",
			url : "validate",
			dataType : "json",
			//contentType: "application/json;charset=UTF-8", //连接格式为json
			data : {
				"data" : JSON.stringify(data)
			},
			success : function(message) {
				if (message.message == "true") {
					window.location.href = "head"
				} else {
					$('#code-tip').text(message.message).show()
					$("#loginID").text("登 录");
					$("#loginID").attr('disabled', false);
				}
			},
			error : function() {				
				alert("网络超时,请刷新");
				$("#loginID").text("登 录");
				$("#loginID").attr('disabled', false);
			},
			complete: function(){
				
			}
		})
	}
	$(
			function() {			
				$(".head").hide()//解决session属性admin过期后，页面重定向到login.html后 head.html依然存在的问题 
				//初始化验证码操作
				$('#login-code').attr("src",
						"/PMQ/common/getCode");
				//更新验证码操作
				$("#login-code")
						.click(
								function() {
									//兼容性问题//Math.random()解决验证码img的src网址不变的话，Firefox或ie不进行服务器刷新的问题
									$("#login-code")
											.attr("src",
													"/PMQ/common/getCode?r="+Math.random());
									$("#code-tip").hide()
								})
				//格式不规范提示
				$('#user').on('blur', function() {
					var dataInput = $.trim($(this).val())
					if (dataInput.length<10 && dataInput.length>0) {
						$('#user-tip').hide()						
					} else {
						$('#user-tip').text("用户名由1到10个字符组成").show()
					}
				})
				$('#pass').on('blur', function() {
					var dataInput =$.trim($(this).val())
					if (dataInput.length > 0 && dataInput.length < 25) {
						$('#pass-tip').hide()
					} else {						
						$('#pass-tip').text("密码由1到25个字符组成").show()
					}
				})
				//回车事件绑定   
				$('#code').bind('keypress', function(event) {  
					if (event.keyCode == "13") {         
						//event.preventDefault();   
						login()    
					}
				});				
			})