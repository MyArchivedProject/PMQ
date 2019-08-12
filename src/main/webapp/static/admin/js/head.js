var url = "paper" //默认值为测试者界面 
	var adminId //保存管理员的id,管理员更新自身密码时用到 
	var adminUser //保存管理员的登录账户 ,管理员更新自身密码时用到 
	function getUserName() {
		$.ajax({
			url : "getUserInfo",
			success : function(all) {
				$("#adminName").text(" " + all.name)
				adminId = all.id
				adminUser = all.user
				if (all.rank == 2) {
					$("#head-nav-admin").hide()
				}
			}
		})

	}
	//退出登录
	function logout() {
		$.ajax({
			url : "logout",
			success : function() {
				window.location = "login"
			}
		})
	}
	//设置密码
	function passSet() {
		alert("有待开发 ")
	}
	//设置要传递的url地址 ,并触发获取界面事件
	function setUrl(url) {
		this.url = url;
		setContent()
		$("nav ul li button").removeClass("head-top-nav")
		if (this.url == "paper") {
			$("#head-nav-paper").addClass("head-top-nav")
		} else if (this.url == "ques") {
			$("#head-nav-ques").addClass("head-top-nav")
		} else if (this.url == "eva") {
			$("#head-nav-eva").addClass("head-top-nav")
		} else if (this.url == "target") {
			$("#head-nav-target").addClass("head-top-nav")
		} else if (this.url == "post") {
			$("#head-nav-target").addClass("head-top-nav")
		} else if (this.url == "admin") {
			$("#head-nav-admin").addClass("head-top-nav")
		}
	}
	//获取后台管理所对应的界面
	function setContent() {
		$.ajax({
			url : this.url,
			success : function(data) {
				$("#head-content").html(data)
			}
		})
	}
	//打开修改密码界面 
	function openHeadUpdatePass() {
		$(".headUpdatePass-dowebok").show()
		$("#mask").show()
	}
	//关闭 修改密码界面 
	function quitHeadUpdatePass() {
		$(".headUpdatePass-dowebok").hide()
		$("#mask").hide()
	}
	//管理员修改自己的密码
	function headUpdatePass() {
		if($("#oldPass").val().trim()==""){
			$("#tip-oldPass").show()
			return
		}else{
			$("#tip-oldPass").hide()
		}
		if($("#newPass").val().trim().length>0 && $("#newPass").val().trim().length<26){
			$("#tip-newPass").hide()		
		}else{
			$("#tip-newPass").show()
			return
		}
		if ($("#newPass").val() == $("#reNewPass").val()) {
			$.ajax({
				type : 'post',
				url : 'admin/updateSelfPass',
				data : {
					adminId : adminId,
					user : adminUser,
					oldPass : $("#oldPass").val(),
					newPass : $("#newPass").val()
				},
				success : function(all) {
					if (all.message == "success") {
						alert("修改成功")
						quitHeadUpdatePass()
					} else {
						$("#tip-reNewPass").hide()
						$("#tip-oldPass").text(all.message).show()
					}
				}
			})
		}else{
			$("#tip-reNewPass").show()
		}

	}
	//初始化 
	$(function() {
		setContent()
		getUserName()
	})