	var nums = 0;//数据总量
	var num = 0;//截至到当前页面的数据总量  
	var totalPage = 0;//总页数
	var data;//从服务端获取的数据
	var i = 0;//记录data[i]的下标
	var currentPage = 0;//记录当前页码
	var totalPage = 0;//共多少页
	var name = ""//保存姓名,用来进行数据查询
	//同步操作从服务端获取题库 
	function connectServer() {
		$.ajax({
			url : 'admin/getAdminByN',
			async : false,
			type : 'post',
			data : $("#search-form").serialize(),
			success : function(all) {
				data = all.all
				//设置数据总量
				nums = data.length
				//设置总页数
				if (nums % 10 == 0) {
					totalPage = parseInt(nums / 10)
				} else {
					totalPage = parseInt(nums / 10) + 1
				}
				$("#spanTotalPage").text(totalPage);
				jumpStart()
			}
		})
	}
	//创建表格
	function setAdminTable() {
		var tr = $("<tr></tr>");
		tr.append("<td>" + (i + 1) + "</td>")
		if (1 == data[i].rank) {
			tr.append("<td>" + "超级管理员" + "</td>")
		} else {
			tr.append("<td>" + "普通管理员" + "</td>")
		}
		tr.append("<td>" + data[i].name + "</td>")
		tr.append("<td>" + data[i].user + "</td>")
		tr.append("<td>" + "****" + "</td>")
		tr.append("<td>" + new Date(data[i].time).toLocaleDateString()
				+ "</td>")
		tr
				.append("<td>"
						+ "<button class='opt-but b' onClick='deleteAdmin("
						+ i + ")'>删除</button>" + 
						"<button class='opt-but a' onClick='openUpdateAdmin(" + i + ")'>重置</button>"+"</td>")
		tr.appendTo($("tbody"))
		
	}
	//分页显示给客户端//设置当前页码//此方法名每个页面都必须相同 
	function setPageContent() {
		$("tbody").html("");
		for (i = (currentPage - 1) * 10; i < num; i++) {
			setAdminTable();
		}
		$("#spanCurrentPage").text(currentPage)
	}
	$(function() {
		connectServer()
		
		
	})
	//删除管理员 
	function deleteAdmin(e) {
		var msg = "您确定要删除的这条数据？"+"\n被删除者姓名:"+data[e].name;
		if (confirm(msg)==true){
			$.ajax({
				url : 'admin/deleteAdmin',
				async : false,
				type : 'post',
				data : {
					id : data[e].id
				},
				success : function(all) {
					alert(all.message)
					if(all.message=="删除成功"){
						connectServer()
					}
				}
			})
		}
	}
	//增加管理员操作
	function addAdmin() {		
		//增加管理员表单验证
			var flagVali= true
			var data_name = $.trim($('#add-name').val())
			if (data_name.length<7 && data_name.length>1) {
				$('#tip-add-name').hide()						
			} else {
				$('#tip-add-name').show()
				flagVali=false;
			}

			var data_userid = $.trim($("#add-userid").val())
			if (data_userid.length>0 && data_userid.length<25) {
				$('#tip-add-userid').hide()						
			} else {
				$('#tip-add-userid').show()
				flagVali=false;
			}

			var data_repass = $.trim($("#add-repass").val())
			if (data_repass.length<26 && data_repass.length>0) {
				if($.trim($("#add-repass").val())!=$.trim($("#add-pass").val())){
					$('#tip-add-repass').text("两次密码不一致").show()
					flagVali=false;
				}else{
					$('#tip-add-repass').hide()
				}										
			} else {
				$('#tip-add-repass').text("密码为1到25个字符").show()
				flagVali=false;
			}
			if(flagVali){
				$.ajax({
					url : 'admin/addAdmin',
					async : false,
					type : 'post',
					data : $("#form-add").serialize(),		
					success : function(all) {			
						if("success"==all.message){	
							alert("添加成功")
							//quitAddAdmin()
							connectServer()
						}else{
							alert(all.message)
						}
						
					}/*,
					complete:function(){
						$("#mask").hide()
					}*/
				})
			}
	}
	function updateAdmin() {
		var flagVali= true
		var data_name = $.trim($('#update-name').val())
		if (data_name.length<7 && data_name.length>1) {
			$('#tip-update-name').hide()						
		} else {
			$('#tip-update-name').show()
			flagVali=false;
		}

		var data_repass = $.trim($("#update-repass").val())
		if (data_repass.length<26 && data_repass.length>0) {
			if($.trim($("#update-repass").val())!=$.trim($("#update-pass").val())){
				$('#tip-update-repass').text("两次密码不一致").show()
				flagVali=false;
			}else{
				$('#tip-update-repass').hide()
			}										
		} else {
			$('#tip-update-repass').text("密码为1到25个字符").show()
			flagVali=false;
		}
		if(flagVali){
			$.ajax({
				url : 'admin/updateAdmin',
				async : false,
				type : 'post',
				data : $("#form-update").serialize(),		
				success : function(all) {			
					if("success"==all.message){	
						alert("重置成功")
						quitUpdateAdmin()
						connectServer()
					}else{
						alert(all.message)
					}
					
				}
			})
		}
	}
	//打开增加管理员操作界面 
	function openAddAdmin() {
		$("#mask").show()
		$(".addAdmin-dowebok").show()
	}
	//关闭 增加管理员操作界面 
	function quitAddAdmin() {
		$("#form-add")[0].reset();
		$(".tip").hide();
		$("#mask").hide();
		$(".addAdmin-dowebok").hide()
	}
	//打开重置管理员操作界面 
	function openUpdateAdmin(e) {
		$("#update-id").val(data[e].id)
		$("#update-rank").val(data[e].rank)
		$("#update-name").val(data[e].name)
		$("#update-pass").val(data[e].pass)
		$("#update-repass").val(data[e].pass)
		$("#mask").show()
		$("#updateAdmin-div").show()
	}
	//关闭 重置管理员操作界面 
	function quitUpdateAdmin() {
		$("#mask").hide()
		$("#updateAdmin-div").hide()
		$(".mytip").hide();
	}
	function searchBy(){
		connectServer()
	}
	function clearInput(){
		$("#search-form")[0].reset()
	}