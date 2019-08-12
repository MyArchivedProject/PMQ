//每次从服务器获取数据后,则更新所有变量
	var totalPage=0;//共多少页
	var currentPage=0;//当前在第几页
	var data;//从服务端获取的数据
	var nums = 0;//数据总量
	var num = 0;//截至到当前页面的数据总量  
	var i=0;//当前页面第一条数据的data[]下标 
	
	var objsub = 2;//判断用户的条件查询//主观题,客观题,所有,//默认值为所有
	var flag = 2;//判断用户的条件查询//是,否,所有,//默认值为所有
	var post = "";//职位
	var indexF = "";//一级指标
	var indexS = "";//二级指标	
	
	
	var errors=new Array();//保存 导入excel产生的错误信息 
	//根据i的值进行分页
	function setQuesTable() {
		//动态创建一个tr行标签,并且转换成jQuery对象
		var tr = $("<tr></tr>");
		//往行里面追加 td单元格//value删除时需要用到//index修改某一题目时需要用到
		tr
				.append("<input class='tick' name='tick' index='"+i+"'  type='checkbox' value='"+data[i].id+"'>"
						+ "</input>");
		tr.append("<td>" + (i + 1) + "</td>");
		tr.append("<td>" + data[i].post + "</td>");
		tr.append("<td>" + data[i].indexF + "</td>");
		tr.append("<td>" + data[i].indexS + "</td>");
		tr.append("<td>" + data[i].question + "</td>");
		tr.append("<td>" + data[i].optionA + "</td>");
		tr.append("<td>" + data[i].scoreA + "</td>");
		tr.append("<td>" + data[i].optionB + "</td>");
		tr.append("<td>" + data[i].scoreB + "</td>");
		tr.append("<td>" + data[i].optionC + "</td>");
		tr.append("<td>" + data[i].scoreC + "</td>");
		tr.append("<td>" + data[i].optionD + "</td>");
		tr.append("<td>" + data[i].scoreD + "</td>");
		tr.append("<td>" + data[i].optionE + "</td>");
		tr.append("<td>" + data[i].scoreE + "</td>");
		//tr.append("<td>" + data[i].optionF + "</td>");
		//tr.append("<td>" + data[i].scoreF + "</td>");
		//0替换为选择题//1替换为主观题
		if (data[i].objsub == 0) {
			tr.append("<td>" + "客观题" + "</td>");
		} else if (data[i].objsub == 1) {
			tr.append("<td>" + "主观题" + "</td>");
		}
		if (data[i].flag == 0) {
			tr.append("<td>" + "否" + "</td>");
		} else if (data[i].flag == 1) {
			tr.append("<td>" + "是" + "</td>");
		}
		tr.appendTo("tbody");
	}
	//分页显示给客户端//设置当前页码//此方法名每个页面都必须相同 
	function setPageContent() {
		$("tbody").html("");
		for ( i = (currentPage - 1) * 10; i < num; i++) {
			setQuesTable();
			$("table  tr:nth-child(even)").css("background-color","#DFFFDF");  
		}
		$("#spanCurrentPage").text(currentPage)
	}
	//同步操作从服务端获取题库 
	function connectServer() {
		$.ajax({
			url : "ques/getQues",
			async : false,
			type : 'post',

			dataType : "json",
			data : {
				"post" : post,
				"indexF" : indexF,
				"indexS" : indexS,
				"objsub" : objsub,
				"flag" : flag
			},
			success : function(all) {
				//if(typeof(data)=="undefined"){
				/*if(nums==0){
					console.log("a")
					var head = document.getElementsByTagName('head')[0];
			        var link = document.createElement('link');
			        link.type='text/css';
			        link.rel = 'stylesheet';
			        link.href = "/PMQ/static/admin/operateCSS/quesImport.css";
			        head.appendChild(link);
				}*/
				data = all
				//设置数据总量
				nums = data.length
				//设置总页数
				if(nums%10==0){
					totalPage = parseInt(nums / 10)
				}else{
					totalPage = parseInt(nums / 10) + 1
				}				
				$("#spanTotalPage").text(totalPage)
				jumpStart();
			}	
		})
	}
	//查询题目按钮
	function searchQues() {
		post = $("#input-post").val()
		indexF = $("#input-indexF").val()
		indexS = $("#input-indexS").val()
		objsub = $("#select-objsub").val()
		flag = $("#select-flag").val()
		connectServer();
	}
	// 下载 模版 按钮 
	function exportPattern() {
		var a = document.createElement('a');
		var url = '/PMQ/static/admin/excels/pattern.xls';
		// var filename = 'evaluation.xls';
		a.href = url;
		//a.download = filename;
		a.click()
	}
	//打开导入excel操作界面
	function openImportQues() {
		$("#mask").show()
		$("#import-dialog").show()
	}
	//关闭导入excel操作界面
	function quitImportQues() {
		$("#mask").hide()
		$("#import-dialog").hide()
	}
     //执行导入操作
	function importQues(){
    	//var temp=document.getElementById("uploadFile");
		var formData = new FormData($("#uploadFile")[0]);//生成FormData对象 
		if($("#file").val()==""){
			alert("请先选择文件!")
			return;
		}
	 	 $.ajax({
			async : false,
			url:'ques/addQues',
			type:'post',
			cache: false,
			processData:false,  // 告诉jQuery不要去处理发送的数据
			contentType:false,   // 告诉jQuery不要去设置Content-Type请求头
			data:formData,
			success:function(all){
				/* let m=message.message
				let yesno=m.indexOf("success")
				if(yesno==0){
					connectServer();
				}
				alert(m) */
				if(all.success!=undefined){
					alert(all.success)
					quitImportQues()
					connectServer();
				}else if(all.message!=undefined){
					alert(all.message)
				}else{
					errors=all.errors;
					window.open("/PMQ/errors.html")				
				}
			},
			error:function(){
				alert("网络异常,请刷新!")
			}
		})  
	} 
	//通过链接触发文件选择按钮 
	function importFile(){
		$("#file").click()
	}
	//所有输入框清空
	function clearInput() {
		$("#input-post").val("")
		$("#input-indexF").val('')
		$("#input-indexS").val('')
		$("#select-objsub").val('2')
		$("#select-flag").val('2')
	}
	//删除题目
	function deleteQues() {
		//获取所有被删除的题目id
		var array=[]
		$('input:checkbox[name=tick]:checked').each(function() {
			array.push($(this).val().trim())
			//alert($(this).val().trim())
		})
		if(array.length>0){
			var msg = "您确定要删除这"+array.length+"条数据";
			if (confirm(msg)==true){
				$.ajax({
					url : 'ques/deleteQues',
					async : false,
					type : 'post',
					dataType : "json",
					data : {"data":JSON.stringify(array)},
					success : function(message) {
						alert(message.message);
						connectServer()//删除成功则刷新页面 
					},
					error:function(){
						alert("异常,请刷新页面 ")
					}
				})
			}
		}	
	}
	//选中所有
	var selectA=true;
	function selectAll() {
		$('input:checkbox[name=tick]').prop("checked", selectA)
		if(selectA){
			selectA=false;
		}else{
			selectA=true;
		}
	}	
	//初始化
	$(function() {
		connectServer();
		//$("#update-dialog").load("quesUpdate.html");我把quesUpdate.html的代码直接放进ques.html里了
	})
	
	
	
