//data在head.html中已经定义了
	var modeNums = allMode[0].length//保存已经遍历的题目总量 
	var i = 0 //遍历每一个模块的题目,最大值为某个模块的最大值 //当初始化界面时，调用nextQues()
	var modeI = 0//保存每一个模块的下标allmode[modeI]
	//提交试卷 
	function submitPaper(){
		$.ajax({
			url : 'submitPaper',
			async : false,
			type : 'post',
			data:{
				answers:JSON.stringify(answers)
			},
			success : function(all) {
				if(all.message=="success"){
					window.location="report"
				}else{
					alert(all.error)
				}			
			}
		})
	}
	//从测试完成界面返回
	function unFinish(){
		$("#finishQuesDiv").attr("hidden",true)
		changeNav()//更改导航栏 
	}
    //更改导航栏 
	function changeNav(){
		$(".one").removeClass("oneSelected")
		$("#"+allModeEnTitle[modeI]).addClass("oneSelected")
	}
	//下一题 
	function nextQues() {
		
		var flagA=0//是否被选中
		$("input:radio:checked").each(function(){
			flagA=1
		})		 
		if(flagA==0 && data[allMode[modeI][i]].objsub==0){
			alert("请先进行选择")
			return 
		}
		
		i++
		if (i < modeNums) {
			showQues()
		} else {
			modeI++ //模块下标加1
			nextMode()
		}
		
	}
	//进入下一个模块  //当某个模块不存在题目时,将会出现一个小的bug,不影响程序 
	function nextMode() {
		//判断所有 模块是否遍历结束 
		
		if (modeI < allMode.length ) {
			i = 0//模块又从0开始		
			modeNums = allMode[modeI].length //模块的题目总量 
			$(".modeQues").text(
					allModeTitle[modeI] + "(共" + allMode[modeI].length + "题)")
			if (modeNums == 0) {
				modeI++ //模块下标加1
				nextMode()
			} else {
				showQues()
				changeNav()//更改导航栏 
			}
			
		} else {
			//测试完成 
			$("#objQuesDiv").hide()
			$("#subQuesDiv").hide()
			$("#finishQuesDiv").attr("hidden",false)
			//更改导航栏
			$(".one").removeClass("oneSelected")
			$("#finish").addClass("oneSelected")
		}
		
	}
	//上一题 
	function preQues() {
		i--
		if (i >= 0) {
			showQues()
		} else {
			preMode()
		}
	}
	//判断是否进入上一模块
	function preMode() {
		//判断是否是在第一个模块，不是则往后退 
		if (modeI > 0) {
			modeI--
			modeNums = allMode[modeI].length //模块的题目总量  
			i = modeNums - 1

			//判断此模块题目数量是否大于0
			if (modeNums > 0) {
				//alert(modeNums)
				showQues()
				changeNav()//更改导航栏 
				//设置模块标题 
				$(".modeQues").text(
						allModeTitle[modeI] + "(共" + allMode[modeI].length
								+ "题)")
								
			} else {
				preMode()
			}
		
		} else {
			i = 0 //i恢复默认值 
			//更改导航栏
			$(".one").removeClass("oneSelected")
			$("#instruction").addClass("oneSelected")
			getInstruction()
			
		}
		

	}

	//显示题目 
	function showQues() {
		var dataI=allMode[modeI][i] //获取data[]的下标 
		var ques = data[dataI]   
		if (ques.objsub == 0) {
			$("#subQuesDiv").hide()
			$("#objQuesDiv").show()
			$("#objQues").text((i+1)+". "+ques.question)
			$("#objQptionDiv").html("")//清空原来的题目 
			$("#objQptionDiv").append(
					"<input type='radio' name='option' id='a' value='a'/> <label for='a'>A." + ques.optionA
							+ "</label><br/><br/>")
			$("#objQptionDiv").append(
					"<input type='radio' name='option' id='b' value='b' /> <label for='b'>B." + ques.optionB
							+ "</label><br/><br/>")
			$("#objQptionDiv").append(
					"<input type='radio' name='option' id='c' value='c' /> <label for='c'>C." + ques.optionC
							+ "</label><br/><br/>")
			$("#objQptionDiv").append(
					"<input type='radio' name='option' id='d' value='d' /> <label for='d'>D." + ques.optionD
							+ "</label><br/><br/>")
			if (!ques.optionE == "") {
				$("#objQptionDiv").append(
						"<input type='radio' name='option' id='e' value='e' /> <label for='e'>E." + ques.optionE
								+ "</label><br/><br/>")
			}
			if (!ques.optionF == "") {
				$("#objQptionDiv").append(
						"<input type='radio' name='option' id='f' value='f' /> <label for='f'>F." + ques.optionF
								+ "</label><br/><br/>")
			}
			//判断测试者是否已经回答过此题 
			if(typeof(answers[dataI]) != "undefined" && answers[dataI]!=null){
				$("input:radio").each(function(){
					if(answers[dataI]==$(this).val()){
						$(this).attr("checked","checked")
					}
				})
				
			}
			
			$("input:radio").change(function(){
				answers[dataI]=$(this).val()	
			})
		} else if (ques.objsub == 1) {
			$("#objQuesDiv").hide()
			$("#subQuesDiv").show()
			$("#subQues").text("")//清空原来的题目
			$("#subAnswer").val("")//清空原来的答案
			$("#subQues").text((i+1)+". "+ques.question)
			//if(typeof(answers[dataI]) != "undefined"){
				$("#subAnswer").val(answers[dataI])
			//}
			$("#subAnswer").off("change");//防止事件冒泡//即防止多次触发change事件,产生异常
			//保存答案
			$("#subAnswer").change(function(){
				answers[dataI]=$(this).val()
			})
		}
	}

	$(
			function() {
				$("#modeQues").text(
						allModeTitle[modeI] + "(共" + allMode[modeI].length
								+ "题)")
				nextMode()
			})