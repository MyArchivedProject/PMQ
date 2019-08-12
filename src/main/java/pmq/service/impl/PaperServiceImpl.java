package pmq.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pmq.dao.PaperMapper;
import pmq.dao.TesterMapper;
import pmq.pojo.Paper;
import pmq.pojo.Tester;
import pmq.service.IPaperService;

@Service(value = "paperService")
public class PaperServiceImpl implements IPaperService {
	@Resource(name = "testerMapper")
	private TesterMapper testerDAO;

	@Resource(name = "paperMapper")
	private PaperMapper paperDAO;

	@Resource(name = "tester")
	private Tester tester;
	//获取所有的已经批改的测试者信息
	public List<Tester> getAllTester(Integer id) {
		return testerDAO.selectAllMarked(id);
	}
	// 条件查询，获取所有测试者
	public List<Tester> getAllTester(Tester tester) {
		// TODO Auto-generated method stub
		// tester的字段不能为null
		if (null == tester.getTotal()) {
			tester.setTotal(0);
		}
		if (null == tester.getTotalSub()) {
			tester.setTotalSub(0);
		}
		if (null == tester.getTotalObj()) {
			tester.setTotalObj(0);
		}
		if (null == tester.getTeacher()) {
			tester.setTeacher("");
		}

		if (null == tester.getName()) {
			tester.setName("");
		}
		if (null == tester.getEmail()) {
			tester.setEmail("");
		}
		if (null == tester.getTele()) {
			tester.setTele("");
		}
		if (null == tester.getPost()) {
			tester.setPost("");
		}
		return testerDAO.selectAllBy(tester);
	}

	// 查看测试者的试卷详情
	public List<Paper> getPaper(int testerId) {
		// TODO Auto-generated method stub

		return paperDAO.selectAllByTesterId(testerId);
	}

	// 根据测试者id获取所有的主观题题目
	public List<Paper> getUndoPaper(int testerId) {
		// TODO Auto-generated method stub
		return paperDAO.selectAllByTeacher(testerId);
	}

	/*
	 * 放弃此方法//提交主观题批改结果 public String submitSub(Map<Integer, Integer>
	 * allScore,int testerId) {//参数放入主观题id和对应的得分 // TODO Auto-generated method
	 * stub
	 * 
	 * int totalSub=0; for(Integer score : allScore.values()){ totalSub+=score;
	 * } //判断更新主观题分数是否成功 int aa=paperDAO.updateSubScore(allScore); if(aa>0){
	 * System.out.println(aa);
	 * 
	 * int total=testerDAO.selectObjScoreById(testerId)+totalSub;//求总分
	 * tester.setTotalSub(totalSub); tester.setTotal(total); //插入测试者的主观题分数和总分
	 * testerDAO.updateSubScore(tester); return (totalSub+""); }else{ return
	 * "系统错误"; } }
	 */
	// 提交主观题批改结果
	public String submitSub(List<String> allScore, int testerId, String teacher) {// 参数放入主观题id和对应的得分
		// TODO Auto-generated method stub
		String message = ""; //存储返回的信息
		List<Paper> papers = paperDAO.selectAllByTeacher(testerId); //获取所有的主观题
		Map<Integer, Integer> map = new HashMap<Integer, Integer>(); //存储题目id和得分
		int totalSub = 0;
		
		int knowPaper=0;
		int experiencePaper=0;
		int skillPaper=0;
		int stylePaper=0;
		int valuePaper=0;
		
		int knowScore=0;
		int experienceScore=0;
		int skillScore=0;
		int styleScore=0;
		int valueScore=0;
		
		if (papers.size() == allScore.size()) {
			for (int i = 0; i < allScore.size(); i++) {
				System.out.println(allScore.get(i));
				int tempScore = Integer.parseInt(allScore.get(i));// 保存题目得分
				totalSub += tempScore;
				map.put(papers.get(i).getId(), tempScore);
				
				if ("专业知识".equals(papers.get(i).getIndexF())) {
					knowPaper+=papers.get(i).getTop();
					knowScore+=tempScore;
				} else if ("工作经验".equals(papers.get(i).getIndexF())) {
					experiencePaper+=papers.get(i).getTop();
					experienceScore+=tempScore;
				} else if ("工作技能".equals(papers.get(i).getIndexF())) {
					skillPaper+=papers.get(i).getTop();
					skillScore+=tempScore;
				} else if ("工作风格".equals(papers.get(i).getIndexF())) {
					stylePaper+=papers.get(i).getTop();
					styleScore+=tempScore;
				} else if ("工作价值观".equals(papers.get(i).getIndexF())) {
					valuePaper+=papers.get(i).getTop();
					valueScore+=tempScore;
				}
			}
			if (paperDAO.updateSubScore(map) > 0) {// 更新数据库卷子信息//更新成功,则更改测试者信息
				int total = testerDAO.selectObjScoreById(testerId) + totalSub;// 求总分
				int subPaper=knowPaper+experiencePaper+skillPaper+stylePaper+valuePaper;
				tester.setId(testerId);
				tester.setTotalSub(totalSub);
				tester.setTotal(total);
				tester.setTeacher(teacher);
				
				tester.setKnowPaper(knowPaper);
				tester.setExperiencePaper(experiencePaper);
				tester.setSkillPaper(skillPaper);
				tester.setStylePaper(stylePaper);
				tester.setValuePaper(valuePaper);
				tester.setSubPaper(subPaper);
				
				tester.setKnowScore(knowScore);
				tester.setExperienceScore(experienceScore);
				tester.setSkillScore(skillScore);
				tester.setStyleScore(styleScore);
				tester.setValueScore(valueScore);
				
				// 插入测试者的主观题分数和总分
				testerDAO.updateSubScore(tester);
				message = "主观题得分:" + totalSub + "\n总分为:" + total;
			} else {
				message = "系统错误-Service";
				System.out.println("更新数据库卷子信息失败");
			}
		} else {
			System.out.println("主观题题目数量和答案数量不相等");
			message = "系统错误-Service";
		}
		return message;
	}

	// 删除测试者//即删除测试者的试卷
	public String deletePaper(int testerId) {
		// TODO Auto-generated method stub
		String flag;
		if (testerId > 0) {
			if (testerDAO.deleteByPrimaryKey(testerId) > 0) {
				flag = "删除成功";
			} else {
				flag = "删除失败";
			}
		} else {
			flag = "输入错误";
		}

		return flag;
	}

}
