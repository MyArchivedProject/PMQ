	var scores;
	var data;//保存试卷
	 $(function(){
		//获取试卷的所有主观题
		$.ajax({
			url : 'paper/getUndoPaper',
			async : false,
			type : 'get',			
			success : function(all) {
				data=all.all
			}
		})
		for(var i=0;i<data.length;i++){
			var div=$("<div name='"+i+"' class='quesDiv'></div>")
			div.append("<span>" + (i + 1) +"."+ "</span><br>");
			div.append("<span>" + "题目：" + "</span><br>");
			div.append("<p>" +"&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].question + "</p>");
			div.append("<span>" + "答案：" + "</span><br>");
			/* if(data[i].answer==null){ 
				data[i].answer=""
			} */
			div.append("<p>" +"&nbsp;&nbsp;&nbsp;&nbsp;"+ data[i].answer + "</p>");		
			div.append("<span>" + "得分：" + "</span><br>");
			div.append($("<label>"+data[i].optionA+"<input type='radio' name='"+i+"' value='"+data[i].scoreA+"'></label>"))
			div.append($("<label>"+data[i].optionB+"<input type='radio' name='"+i+"' value='"+data[i].scoreB+"'></label>"))
			div.append($("<label>"+data[i].optionC+"<input type='radio' name='"+i+"' value='"+data[i].scoreC+"'> </label>"))
			div.append($("<label>"+data[i].optionD+"<input type='radio' name='"+i+"' value='"+data[i].scoreD+"'></label>"))
			if(""!=data[i].optionD){
				div.append($("<label>"+data[i].optionE+"<input type='radio' name='"+i+"' value='"+data[i].scoreE+"'></label>"))
			}
			if(""!=data[i].optionF){
				div.append($("<label>"+data[i].optionF+"<input type='radio' name='"+i+"' value='"+data[i].scoreF+"'></label>"))
			}
			div.append("<hr>");
			$("#content").append(div)
		}
		$("input[type='radio']").on("click",function(){
			$(".quesDiv").css("background-color","#F5F5F5")
		})    
		
	}) 
	//批改完成 提交试卷
	function submitSub(){
		 scores=new Array()
		 for(var i=0;i<data.length;i++){
			 //判断是否所有的主观题都被批改了 
			 if($('input[name='+i+']:radio:checked').val()!=undefined){				 
				 scores.push($('input[name='+i+']:radio:checked').val())
			 }else{
				 //获取相对位移对象 
				 var displacement= $("div[name='" +i+ "']").offset();   
				// $("html,body").animate({scrollTop:displacement.top - "90" + "px"}, 500);
				 $("html,body").scrollTop(displacement.top);
				 $("div[name='" +i+ "']").css("background-color","#FFF68F")
				 return 
			 }
		 }
		 connectServer()
	 }
	function returnHome(){
		var msg="返回主界面后,您所做的批改将作废,按确定将返回主界面！";
		if(confirm(msg)==true){
			window.location.href = 'head';
		}	
	}
	//连接服务器//上传答案
	function connectServer(){
		var msg="您已经完成批改,请仔细核对批改结果!\n点击确定后将无法再进行修改!"
		if(confirm(msg)==true){
			$.ajax({
				url:"paper/submitSubPaper",
				data:{ 
					answers:JSON.stringify(scores),
					testerId:data[0].testerId
					},
				async : false,
				type : 'post',
				success:function(all){
					alert(all.message)
					window.location.href = 'head'; 
				}
			})
		}
	}