package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import pmq.dao.QuestionMapper;
import pmq.dao.TargetMapper;
import pmq.dao.TesterMapper;
import pmq.pojo.Admin;
import pmq.pojo.Paper;
import pmq.pojo.Question;
import pmq.pojo.Target;
import pmq.pojo.Tester;
import pmq.service.IPaperService;
import pmq.service.ITestService;

public class test_Tester extends BaseTest{

	@Resource(name="questionMapper")
	private QuestionMapper qDAO;
	@Resource(name="targetMapper")
	private TargetMapper targetDAO;
	
	@Resource(name="testService")
	private ITestService testService;
	@Resource(name="testerMapper")
	private TesterMapper testerDAO;
	@Resource(name="tester")
	private Tester tester;
	
	//@Test
	public void test(){ 
		Tester tester=new Tester();
		tester.setName("1");
		tester.setEmail("1");
		tester.setPost("1");
		tester.setTeacher("1");
		tester.setTele("1");
		tester.setTotalObj(0);
		tester.setTotalSub(0);
		tester.setTotal(0);		
		System.out.println(testerDAO.insert(tester));
		System.out.println(tester.getId());
		System.out.println(this.tester.getId());
		
		//System.out.println(testerDAO.selectAllBy(tester));
		
	}
	
	//@Test
	public void testAdmin(){
		Admin a1=new Admin();  
		Admin a2=new Admin(); 
		a1.setUser("aaa");
		a2.setUser("bbb");
		List<Admin>all=new ArrayList<Admin>();
		all.add(a1);
		all.add(a2);
		all.get(0).setUser("qqq");
		for(int i=0;i<all.size();i++){
			System.out.println(all.get(i).getUser());
		}
	}
	//@Test
	public void testPaper(){
		/*Iterator<Paper> iter=testService.getPaper("项目经理").iterator();
		while(iter.hasNext()){
			System.out.println(iter.next().getQuestion());
		}*/
		List<List<Paper>>list=testService.getPaper("项目经理");
		//System.out.println(list.get(1).getQuestion());
		
		
		//List<Target>allTarget=targetDAO.selectByPost("项目经理");
	}
}
