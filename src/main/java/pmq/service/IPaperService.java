package pmq.service;

import java.util.List;

import pmq.pojo.Paper;
import pmq.pojo.Tester;

public interface IPaperService {
	//通过id查询测试者职位，通过测试职位获取所有已经批改过的试卷
	public List<Tester> getAllTester(Integer id);
	//条件查询，获取所有测试者
	public List<Tester> getAllTester(Tester tester); 
	
	//删除测试者
	public String deletePaper(int testerId);
	
	//查看测试者的试卷详情
	public List<Paper> getPaper(int testerId);
	
	//通过测试者id查询所有主观题未被批改的题目//批改主观题时用到
	public List<Paper> getUndoPaper(int testerId);
	
	//放弃此方法//提交主观题批改结果public String submitSub(Map<Integer,Integer>allScore,int testerId);
	//提交主观题批改结果
	public String submitSub(List<String> scores,int testerId, String teacher);
}
 