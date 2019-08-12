var nums = 0;//数据总量
	var num = 0;//截至到当前页面的数据总量  
	var totalPage = 0;//总页数
	var data;//从服务端获取的数据
	var i = 0;//记录当前页的第一个值下标data[i]
	var currentPage = 0;//记录当前页码
	var totalPage = 0;//共多少页

	//同步操作从服务端获取题库 
	function connectServer() {
		$.ajax({
			url : 'paper/getAllTester',
			async : false,
			type : 'post',
			data : $("#select-form").serialize(),
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
	function setEvaTable() {
		var tr = $("<tr></tr>");

		tr.append("<td>" + (i + 1) + "</td>")
		tr.append("<td>" + data[i].name + "</td>")
		tr.append("<td>" + new Date(data[i].time).toLocaleDateString()
				+ "</td>")
		tr.append("<td>" + data[i].email + "</td>")
		tr.append("<td>" + data[i].tele + "</td>")
		/* tr.append("<td>" + data[i].totalPaper + "</td>") */
		tr.append("<td>" + data[i].post + "</td>")		
		tr.append("<td>" + data[i].totalObj + "</td>")
		tr.append("<td>" + data[i].totalSub + "</td>")
		tr.append("<td>" + data[i].total + "</td>")
		tr.append("<td>" + data[i].teacher + "</td>")
		if ($.trim(data[i].teacher) == "") {
			tr
					.append("<td>"
							+ "<button class='opt-but a' onClick='markPaper("
							+ data[i].id
							+ ")'>批改</button><button class='opt-but b' onClick='deletePaper("
							+ i + ")'>删除</button>" + "</td>")
			tr.appendTo($("tbody"))
		} else {
			tr
					.append("<td>"
							+ "<button class='opt-but a' onClick='report("
							+ data[i].id
							+ ")'>详情</button><button class='opt-but b' onClick='deletePaper("
							+ i + ")'>删除</button>" + "</td>")
			tr.appendTo($("tbody"))
		}

	}
	//分页显示给客户端//设置当前页码//此方法名每个页面都必须相同 
	function setPageContent() {
		$("tbody").html("");
		for (i = (currentPage - 1) * 10; i < num; i++) {
			setEvaTable();
		}
		$("#spanCurrentPage").text(currentPage)
	}
	$(function() {
		connectServer()
	})
	
	//删除测试者/试卷
	function deletePaper(e) {
		var msg="您确定要删除的这条数据？"+"\n被删除者姓名:"+data[e].name;
		if(confirm(msg)==true){
			$.ajax({
				url : 'paper/deletePaper',
				async : false,
				type : 'get',
				data : {
					testerId : data[e].id
				},
				success : function(all) {
					alert(all.all)
					if ("删除成功" == all.all) {
						connectServer()
					}
				}
			})
		}
	}
	function searchBy(){
		connectServer()
		/* $("#by-name").val()
		$("#by-post")
		$("#by-tele") */
	}
	//打开试卷详情界面 
	function report(e){
		window.open('report?testerId=' + e);
	}
	//获取试卷的所有主观题
	function markPaper(e) {
		window.location.href = 'mark?testerId=' + e;
	}
	//清空输入框 
	function clearInput(){
		$('#select-form')[0].reset();
	}