//更新题目界面的js代码
		var updateIndex = null; //保存需要修改的题目，在data[]中的位置
	//打开更新题目的界面 
	function openQuesUpdateDialog() {
		var array = new Array();
		$('input:checkbox[name=tick]:checked').each(function(i) {
			array.push($(this).attr("index"))
		})
		updateIndex = array.shift()
		if (typeof (updateIndex) != "undefined") {
			updateDetail()
			$("#mask").show()
			$("#update-dialog").show()
		} else {
			alert("请先选择一道题目")
		}
	}
	//openQuesUpdateDialog()调用此方法，给输入框赋值 
	function updateDetail() {
		$("#id").val(data[updateIndex].id);
		$("#question").val(data[updateIndex].question);
		$("#optionA").val(data[updateIndex].optionA);
		$("#scoreA").val(data[updateIndex].scoreA);
		$("#optionB").val(data[updateIndex].optionB);
		$("#scoreB").val(data[updateIndex].scoreB);
		$("#optionC").val(data[updateIndex].optionC);
		$("#scoreC").val(data[updateIndex].scoreC);
		$("#optionD").val(data[updateIndex].optionD);
		$("#scoreD").val(data[updateIndex].scoreD);
		$("#optionE").val(data[updateIndex].optionE);
		$("#scoreE").val(data[updateIndex].scoreE);
		$("#optionF").val(data[updateIndex].optionF);
		$("#scoreF").val(data[updateIndex].scoreF);
		$("#objsub").val(data[updateIndex].objsub);
		$("#flag").val(data[updateIndex].flag);
	}

	//取消更新操作 
	function updateCancel() {
		$("#mask").hide()
		$("#update-dialog").hide()
	}
	//确定更新，提交数据
	function submit() {
		$.ajax({			
			url : 'ques/updateQues',
			async : false,
			type : 'post',
			data : $("#form-update").serialize(),
			success : function(all) {
				if(all.message!=undefined && all.message!="success"){
					alert(all.message)
				}else if(all.message=="success"){
					//修改成功暂时不需要从数据库重新获取数据,直接修改本地的data[]变量,重新显示表格
					data[updateIndex].id=$("#id").val();
					data[updateIndex].question=$("#question").val();
					data[updateIndex].optionA=$("#optionA").val();
					data[updateIndex].scoreA=$("#scoreA").val();
					data[updateIndex].optionB=$("#optionB").val();
					data[updateIndex].scoreB=$("#scoreB").val();
					data[updateIndex].optionC=$("#optionC").val();
					data[updateIndex].scoreC=$("#scoreC").val();
					data[updateIndex].optionD=$("#optionD").val();
					data[updateIndex].scoreD=$("#scoreD").val();
					data[updateIndex].optionE=$("#optionE").val();
					data[updateIndex].scoreE=$("#scoreE").val();
					data[updateIndex].optionF=$("#optionF").val();
					data[updateIndex].scoreF=$("#scoreF").val();
					data[updateIndex].objsub=$("#objsub").val();
					data[updateIndex].flag=$("#flag").val();
					alert("修改成功");
					setPageContent()
				}else{
					alert("error,请刷新页面 ")
				}	
			},
			error : function() {
	             alert("异常！请重新刷新 ");
	        }
		})
	}