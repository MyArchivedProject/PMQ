$(	
	function() {	
		if (!!window.ActiveXObject || "ActiveXObject" in window) {
					document.getElementById("download").style.display = "none"
			}
			$.ajax({
				url : 'getReport',
				async : false,
				type : 'get',
				success : function(all) {
					if (all.report != undefined
							&& all.tester != undefined) {
						var report //保存等级评价信息
						var tester //保存
						report = all.report
						tester = all.tester
						$("#name").text(tester.name)
						$("#email").text(tester.email)
						$("#tele").text(tester.tele)

						$("#know-score").text(tester.knowScore)

						$("#experience-score").text(
								tester.experienceScore)

						$("#skill-score").text(tester.skillScore)

						$("#style-score").text(tester.styleScore)

						$("#value-score").text(tester.valueScore)
						$("#total-score").text(
								tester.knowScore
										+ tester.experienceScore
										+ tester.skillScore
										+ tester.styleScore
										+ tester.valueScore)
						$.each(report,function(index) {
							//添加一条等级评价 
							var li = $("<ul class='pdf'>"
										+ (index + 1)
										+ ". "
										+ report[index].evaName
										+ "</ul>")
								var ul = $("<ul type='disc' ></ul>")
								var evaContent = report[index].evaContent.split("\n")
								$.each(evaContent,function(index) {
									//alert(evaContent[index])
									ul.append("<li>"+ evaContent[index]+ "</li>")
								})
								li.append(ul)
								$("#report-eva").append(li)
								$("#report-eva").append($("<br>"))
							})
					} else {
						alert(all.error)
					}
				}
		})
	})
	
	var topOffPD;//被打印的元素的top边距离html顶部的距离 
	var pageTopPDF; //要被减去的//进行一次分页,变一次 
	var allItemPDF;//保存所有要打印的元素 
	$(function() {
		topOffPDF = $("#report").offset().top
		allItemPDF = $(".pdf");
	})
	var options = {
		"background" : '#FFFFFF' //如果导出的pdf为黑色背景，需要将导出的html模块内容背景 设置成白色。
	};
	//k表示第几个元素 
	function printbypage(pdf, k) {
		if (k >= $('.pdf').length) {
			pdf.save($("#name").text())
			$("#download").text("下载测试结果");
			$("#download").attr('disabled', false);
			return //跳出递归 
		}
		try{
			//(html,左右边距，距离pdf顶部的距离,修饰项)
			pdf.addHTML($('.pdf')[k], 20,(allItemPDF[k].offsetTop - pageTopPDF) / 2, options,
				function() {
					if (k < $('.pdf').length - 1) {
						var length = allItemPDF[k + 1].offsetTop;// 距离顶部的距离
						var lengthPlus = allItemPDF[k + 1].offsetTop
								+ allItemPDF[k + 1].offsetHeight;//距离顶部的距离+自身的宽度(width+padding)
						var page = parseInt((length - topOffPDF) / 1683) + 1
						//console.log(length+"==="+lengthPlus+"==="+(1683*page+topOffPDF))	    	
						if (length < (1683 * page + topOffPDF)
								&& lengthPlus > (1683 * page + topOffPDF)) {
							pdf.addPage();
							pageTopPDF = length -20//-20表示距离顶部增加20的距离 
						}
					}
					printbypage(pdf, k + 1);
				});		
		 }catch(err){
			console.log(err);
			alert("游览器版本过低.\n请更新游览器后再进行尝试.\n提示:不支持使用IE游览器进行下载操作.")
		}
	};

	function downloadPDF() {
		$("#download").text("正在下载中*");
		$("#download").attr('disabled', true);
		////9pt=12px。 方向默认竖直，尺寸ponits，格式a4[592.28,841.89]
		var pdf = new jsPDF('', 'pt', 'a4');
		pageTopPDF = topOffPDF
		pdf.internal.scaleFactor = 2; //缩小2倍     
		printbypage(pdf, 0);
	}