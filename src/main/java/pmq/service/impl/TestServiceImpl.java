package pmq.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pmq.dao.EvaluationMapper;
import pmq.dao.PaperMapper;
import pmq.dao.QuestionMapper;
import pmq.dao.TargetMapper;
import pmq.dao.TesterMapper;
import pmq.pojo.Evaluation;
import pmq.pojo.Paper;
import pmq.pojo.Question;
import pmq.pojo.Target;
import pmq.pojo.Tester;
import pmq.pojo.ToTesterEva;
import pmq.service.ITestService;
import pmq.utils.MyCloneUtil;

@Service(value = "testService")
public class TestServiceImpl implements ITestService {

	@Resource(name = "targetMapper")
	private TargetMapper targetDAO;
	@Resource(name = "questionMapper")
	private QuestionMapper qDAO;

	@Resource(name = "paper")
	private Paper paper;
	@Resource(name = "paperMapper")
	private PaperMapper paperDAO;

	@Resource(name = "evaluation")
	private Evaluation eva;
	@Resource(name = "evaluationMapper")
	private EvaluationMapper evaDAO;

	@Resource(name = "testerMapper")
	private TesterMapper testerDAO;

	public List<List<Paper>> getPaper(String post) {
		// TODO Auto-generated method stub
		// 同时保存两份试卷，第一份是保留在session里的,第二份是返回给测试者的
		List<List<Paper>> both = new ArrayList<List<Paper>>();
		// 用于保存在session的，无序的暂时的试卷
		List<Paper> papers = new ArrayList<Paper>();
		// 用于保存给测试者的暂时的试卷
		List<Paper> all = new ArrayList<Paper>();
		
		// 获取此职位的所有职位指标id
		List<Target> allTarget = targetDAO.selectByPost(post);
		//获取所有必考题
		List<Question> quesMust=qDAO.selectMust();
		int quesMustNum=quesMust.size();

		// 通过职位指标ID获取所有的题目
		if (null != allTarget && allTarget.size() > 0) {
			for (int i = 0; i < allTarget.size(); i++) {
				// 获取每一个二级指标的所有题目
				Target tar = allTarget.get(i);
				String indexF = tar.getIndexF();// 一级指标名称
				int indexId = tar.getId();// 职位指标Id
				int numObj = tar.getNumObj();// 客观题数量
				int numSub = tar.getNumSub();// 主观题数量	
				if (numObj > 0) {
					List<Question>allQues=new ArrayList<Question>();
					//先加入必考题
					if(quesMustNum>0){
						for(int mustI=0;mustI<quesMust.size();mustI++){						
							if(numObj>0){
								//是否属于此二级指标下的题目，是否是客观题
								if(quesMust.get(mustI).getIndexId()==indexId && quesMust.get(mustI).getObjsub()==0){
									allQues.add(quesMust.get(mustI));
									quesMust.get(mustI).setIndexId(-1);//设置此题目的职位指标id为-1//此题已经被加入试卷里
									numObj--;//需要的客观题数量-1
									quesMustNum--;//必考题数量-1
								}
							}					
						}
					}					
					if(numObj>0){
						// 获取该职位指标下的一定数量的客观题
						allQues.addAll(qDAO.selectObjByIndex(indexId, numObj));
					}										
					Iterator<Question> iter =allQues.iterator();
					while (iter.hasNext()) {
						Question q = iter.next();
						paper = new Paper();
						BeanUtils.copyProperties(q, paper);
						paper.setIndexF(indexF);
						papers.add(paper);// 保存在session里的试卷
						// 返回给测试者的试卷，把不必要显示的信息去掉
						all.add(getRidOf(paper));
					}
				}
				if (numSub > 0) {
					List<Question>allQues=new ArrayList<Question>();
					//先加入必考题
					if(quesMustNum>0){
						for(int mustI=0;mustI<quesMust.size();mustI++){
							if(numSub>0){
								if(quesMust.get(mustI).getIndexId()==indexId && quesMust.get(mustI).getObjsub()==1){
									allQues.add(quesMust.get(mustI));
									quesMust.get(mustI).setIndexId(-1);//设置此题目的职位指标id为-1//此题已经被加入试卷里
									numSub--;
									quesMustNum--;//必考题数量-1
								}
							}
						}
					}
					if(numSub>0){
						// 获取该职位指标下的一定数量的主观题
						allQues.addAll(qDAO.selectSubByIndex(indexId, numSub));
					}			
					//遍历进卷子paper里
					Iterator<Question> iter = allQues.iterator();
					while (iter.hasNext()) {
						paper = new Paper();
						// BeanUtils.
						BeanUtils.copyProperties(iter.next(), paper);
						paper.setIndexF(indexF);
						papers.add(paper);
						// 返回给测试者的试卷，把不必要显示给测试者的信息去掉
						all.add(getRidOf(paper));
					}
				}
			}
		}
		//下面的代码为后面加上去的,为了使题目变得有序,更方便前端进行逻辑控制
		// 有序的//用于保存在session里的试卷
		List<Paper> paperss = new ArrayList<Paper>();
		// 有序的// 用于保存给测试者的试卷
		List<Paper> alls = new ArrayList<Paper>();
		// 保存每一个模块的题目//使其有序
		// 保存给管理员的
		List<Paper> informationList = new ArrayList<Paper>();
		List<Paper> knowList = new ArrayList<Paper>();
		List<Paper> experienceList = new ArrayList<Paper>();
		List<Paper> skillList = new ArrayList<Paper>();
		List<Paper> styleList = new ArrayList<Paper>();
		List<Paper> valueList = new ArrayList<Paper>();
		// 保存给测试者的
		List<Paper> informationListT = new ArrayList<Paper>();
		List<Paper> knowListT = new ArrayList<Paper>();
		List<Paper> experienceListT = new ArrayList<Paper>();
		List<Paper> skillListT = new ArrayList<Paper>();
		List<Paper> styleListT = new ArrayList<Paper>();
		List<Paper> valueListT = new ArrayList<Paper>();
		for (int i = 0; i < papers.size(); i++) {
			if (papers.get(i).getFlag() == 1) {
				informationList.add(papers.get(i));
				informationListT.add(all.get(i));
			} else if ("专业知识".equals(papers.get(i).getIndexF())) {
				 knowList.add(papers.get(i));
				 knowListT.add(all.get(i));
			} else if ("工作经验".equals(papers.get(i).getIndexF())) {
				experienceList.add(papers.get(i));
				experienceListT.add(all.get(i));
			} else if ("工作技能".equals(papers.get(i).getIndexF())) {
				skillList.add(papers.get(i));
				skillListT.add(all.get(i));
			} else if ("工作风格".equals(papers.get(i).getIndexF())) {
				styleList.add(papers.get(i));
				styleListT.add(all.get(i));
			} else if ("工作价值观".equals(papers.get(i).getIndexF())) {
				valueList.add(papers.get(i));
				valueListT.add(all.get(i));
			}
		}
		paperss.addAll(informationList);
		paperss.addAll(knowList);
		paperss.addAll(experienceList);
		paperss.addAll(skillList);
		paperss.addAll(styleList);
		paperss.addAll(valueList);
		alls.addAll(informationListT);
		alls.addAll(knowListT);
		alls.addAll(experienceListT);
		alls.addAll(skillListT);
		alls.addAll(styleListT);
		alls.addAll(valueListT);
		both.add(paperss);
		both.add(alls);
		return both;
	}

