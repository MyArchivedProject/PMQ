package pmq.service;

import java.util.List;

import pmq.pojo.Paper;
import pmq.pojo.Tester;
import pmq.pojo.ToTesterEva;

public interface ITestService {

	//获取试卷//一个list存试卷的所有信息，一个list存只让测试者知道的试卷信息
	public List<List<Paper>> getPaper(String post);
	
	//提交试卷//返回测试者所获得的等级评价内容
	public List<ToTesterEva> submitPaper(List<Paper> papers,List<Object> answers,Tester tester);
	
	//获取所有的职位名字
	public List<String> getAllPost();
	
}
