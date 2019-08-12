package test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import pmq.dao.PaperMapper;
import pmq.pojo.Paper;
import pmq.pojo.Tester;
import pmq.service.IPaperService;

public class test_paper extends BaseTest{
	@Resource(name="paperMapper")
	private PaperMapper paperDAO;
	
	
	@Resource(name="tester")
	private Tester tester;
	@Resource(name="paperService")
	private IPaperService paperService;
	@Test
	public void testGetAll(){
		tester.setEmail("");
		tester.setName("");
		tester.setPost("");
		tester.setTele("");
		tester.setTotalObj(0);
		tester.setNumSub(0);
		tester.setTotalSub(0);
		tester.setTotal(0);
		tester.setTeacher("");
		System.out.println(paperService.getAllTester(tester));
		
	}
	//@Test
	public void testUpdate(){
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		map.put(1, 58);
		map.put(2,55);
		System.out.println(paperDAO.updateSubScore(map));
		
		List<Paper>papers=paperDAO.selectAllByTeacher(1);
		for(int i=0;i<papers.size();i++){
			//System.out.println(papers.get(i).getId());
		}
	}

}
