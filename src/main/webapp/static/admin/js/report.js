	var url = window.location.href
	var testerid = parseInt(url.substr(url.indexOf("=") + 1))// 获取测试者id
	var paper // 所有的题目
	var tester // 所有的测试者
	var me // 测试者本人
	$.ajax({
		url : 'paper/getPaper',
		async : false,
		type : 'get',
		data : {
			testerId : testerid
		},
		success : function(all) {
			if (all.paper != undefined && all.tester != undefined) {
				paper = all.paper
				tester = all.tester;
				me = all.me
			}
		}
	})
	$(function() {
		showPaper()
	})
	function showPaper() {
		var subIndex = 1
		var objIndex = 1
		$.each(paper, function(index) {
			if (paper[index].objsub == 0) {
				var p = $("<p class='pdf'>" + objIndex + " " + paper[index].question
						+ "</p>")
				objIndex++
				var div = $("<div ></div>")
				var radioA = $("<input disabled type='radio' ><span class='pdf'>A "
						+ paper[index].optionA + "</span></br>")
				var radioB = $("<input disabled type='radio' ><span class='pdf'>B "
						+ paper[index].optionB + "</span></br>")
				var radioC = $("<input disabled type='radio' ><span class='pdf'>C "
						+ paper[index].optionC + "</span></br>")
				var radioD = $("<input disabled type='radio' ><span class='pdf'>D "
						+ paper[index].optionD + "</span></br>")
				var radioE = $("<input disabled type='radio' ><span class='pdf'>E "
						+ paper[index].optionE + "</span></br>")
				var radioF = $("<input disabled type='radio' ><span class='pdf'>F "
						+ paper[index].optionF + "</span></br>")
				div.append(radioA)
				div.append(radioB)
				div.append(radioC)
				div.append(radioD)
				if (paper[index].optionE != "") {
					div.append(radioE)
					if (paper[index].optionF != "") {
						div.append(radioF)
					}
				}
				if (paper[index].answer == "a") {
					radioA.attr("checked", "checkde")
				} else if (paper[index].answer == "b") {
					radioB.attr("checked", "checkde")
				} else if (paper[index].answer == "c") {
					radioC.attr("checked", "checkde")
				} else if (paper[index].answer == "d") {
					radioD.attr("checked", "checkde")
				} else if (paper[index].answer == "e") {
					radioE.attr("checked", "checkde")
				} else if (paper[index].answer == "f") {
					radioF.attr("checked", "checkde")
				}

				var ques = $("<div class='question'></div>")
				ques.append(p)
				ques.append(div)
				$("#allObj").append(ques)
			} else {
				var p = $("<p class='pdf'>" + subIndex + " " + paper[index].question
						+ "</p>")
				var div = $("<div class='answer pdf'>" + paper[index].answer
						+ "</div>")
				var ques = $("<div class='question'></div>")
				ques.append(p)
				ques.append(div)
				$("#allSub").append(ques)
				subIndex++
			}
		})
	}
	var topOffPDF;// report距离html顶部的距离
	var pageTopPDF; // 要被减去的//进行一次分页,变得一次
	var allItemPDF;// 保存所有要打印的元素
	$(function(){
		topOffPDF=$("#report").offset().top
		allItemPDF=$(".pdf");
	})
	var options = {
        "background": '#FFFFFF'   // 如果导出的pdf为黑色背景，需要将导出的html模块内容背景 设置成白色。
	};
	function printbypage(pdf, k){
	  if(k >= $('.pdf').length)
	  {
		  pdf.save("aa.pdf")
		  return; // 跳出递归
	  }  	   
	   pdf.addHTML($('.pdf')[k],20,(allItemPDF[k].offsetTop-pageTopPDF)/2, options,function(){		
		  if(k < $('.pdf').length - 1){		
			  var length=allItemPDF[k+1].offsetTop;// 距离顶部的距离
			  var lengthPlus=allItemPDF[k+1].offsetTop + allItemPDF[k+1].offsetHeight;// 距离顶部的距离+自身的宽度(width+padding)
			  var page=parseInt((length-topOffPDF)/1683)+1
			  // console.log(length+"==="+lengthPlus+"==="+(1683*page+topOffPDF))
				if(length < (1683*page+topOffPDF) && lengthPlus>(1683*page+topOffPDF)){
					pdf.addPage();
					pageTopPDF=length 
				}
		   }    
		    printbypage(pdf, k + 1);
	  });
	};
	
	function downloadPDF(){
		
		// //9pt=12px。 方向默认竖直，尺寸ponits，格式a4[592.28,841.89]
      var pdf = new jsPDF('', 'pt', 'a4');
      pageTopPDF=topOffPDF
       pdf.internal.scaleFactor = 2;  // 缩小2倍
       printbypage(pdf, 0);    
	}
	
	
	
	// 生成图表

	// 指定图标的配置和数据
	var graficTotal = [] // 总分//保存每一个测试者测试所得的总分
	
	var indexFScore=[0,0,0,0,0] // 保存每一个一级指标的得分 （所有测试者的平均）
	var indexFTotalScore=[0,0,0,0,0] // 保存每一个一级指标的得分 （所有测试者的累加和）
	var indexFAveScore=[0,0,0,0,0] // 保存每一个一级指标的得分 （所有测试者的平均值）
	var indexFPaperTotalScore=[0,0,0,0,0] // 保存每一个一级指标的满分值(所有测试者的累加和)
	var indexFAveRate = [ 0, 0, 0, 0, 0 ] // 保存每一个一级指标的平均得分率（所有测试者 ）
	var ranking = [] // 排名
	// var graficTotal=[111,109,108,107,106,114,102,99,88,77,65,45,44,33,22,21]
	for(var index=0;index<tester.length;index++){
		indexFTotalScore[0] += tester[index].knowScor
		indexFTotalScore[1] += tester[index].experienceScore
		indexFTotalScore[2] += tester[index].skillScore
		indexFTotalScore[3] += tester[index].styleScore
		indexFTotalScore[4] += tester[index].valueScore
		
		indexFPaperTotalScore[0] += tester[index].knowPaper
		indexFPaperTotalScore[1] += tester[index].experiencePaper
		indexFPaperTotalScore[2] += tester[index].skillPaper
		indexFPaperTotalScore[3] += tester[index].stylePaper
		indexFPaperTotalScore[4] += tester[index].valuePaper
		// graficTotal[index]=parseInt(tester[index].total)
		graficTotal[index] = tester[index].total
		ranking[index] = index + 1
	}
	for(var i=0;i<5;i++){
		indexFAveScore[i]=indexFTotalScore[i]/tester.length
		indexFAveRate[i]=indexFTotalScore[i]/indexFPaperTotalScore[i]
	}
	// 想使用js自带的sort倒序，则要实现此方法
	function compare(a, b) {
		return b - a;
	}
	graficTotal.sort(compare)
	var meRank = graficTotal.indexOf(me.total) + 1
	var meTotal = me.total
	var totalConfig = {
		title : {
			text : "总得分排名          测试者得分: " + meTotal+",排名: " + meRank
					+ ""
		},
		tooltip : {
			trigger : 'item',
			formatter : function(data) {
				return "得分 : "+data.value + '<br/>' + data.seriesName + '：'
						+ data.name ; 
			}
		},
		legend : {
			data : [ '用户来源' ]
		},
		xAxis : {
			data : ranking
		},
		yAxis : {
			name : '总分',
			type : 'value',
			axisLabel : {
				formatter : '{value}分'
			}
		},
		series : [ {
			smooth : true,
			name : '排名',
			type : 'line',
			data : graficTotal,

		} ]
	}
	// 初始化echarts实例
	var myChart1 = echarts.init(document.getElementById('chartTotal'));
	// 使用制定的配置项和数据显示图表
	myChart1.setOption(totalConfig);
	$("#chartTotalNote").html("注:<br>1. 测试职位 : "+me.post+"。<br>2. 统计人数 : "+
			(tester.length+1)+" (人)。"+"<br>3. 试卷题目量："+paper.length+" (题)"+"<br>4. 试卷满分值 : "+me.totalPaper+" (分)。")
	
	// 一级指标得分率柱状图
	var graficPercent = [ me.knowScore / me.knowPaper,
			me.experienceScore / me.experiencePaper,
			me.skillScore / me.skillPaper, me.styleScore / me.stylePaper,
			me.valueScore / me.valuePaper ]
	// 把小数转化为百分数
	for(var index=0;index<graficPercent.length;index++){
		graficPercent[index] = (graficPercent[index] * 100).toFixed(2)
		indexFAveRate[index] = (indexFAveRate[index] * 100).toFixed(2)
		
	}
	

	var indexF = [ '专业知识', '工作经验', '工作技能', '工作风格', '工作价值观' ]
	var percentConfig = {
		title : {
			text : "一级指标得分率"
		},
		tooltip : {
			trigger : 'item',
			formatter : function(data) {
				return data.name + '<br/>' + data.seriesName + '：'
						+ data.value + '%'; // 转化为百分数显示
			}
		},
		legend : {
			width:10,
		    height:10,
		    orient:'horizontal',
		 // 使用字符串模板，模板变量为图例名称 {name}
		    formatter: '{name}',
		   // 点击图例可以显示/隐藏该图例所示图表
		    tooltip:{
		          show:true
		      }
		    
		},
		xAxis : {
			data : indexF
		},
		yAxis : {
			name : '得分率',
			max:100,
			axisLabel : {
				formatter : '{value}%'
			}
		},
		series : [ {
			name : '个人得分率',
			type : 'bar',
			data : graficPercent,
			
		} ,
		{
			name : '平均得分率',
			type : 'bar',
			data : indexFAveRate,
			itemStyle:{
                normal:{
                    color:'#00cc66'
                }
            }
		}
		]
	}
	// 初始化echarts实例
	var myChart2 = echarts.init(document.getElementById('chartPercent'));
	// 使用制定的配置项和数据显示图表
	myChart2.setOption(percentConfig);
	
	//测试者的一级指标得分情况
	var personIndexFScore=[ me.knowScore ,
	            			me.experienceScore ,
	            			me.skillScore ,
	            			me.styleScore ,
	            			me.valueScore ]
	var indexFScoreConfig = {
			title : {
				text : "一级指标得分"
			},
			// -------------- 提示框 -----------------
            tooltip: {
                show:true,                  // ---是否显示提示框,默认为true
                trigger:'item',             // ---数据项图形触发
                axisPointer:{               // ---指示样式
                    type:'shadow',      
                    axis:'auto',    

                },
            }, 
			legend : {
				width:10,
			    height:10,
			    orient:'horizontal',
			    formatter: '{name}',
			   // 点击图例可以显示/隐藏该图例所示图表
			    tooltip:{
			          show:true
			      }
			},
			xAxis : {
				data : indexF
			},
			yAxis : {
				name : '得分',
				max:tester.totalPaper,
				axisLabel : {
					formatter : '{value}'
				}
			},
			series : [ {
				name : '个人得分',
				type : 'bar',
				data : personIndexFScore,
				
			} ,
			{
				name : '平均得分',
				type : 'bar',
				data : indexFAveScore,
				itemStyle:{
	                normal:{
	                    color:'#00cc66'
	                }
	            }
			}
			]
		}
	// 初始化echarts实例
	var myChart2 = echarts.init(document.getElementById('chartIndexF'));
	// 使用制定的配置项和数据显示图表
	myChart2.setOption(indexFScoreConfig);