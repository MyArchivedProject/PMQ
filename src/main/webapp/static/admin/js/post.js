var nums = 0;//数据总量
	var num = 0;//截至到当前页面的数据总量  
	var totalPage = 0;//总页数
	var data;//从服务端获取的数据
	var i = 0;//记录当前页的第一个值下标data[i]
	var currentPage = 0;//记录当前页码
	var totalPage = 0;//共多少页

	var post=""
	//同步操作从服务端获取题库 
	function connectServer() {
		$.ajax({
			url : 'target/getPost',
			async : false,
			type : 'post',
			data:{
				post:this.post
			},
			success : function(all) {
				data = all
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
		tr.append("<td>" + data[i].post + "</td>")
		tr.append("<td>" + data[i].indexSNum + "</td>")
		tr.append("<td>" + data[i].objNum + "</td>")
		tr.append("<td>" + data[i].subNum + "</td>")
		tr.append("<td>" + "<button class='opt-but b' onClick='deletePost("+i+")'>删除</button >" + "</td>")
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
	//打开增加测试职位操作界面
	function openAddPost(){
		$("#mask").show()
		$(".addPost-dowebok").show()
	}
	//关闭 增加测试职位操作界面
	function quitAddPost(){
		$("#mask").hide()
		$(".addPost-dowebok").hide()
		$("#tip-addPost").text(".")
	}
	//增加测试职位
	function addPost(){
		$.ajax({
			url:"target/addPost",
			data:$("#form-add").serialize(),
			async:false,
			type:"post",
			success:function(all){			
				if("添加成功"==all.message){
					connectServer()
					//$(".addPost-dowebok").hide();
					quitAddPost()
					alert(all.message)
				}else{
					$("#tip-addPost").text(all.message)
				}
			}
		})
	}
	//根据传进来的data索引获取职位名称 删除 测试职位
	function deletePost(e){		
		var msg = "职位:"+data[e].post+"\n删除此职位下将会清空此职位下的所有二级指标和题目.\n";
		if (confirm(msg)==true){
			$.ajax({
				url:"target/deletePost",
				data:{
					post:data[e].post
				},
				async:false,
				type:"post",
				success:function(all){
					alert(all.message)
					if("删除成功"==all.message){
						connectServer()
					}
				}
			}) 
		}
		
	}
	function clearInput(){
		$("#by-post").val("");
	}
	//条件查询//模糊查询
	function searchBy(){
		this.post=$("#by-post").val()
		connectServer()
	}
	$(function() {
		connectServer()
	})