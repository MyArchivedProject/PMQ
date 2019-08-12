package pmq.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pmq.dao.EvaluationMapper;
import pmq.dao.QuestionMapper;
import pmq.dao.TargetMapper;
import pmq.pojo.Evaluation;
import pmq.pojo.Post;
import pmq.pojo.Question;
import pmq.pojo.Target;
import pmq.service.IIndexService;

@Service(value = "indexService")
public class IndexServiceImpl implements IIndexService {
	@Resource(name = "targetMapper")
	private TargetMapper targetDAO;
	@Resource(name = "target")
	private Target index;
	@Resource(name = "evaluationMapper")
	private EvaluationMapper evaDAO;
	@Resource(name = "evaluation")
	private Evaluation eva;
	@Resource(name = "question")
	private Question question;
	@Resource(name = "questionMapper")
	private QuestionMapper qDAO;

	// 增加职位
	@Transactional
	public String addPost(String post) {
		// TODO Auto-generated method stub

		if (findPost(post)) {
			return "已经存在此职位";
		}
		/*
		 * int sum=0; // 确定要操作的excelc HSSFWorkbook workbook = new HSSFWorkbook(
		 * new POIFSFileSystem(new
		 * FileInputStream("src/main/resources/xls/index.xls"))); // 取第0个单元表
		 * HSSFSheet sheet = workbook.getSheetAt(0);
		 * //判断职位所对应的所有二级指标是否添加成功//判断相对应的等级评价是否添加成功 for (int i = 1; i < 17; i++)
		 * { HSSFRow row = sheet.getRow(i); //
		 * row.getPhysicalNumberOfCells();求出本行的单元格数，也就是列数 for (int j = 0; j < 6;
		 * j++) { String cellString = row.getCell(j).toString().trim(); int
		 * length = cellString.length(); if (length >= 2) { //
		 * 这里大于等于2是防止有些列只有一个字符，到下面会报错 // 通过截取最后两个字符，如果等于.0 就去除最后两个字符 if
		 * (cellString.substring(length - 2, length).equals(".0")) cellString =
		 * cellString.substring(0, length - 2); } // 判断是第几列，然后对实体index进行相对应的赋值
		 * switch (j) { case 0: index.setPost(post); break; case 1:
		 * index.setIndexF(cellString); break; case 2:
		 * index.setIndexS(cellString); break; case 3:
		 * index.setNumObj(Integer.valueOf(cellString)); break; case 4:
		 * index.setNumSub(Integer.valueOf(cellString)); break; case 5:
		 * index.setTotal(Integer.valueOf(cellString)); break; } }
		 * if(targetDAO.insert(index)>0){ sum++; } } if(sum==16){
		 * System.out.println("增加职位成功"); }
		 */

		index.setPost(post);
		index.setIndexF("专业知识");
		index.setIndexS("专业广度");
		index.setNumObj(0);
		index.setNumSub(0);
		index.setTotal(0);
		targetDAO.insert(index);
		index.setIndexS("专业深度");
		targetDAO.insert(index);

		index.setIndexF("工作经验");
		index.setIndexS("行业工作经验");
		targetDAO.insert(index);
		index.setIndexS("团队领导经验");
		targetDAO.insert(index);

		index.setIndexF("工作技能");
		index.setIndexS("主动学习");
		targetDAO.insert(index);
		index.setIndexS("解决问题");
		targetDAO.insert(index);
		index.setIndexS("策划与组织");
		targetDAO.insert(index);
		index.setIndexS("沟通与协调");
		targetDAO.insert(index);
		index.setIndexS("把控能力");
		targetDAO.insert(index);

		index.setIndexF("工作风格");
		index.setIndexS("正直诚信");
		targetDAO.insert(index);
		index.setIndexS("领导能力");
		targetDAO.insert(index);
		index.setIndexS("适应性");
		targetDAO.insert(index);
		index.setIndexS("主动性");
		targetDAO.insert(index);

		index.setIndexF("工作价值观");
		index.setIndexS("成就动机");
		targetDAO.insert(index);
		index.setIndexS("独立自主");
		targetDAO.insert(index);
		index.setIndexS("服务意识");
		targetDAO.insert(index);
		// 增加相对应的等级评价//等级评价将不自动增加
		/*
		 * List<Integer> allTar = targetDAO.selectAllByP(post); if (allTar !=
		 * null && allTar.size() == 16) { for (int i = 0; i < allTar.size();
		 * i++) { evaDAO.insertOrigin(allTar.get(i), post); if (i == 15) {
		 * return "添加成功"; } } }
		 */
		return "添加成功";
	}

