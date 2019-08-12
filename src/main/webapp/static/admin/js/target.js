var nums = 0;//数据总量
	var num = 0;//截至到当前页面的数据总量  
	var totalPage = 0;//总页数
	var data;//从服务端获取的数据
	var i = 0;//记录当前页的第一个值下标data[i]
	var currentPage = 0;//记录当前页码
	var totalPage = 0;//共多少页

	var post = ""//通过职位进行查询 
	//同步操作从服务端获取所有的二级指标
	function connectServer() {
		$.ajax({
			url : 'target/getTarget',
			async : false,
			type : 'post',
			data :$("#search-form").serialize(),
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
				$("#spanTotalPage").text(totalPage)
				jumpStart()
			}
		})
	}
	//创建表格
	function setAdminTable() {
		var tr = $("<tr></tr>");
		tr.append("<td>" + (i + 1) + "</td>")

		tr.append("<td>" + data[i].post + "</td>")
		tr.append("<td>" + data[i].indexF + "</td>")
		tr.append("<td>" + data[i].indexS + "</td>")
		//tr.append("<td>" + data[i].totalObj + "</td>")
		//tr.append("<td>" + data[i].totalSub + "</td>")
		tr.append("<td>" + data[i].numObj + "</td>")
		tr.append("<td>" + data[i].numSub + "</td>")

		tr
				.append("<td>"
						+ "<button class='opt-but a' onClick='openUpdateIndex("+i+")'>修改</button>"
						+"<button class='opt-but b' onClick='deleteIndex("+i+")'>删除</button>"
						+ "</td>")
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
	//打开增加二级指标操作界面
	function openAddIndex (){
		$("#mask").show()
		$(".addIndex-dowebok").show()
		$.ajax({
			url:"target/getPost",
			type:"post",
			async:false,
			success:function(all){
				$("#addIndex-post").html("")		
				$.each(all,function(index){
					$("#addIndex-post").append("<option value='"+all[index].post+"'>"+all[index].post+"</option>")
				})								
			}
		})
		
	}
	//关闭 增加二级指标操作 界面
	function quitAddIndex (){
		$(".addIndex-dowebok").hide()
		$("#mask").hide()
	}
	//打开 更新 二级指标操作 界面
	function openUpdateIndex (e){	
		$("#dbSubNum").text(data[e].allnumObj)
		$("#dbObjNum").text(data[e].allnumSub)
		
		$("#update-id").val(data[e].id)
		$("#update-post").val(data[e].post)
		$("#update-indexF").val(data[e].indexF)
		$("#update-indexS").val(data[e].indexS)
		$("#update-numObj").val(data[e].numObj)
		$("#update-numSub").val(data[e].numSub)
		$("#mask").show()
		$(".updateIndex-dowebok").show()
	}
	//关闭  更新 二级指标操作 界面
	function quitUpdateIndex (){
		$(".updateIndex-dowebok").hide()
		$("#mask").hide()
	}
	//增加自定义二级指标操作
	function addIndex(){
		if($("#add-indexS").val().length>0 &&$("#add-indexS").val().length>0<26){
			$.ajax({
				url:"target/addIndex",
				type:"post",
				data:$("#form-add").serialize(),
				async:false,
				success:function(all){
					if(all.message=="success"){
						alert("添加成功")
						//quitAddIndex ()
						connectServer()
					}else{
						alert(all.message)
					}				
				}
			})
		}else{
			alert("二级指标为1至25个字符")
		}
		
	}
	//更新自二级指标操作 
	function updateIndex(){
		$.ajax({
			url:"target/updateNum",
			type:"post",
			data:$("#form-update").serialize(),
			async:false,
			success:function(all){
				if(all.message=="success"){
					alert("更新成功 ")
					connectServer()
				}else{
					alert(all.message)
				}	
			}
		})
	}
	//删除职位指标操作
	function deleteIndex(e){
		var msg="删除此职位指标将会清空此职位指标下的所有题目和相对应的等级评价信息\n被删除的职位指标为:"
		+data[e].post+"-"+data[e].indexF+"-"+data[e].indexS
		if(confirm(msg)==true){
			$.ajax({
				url:"target/deleteTarget",
				type:"get",
				data:{
					targetId:data[e].id
				},
				async:false,
				success:function(all){
					if(all.message="success"){
						alert("删除成功")
						connectServer()
					}else{
						alert(all.message)
					}
				}
			})
		}	
	}
	$(function() {
		connectServer()
	})
	function searchBy(){
		connectServer()
	}
	function clearInput(){
		$("#search-form")[0].reset()
	}