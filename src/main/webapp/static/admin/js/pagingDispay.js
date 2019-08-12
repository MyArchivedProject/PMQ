//把各个内容页面都需要用到的分页方法抽象到此pagingDispay.js里
/* 必须含有以下变量
 var nums = 0;//数据总量
 var num = 0;//截至到当前页面的数据总量  
 var totalPage = 0;//总页数
 var data;//从服务端获取的数据
 var i = 0;//记录当前页的第一个值下标data[i]
 var currentPage = 0;//记录当前页码//初始化时变为1
 var totalPage = 0;//共多少页
 */
// 跳到第一页
function jumpStart() {

	if (data.length > (10)) {
		num = 10
	} else {
		num = data.length
	}
	currentPage = 1;
	setPageContent();
}
// 下一页
function jumpNext() {
	if (currentPage < totalPage) {
		if (data.length > (num + 10)) {
			num = num + 10
		} else {
			num = data.length
		}
		currentPage++;
		setPageContent();
	}
}
// 上一页
function jumpPre() {
	if (currentPage > 1) {
		currentPage--;
		num=currentPage*10
		setPageContent();
	}
}
// 跳到最后一页
function jumpEnd() {
	num = nums;
	currentPage = totalPage;
	setPageContent();
}