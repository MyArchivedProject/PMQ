var nums = 0;//数据总量
	var num = 0;//截至到当前页面的数据总量  
	var totalPage = 0;//总页数
	var data;//从服务端获取的数据
	var i = 0;//记录当前页的第一个值下标data[i]
	var currentPage = 0;//记录当前页码
	var totalPage = 0;//共多少页

	//同步操作从服务端获取等级评价
	function connectServer() {
		$.ajax({
			url : 'eva/getEva',
			async : false,
			type : 'post',
			data : {
				post : $("#by-post").val().trim(),
				flag: $("#by-flag").val(),
				title : $("#by-title").val().trim()
			},
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
	function setEvaTable() {
		var tr = $("<tr ></tr>")
		tr.append("<td>" + (i + 1) + "</td>")
		tr.append("<td>" + data[i].post + "</td>")
		tr.append("<td>" + data[i].title + "</td>")
		//tr.append("<td>" + message.slice(1,message.length) + "</td>")
		if(1==data[i].flag){
			tr.append("<td>" + "活动 "+ "</td>")
		}else{
			tr.append("<td>" + "暂停" + "</td>")
		}
		tr.append("<td>"+ "<button  class='opt-but a' onClick='openUpadteEva("+i+")'>详情</button>"
				+"<button  class='opt-but b' onClick='deleteEva("+i+")'>删除</button>"
						+ "</td>")
		tr.appendTo($("tbody"))
	}
	//分页显示给客户端//设置当前页码//此方法名每个页面都必须相同 
	function setPageContent() {
		$("tbody").html("");
		for ( i = (currentPage - 1) * 10; i < num; i++) {
			setEvaTable();
		}
		$("#spanCurrentPage").text(currentPage)
	}
	
	function updateEva(){
		
	}
	//关闭更新自定义等级评价的界面
	function quitUpadteEva(){
		$("#mask").hide()
		$(".updateEva-dowebok").hide()
	}
	//传递职位名称进来//同步从服务器获取此职位对应的所有二级职位指标 
	var retuanIndexS
	function getAllIndexS(e){
		$.ajax({
			url : 'eva/getIndexS',
			async : false,
			type : 'post',
			data : {
				post : e
			},
			success : function(all) {
				retuanIndexS=all
			}
		})
	}
	//打开等级评价详情界面,查看等级评价的详情 
	function openUpadteEva(e){
		$("#mask").show()
		$(".updateEva-dowebok").show()
		// 获取此职位对应的所有二级职位指标 
		getAllIndexS(data[e].post);
		var indexS=retuanIndexS
		
		$("#update-id").val(data[e].id)
		$("#update-post").val(data[e].post)
		$("#update-title").val(data[e].title)
		$("#update-evaA").val(data[e].evaA)
		$("#update-miniA").val(data[e].miniA)
		$("#update-evaB").val(data[e].evaB)
		$("#update-miniB").val(data[e].miniB)
		$("#update-evaC").val(data[e].evaC)
		$("#update-miniC").val(data[e].miniC)
		$("#update-evaD").val(data[e].evaD)
		$("#update-miniD").val(data[e].miniD)
		$("#update-evaE").val(data[e].evaE)
		$("#update-miniE").val(data[e].miniE)
		$("#update-evaF").val(data[e].evaF)
		$("#update-miniF").val(data[e].miniF)
		$("#update-flag").val(data[e].flag)
		//生成二级指标信息 
		$("#update-indexS").html("");
		var temp=data[e].multiIndex.split(";");//获取此等级评价的二级指标信息
		for(var j=0;j<indexS.length;j++){//遍历此 职位 的所有二级指标 		
			for(var j=0;j<indexS.length;j++){//遍历此 职位 的所有二级指标 		
				var trr
				//每间隔4条数据添加一行 
				if(j%4==0 ){
					trr=$("<ul class='ul-tr'><ul>") //j检验bug暂时无法解决
				}
				//判断是否是被选中的
				var flag=true;
				for(var k=0;k<temp.length;k++){
					if($.trim(temp[k])==$.trim(indexS[j].id)){
						trr.append($("<li class='li-td'><label>"+indexS[j].indexS+"<input type='checkbox'  checked value='"+indexS[j].id+"'></label></li>"))
						flag=false
					}
				}
				if(flag){
					trr.append($("<li class='li-td'><label>"+indexS[j].indexS+"<input type='checkbox'  value='"+indexS[j].id+"'></label></li>"))

				}
				//添加进div里 
				if((j+1)%4==0  || j==(indexS.length-1)){
					$("#update-indexS").append(trr) 
				}	
			}
		}
		
	}
	//打开增加 等级评价界面
	function openAddEva(){		
		$("#mask").show()
		$("#form-add")[0].reset()
		var post
		//获取所有的职位 
		$.ajax({
			url : 'target/getPost',
			async : false,
			type : 'get',
			success : function(all) {
				post=all
			}
		})	
		//添加所有的职位进下拉框 
		if(post!=undefined){
			$.each(post,function(index){
				$("#add-post").append("<option value='"+post[index].post+"'>"+post[index].post+"</option>")
			})
		}
		//获 取此职位对应的所有二级职位指标 
		getAllIndexS($("#add-post option:selected").val());		
		var indexS=retuanIndexS
		generateindexS(indexS)
		$(".addEva-dowebok").show()
	}
	//生成二级指标的复选框//给增加二级指标界面使用的 	
	function generateindexS(){
		var indexS=retuanIndexS
		if(indexS!=undefined){
			$("#add-indexS").html("")//先清空原有的所有二级指标
			for(var j=0;j<indexS.length;j++){//遍历此 职位 的所有二级指标 		
				var trr
				//每间隔4条数据添加一行 
				if(j%4==0 ){
					trr=$("<ul class='ul-tr'><ul>") //j检验bug暂时无法解决
				}
				trr.append($("<li class='li-td'><label>"+indexS[j].indexS+"<input type='checkbox' name='indexS'  value='"+indexS[j].id +"'></label></li>"))	
				//添加进div里 
				if((j+1)%4==0  || j==(indexS.length-1)){
					$("#add-indexS").append(trr) 
				}	
			}
		}
	}
	//在增加等级评价界面,如果职位发生变化则调用此方法
	function fromPostToIndexS(e){	
		 getAllIndexS($("#add-post option:selected").val())
		generateindexS() 
	}
	//关闭 增加 等级评价界面
	function quitAddEva(){
		$("#mask").hide()
		$(".addEva-dowebok").hide()
	}
	//增加 等级评价 
	function addEva(){
		//"/[\s\n]/"
		/* $("#add-evaA").val($("#add-evaA").val().replace("\n","\\n"))
		$("#add-evaB").val($("#add-evaB").val().replace("\n","\\n"))
		$("#add-evaC").val($("#add-evaC").val().replace("\n","\\n"))
		$("#add-evaD").val($("#add-evaD").val().replace("\n","\\n"))
		$("#add-evaE").val($("#add-evaE").val().replace("\n","\\n"))
		$("#add-evaF").val($("#add-evaF").val().replace("\n","\\n")) */
		/* var inputEva=""
		$.each(evaA,function(index){
			inputEva=inputEva+evaA[index]+"\\n"
		})
		alert(inputEva.split("\\n")[0])
		alert(inputEva.split("\\n")[1]) */
		if($("#add-title").val().trim()==""){
			alert("等级评价名称不能为空")
			return 
		}
		if($("#add-miniA").val()==""  ){
			$("#add-miniA").val(0)
		}
		if($("#add-miniB").val()==""  ){
			$("#add-miniB").val(0)
		}
		if($("#add-miniC").val()==""  ){
			$("#add-miniC").val(0)
		}
		if($("#add-miniD").val()==""  ){
			$("#add-miniD").val(0)
		}
		if($("#add-miniE").val()==""  ){
			$("#add-miniE").val(0)
		}
		if($("#add-miniF").val()==""  ){
			$("#add-miniF").val(0)
		}
		var formdata = new FormData($("#form-add")[0]);
		var multiIndex=""
		$("#add-indexS input[type=checkbox]:checked").each(function(index){
			multiIndex=multiIndex+$(this).val()
			//alert($(this).val())
			if($("input[type=checkbox]:checked").length!=(index+1)){
				multiIndex=multiIndex+";"
			}
		})
		formdata.append("multiIndex",multiIndex)
	    $.ajax({
			url : 'eva/addEva',
			async : false,
			type : 'post',
			data:formdata,
			processData: false,  // 告诉jQuery不要去处理发送的数据
	        contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
			
			success : function(all) {
				if(all.message=="success"){
					alert("添加成功")
					quitAddEva()
					connectServer();
					$("#add-title").val("")//清空输入框 
				}else{
					alert(all.message)
				}					
			}
		})	
	}
	//更新  等级评价 
	function updateEva(){
		if($("#add-miniA").val()==""  ){
			$("#add-miniA").val(0)
		}
		if($("#add-miniB").val()==""  ){
			$("#add-miniB").val(0)
		}
		if($("#add-miniC").val()==""  ){
			$("#add-miniC").val(0)
		}
		if($("#add-miniD").val()==""  ){
			$("#add-miniD").val(0)
		}
		if($("#add-miniE").val()==""  ){
			$("#add-miniE").val(0)
		}
		if($("#add-miniF").val()==""  ){
			$("#add-miniF").val(0)
		}
		var formdata = new FormData($("#form-update")[0]);		
		//alert($("#update-indexId"))
		//alert($("#update-indexId").val())
		//如果不是自定义等级评价则不需要加multiIndex
		if($("#update-indexId").val()==undefined){
			var multiIndex=$("#update-title").val()
			$("#update-indexS input[type=checkbox]:checked").each(function(){
				multiIndex=multiIndex+";"+$(this).val()
			})
			formdata.append("multiIndex",multiIndex)
		}
	    $.ajax({
			url : 'eva/updateEva',
			async : false,
			type : 'post',
			data:formdata,
			processData: false,  // 告诉jQuery不要去处理发送的数据
	        contentType: false,  // 告诉jQuery不要去设置Content-Type请求头
			
			success : function(all) {
				if(all.message=="success"){
					alert("更新等级评价成功 ")
					quitUpadteEva();
					connectServer()
				}else{
					alert(all.message)
				}			
			}
		})	
	}
	//删除等级评价 
	function deleteEva(e){
		//alert(e)
		//alert(data[e].id)
		var msg = "您确定要删除的这条数据？"+"\n被删除者:"+data[e].post;
		if (confirm(msg)==true){
			$.ajax({
				url : 'eva/deleteEva',
				async : false,
				type : 'get',
				data:{
					evaId:data[e].id
				},
				success : function(all) {
					if(all.message=="success"){
						alert("删除成功");
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
	//进行条件查询 
	function selectBy(){	
		connectServer()
	}
	function clearInput(){
		/* ("#by-post").val("")
		("#by-flag").val("") */
		$("#select-form")[0].reset()
		post=""
		flag=2
	}