	// 删除职位
	@Transactional
	public boolean deletePost(String post) {
		// TODO Auto-generated method stub
		boolean flag = false;
		/*
		 * if (targetDAO.deletePost(post) > 0 evaDAO.deleteByPost(post) ) { flag
		 * = true; }
		 */
		targetDAO.deletePost(post);
		evaDAO.deleteByPost(post);
		flag = true;
		return flag;
	}

	// 更新职位指标对应的题目数量
	public String updateNum(int id, int numObj, int numSub) {
		// TODO Auto-generated method stub
		if (qDAO.selectObjNumByIndexId(id) < numObj) {
			return "error_客观题数量小于题库的数量";
		}

		if (qDAO.selectSubNumByIndexId(id) < numSub) {
			return "error_主观题数量小于题库的数量";
		}

		if (targetDAO.updateNum(id, numObj, numSub) > 0) {
			return "success";
		}
		return "更新失败";
	}

	public boolean findPost(String post) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (targetDAO.selectByP(post) > 0) {
			flag = true;
		}
		return flag;
	}

	public String addIndex(Target target) {
		// TODO Auto-generated method stub
		String message;
		String post = target.getPost();
		String indexF = target.getIndexF();
		String indexS = target.getIndexS();
		if (targetDAO.selectBYPII(post, indexF, indexS).size() == 0) {
			if (targetDAO.insert(target) > 0) {
				message = "success";
			} else {
				message = "添加职位指标失败";
			}
		} else {
			// 设置属性，提示用户此职位指标已经存在了
			message = "此职位指标已经存在了";
		}
		return message;
	}

	public boolean deleteIndex(Integer id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (targetDAO.deleteByPrimaryKey(id) > 0) {
			/* evaDAO.deleteByIndexId(id); */
			flag = true;
		}
		return flag;
	}

	public boolean updateIndex(Target target) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (targetDAO.updateByPrimaryKey(target) > 0) {
			flag = true;
		}
		return flag;
	}

	public List<Target> findIndex(Target index) {
		// TODO Auto-generated method stub
		List<Target> all = null;
		if (index.getPost() == null) {
			index.setPost("");
		}
		if (index.getIndexF() == null) {
			index.setIndexF("");
		}
		if (index.getIndexS() == null) {
			index.setIndexS("");
		}
		all = targetDAO.selectAllBy(index);

		return all;
	}

	// 查询所有职位post//废弃了,plus取代
	public List<Post> getAllPost(String conPost) {
		// TODO Auto-generated method stub
		List<Post> allPost = new ArrayList<Post>();// 保存所有的职位信息
		if (conPost == null) {
			conPost = "";
		}
		index.setPost(conPost);
		List<String> list;// 模糊查询从职位指标表里获取所有的职位
		if ("".equals(conPost)) {
			list = targetDAO.selectAllPost();
		} else {
			list = targetDAO.selectAllPostBy(index);
		}
		for (int i = 0; i < list.size(); i++) {// 获取职位相对的信息
			Post post = new Post();
			String p = list.get(i);
			Integer objNum = qDAO.selectObjSum(p);
			Integer subNum = qDAO.selectSubSum(p);
			Integer indexSNum = targetDAO.selectIndexSNumByPost(p);
			post.setPost(list.get(i));
			post.setObjNum(objNum);
			post.setSubNum(subNum);
			post.setIndexSNum(indexSNum);
			allPost.add(post);
		}
		return allPost;
	}

	// 查询所有职位post
	public List<Post> getAllPostPlus(String conPost) {
		// TODO Auto-generated method stub
		if ("".equals(conPost)) {
			conPost = "";
		}
		return targetDAO.selectAllNumObjSub(conPost);
	}
}