	private Paper getRidOf(Paper paper) {
		Paper p;
		try {
			p = MyCloneUtil.clone(paper);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return p = new Paper();
		}
		p.setScoreA(null);
		p.setScoreB(null);
		p.setScoreC(null);
		p.setScoreD(null);
		p.setScoreE(null);
		p.setScoreF(null);
		p.setDes(null);
		p.setTop(null);
		p.setIndexId(null);
		// 假如是主观题,把选项也去掉
		if (p.getObjsub() == 1) {
			p.setOptionA(null);
			p.setOptionB(null);
			p.setOptionC(null);
			p.setOptionD(null);
			p.setOptionE(null);
			p.setOptionF(null);
		}
		return p;
	}

	@Transactional
	public List<ToTesterEva> submitPaper(List<Paper> papers, List<Object> answers, Tester tester) {
		// TODO Auto-generated method stub
		List<ToTesterEva> both = new ArrayList<ToTesterEva>();
		/*List<String> evaName = new ArrayList<String>();
		List<String> evaContent = new ArrayList<String>();*/
		
		int totalPaper=0;//试卷的满分是	
		int objPaper=0; //试卷客观题的满分值
		int totalObj = 0;// 总分
		int numObj = 0;// 选择题总数
		int numSub=0;//主观题总数
		// 向数据库插入测试者的数据，并返回的自增的主键id到tester里
		testerDAO.insert(tester);
		
		
		boolean hasSub=false;//判断是否存在主观题
		// 把所有答案都遍历到试卷上/// 获取相对应的答案和测试题,进行匹配
		for (int i = 0; i < papers.size(); i++) {
			// 设置此道测试题是谁提交的
			papers.get(i).setTesterId(tester.getId());
			totalPaper+=papers.get(i).getTop();
			
			// 判断是客观题吗
			if (papers.get(i).getObjsub() == 0) {
				paper = papers.get(i);
				char option = answers.get(i).toString().trim().charAt(0);
				switch (option) {
				case 'a':
					papers.get(i).setAnswer("a");
					papers.get(i).setScore(paper.getScoreA());
					break;
				case 'b':
					papers.get(i).setAnswer("b");
					papers.get(i).setScore(paper.getScoreB());
					break;
				case 'c':
					papers.get(i).setAnswer("c");
					papers.get(i).setScore(paper.getScoreC());
					break;
				case 'd':
					papers.get(i).setAnswer("d");
					papers.get(i).setScore(paper.getScoreD());
					break;
				case 'e':
					papers.get(i).setAnswer("e");
					papers.get(i).setScore(paper.getScoreE());
					break;
				case 'f':
					papers.get(i).setAnswer("f");
					papers.get(i).setScore(paper.getScoreF());
					break;
				}
				numObj++;
				totalObj += papers.get(i).getScore();
				objPaper+=papers.get(i).getTop();
			}else{
				numSub++;
				if(answers.get(i)==null){
					papers.get(i).setAnswer("");
				}else{
					papers.get(i).setAnswer(answers.get(i)+"");
				}			
				hasSub=true;
			}
		}
		//判断是否有主观题
		if(!hasSub){
			tester.setTeacher("无主观题");
			tester.setTotal(totalObj);
		}else{
			tester.setTotal(0);
			tester.setTeacher("");
		}
		//记录每一个一级指标下的得分
		
		
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
		//获取每一个一级指标的分数
		for (int i = 0; i < papers.size(); i++) {
			/*if (papers.get(i).getFlag() == 1) {		
			} else */
			if(papers.get(i).getObjsub()==0){ //是客观题则进行如下操作
				if ("专业知识".equals(papers.get(i).getIndexF())) {
					knowPaper+=papers.get(i).getTop();
					knowScore+=papers.get(i).getScore();
				} else if ("工作经验".equals(papers.get(i).getIndexF())) {
					experiencePaper+=papers.get(i).getTop();
					experienceScore+=papers.get(i).getScore();
				} else if ("工作技能".equals(papers.get(i).getIndexF())) {
					skillPaper+=papers.get(i).getTop();
					skillScore+=papers.get(i).getScore();
				} else if ("工作风格".equals(papers.get(i).getIndexF())) {
					stylePaper+=papers.get(i).getTop();
					styleScore+=papers.get(i).getScore();
				} else if ("工作价值观".equals(papers.get(i).getIndexF())) {
					valuePaper+=papers.get(i).getTop();
					valueScore+=papers.get(i).getScore();
				}
			}
			
		}
		tester.setKnowPaper(knowPaper);
		tester.setExperiencePaper(experiencePaper);
		tester.setSkillPaper(skillPaper);
		tester.setStylePaper(stylePaper);
		tester.setValuePaper(valuePaper);
		
		tester.setKnowScore(knowScore);
		tester.setExperienceScore(experienceScore);
		tester.setSkillScore(skillScore);
		tester.setStyleScore(styleScore);
		tester.setValueScore(valueScore);
		
		
		//进行持久化
		tester.setTotalPaper(totalPaper);	
		tester.setObjPaper(objPaper);
		tester.setNumObj(numObj);
		tester.setNumSub(numSub);
		tester.setTotalObj(totalObj);
		// 更新测试者的信息客观题数量和客观题分数和试卷的满分
		testerDAO.updateObjScore(tester);
		paperDAO.insertPapers(papers);
		
		
		// 查询所有 在使用状态的 等级评价
		List<Evaluation> listEva = evaDAO.selectAllByPostF(tester.getPost());
		for (int i = 0; i < listEva.size(); i++) {
			// 用来保存测试者每一个二级指标的总分
			int sum = 0;
			eva = listEva.get(i);
			// 判断是自定义等级评价吗
			if (eva.getMultiIndex() == null) {
				// 获取测试者在这个二级指标上的总分
				for (int j = 0; j < papers.size(); j++) {
					paper = papers.get(i);
					if (paper.getIndexId() == eva.getIndexId()) {
						sum += paper.getScore();
					}
				}
			} else {
				String[] array = eva.getMultiIndex().split(";");
				// 获取测试者在这个等级评价上的总分
				for (int j = 0; j < papers.size(); j++) {
					paper = papers.get(i);
					for (int k = 1; k < array.length; k++) {
						// 获取测试者在这个二级指标上的总分
						if (paper.getIndexId().equals(Integer.valueOf(array[k].trim()))) {
							sum += paper.getScore();
						}
					}
				}
			}

			// 根据测试者该等级评价的所有二级指标总分获取相应的等级评价。
			String evaStr;
			if (sum >= eva.getMiniA()) {
				evaStr = eva.getEvaA();
			} else if (sum >= eva.getMiniB()) {
				evaStr = eva.getEvaB();
			} else if (sum >= eva.getMiniC()) {
				evaStr = eva.getEvaC();
			} else if (sum >= eva.getMiniD()) {
				evaStr = eva.getEvaD();
			} else if (sum >= eva.getMiniE()) {
				evaStr = eva.getEvaE();
			} else if (sum >= eva.getMiniF()) {
				evaStr = eva.getEvaF();
			} else {
				evaStr = "没有符合等级的评价";
			}
			// 建立测试者的等级评价
			/*String multiIndex = eva.getMultiIndex();
			ToTesterEva evaT;
			if (multiIndex == null) {
				evaT=new ToTesterEva ();
				// 查询职位指标的二级指标，也就是等级评价的名称
				evaT.setEvaName(targetDAO.selectSById(listEva.get(i).getIndexId()));
				// 添加评价内容
				evaT.setEvaContent(evaStr);
			} else {
				evaT=new ToTesterEva ();
				String[] array = multiIndex.split(";");
				evaT.setEvaName(array[0]);
				evaT.setEvaContent(evaStr);
			}*/
			ToTesterEva evaT=new ToTesterEva ();	
			evaT.setEvaName(eva.getTitle());
			evaT.setEvaContent(evaStr);
			both.add(evaT);
		}
				
		return both;
	}

	public List<String> getAllPost() {
		// TODO Auto-generated method stub
		return targetDAO.selectAllPost();
	}
}
