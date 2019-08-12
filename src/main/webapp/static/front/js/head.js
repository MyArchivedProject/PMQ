var answersDone = new Array();// 当用户刷新时使用//保存服务器返回的daan
var answers;// 保存所有的答案
var data;// 用来保存所有的题目
// 每个模块都保存所需要的data[]的索引
var information = [];// 基本信息
var know = [];// 专业知识
var experience = [];// 工作经验
var skill = [];// 工作技能
var style = [];// 工作风格
var value = [];// 工作价值观
var allMode = [ information, know, experience, skill, style, value ] //
var allModeTitle = [ "基本信息", "专业知识", "工作经验", "工作技能", "工作风格", "工作价值观" ] // 显示导航栏时需要用
var allModeEnTitle = [ "information", "know", "experience", "skill", "style",
		"value" ] // 更改导航栏时需要用
// 正式测评开始
function startTest() {
	$.ajax({
		url : 'test',
		async : false,
		type : 'get',
		success : function(all) {
			$("#content").html(all)
		}
	})
}
// 获取测试 说明界面
function getInstruction() {
	$.ajax({
		url : 'instruction',
		async : false,
		type : 'get',
		success : function(all) {
			$("#content").html(all)
		}
	})
}

// 把data[]的索引放进每个模块里
$(function() {
	$.ajax({
		url : 'getPaper',
		async : false,
		type : 'post',

		success : function(all) {
			data = all.all// 所有题目
			answers = new Array(data.length)
			if (typeof (all.answersDone) != "undefined") {
				JSON.stringify(all.answersDone).split(",");
				var temp = JSON.stringify(all.answersDone).split(",");

				// answers= (all.answersDone).parseJSON(); /此方法行不通
				// all.answersDone.parseJSON is not a function
				answers = JSON.parse(all.answersDone)
			}

			for (var i = 0; i < data.length; i++) {
				var ques = data[i]
				if (ques.flag == 1) {
					information.push(i)
				} else if (ques.indexF == "专业知识") {
					know.push(i)
				} else if (ques.indexF == "工作经验") {
					experience.push(i)
				} else if (ques.indexF == "工作技能") {
					skill.push(i)
				} else if (ques.indexF == "工作风格") {
					style.push(i)
				} else if (ques.indexF == "工作价值观") {
					value.push(i)
				}
			}
		}

	})
	// 加载测试说明界面
	getInstruction()
})
// 页面刷新则先进行答案的保存onbeforeunload="saveAnswers()"
function saveAnswers() {
	$.ajax({
		url : 'saveAnswers',
		async : false,
		data : {
			answers : JSON.stringify(answers)
		// answers:answers
		},
		type : 'post'
	})
}