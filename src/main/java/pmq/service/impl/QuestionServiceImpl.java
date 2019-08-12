package pmq.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import pmq.dao.QuestionMapper;
import pmq.dao.TargetMapper;
import pmq.pojo.Question;
import pmq.pojo.Target;
import pmq.service.IQuestionService;

@Service(value = "questionService")
public class QuestionServiceImpl implements IQuestionService {
	@Resource(name = "questionMapper")
	private QuestionMapper qDAO;
	@Resource(name = "targetMapper")
	private TargetMapper indexDAO;
	@Resource(name = "question")
	private Question q;

	@SuppressWarnings({ "deprecation" })
	@Transactional()//废弃了
	public List<String> addQuestion(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		// 记录所有的错误
		List<String> errors = new ArrayList<String>();

		InputStream is = file.getInputStream();
		// 验证是否是*.xls的文件
		/*
		 * int len = url.length(); if (!(len >= 4 && (url.substring(len - 4,
		 * len).equals(".xls")))) { errors.add("文件格式错,请使用以.xls为后缀的文件"); return
		 * errors; }
		 */
		// 确定要操作的excelc
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(is);
			// workbook = new HSSFWorkbook(new POIFSFileSystem(new
			// FileInputStream(url)));
		} catch (org.apache.poi.poifs.filesystem.OfficeXmlFileException e) {
			// 判断是否是真正的xls文件
			errors.add("error0_文件格式错误,请使用模版文件");
			return errors;
		}
		// 为下面判断分数是否是数字类型的作准备
		Pattern pat = Pattern.compile("[0-9][0-9]*");

		// 获取所有的测试者自定义的题目编号
		List<String> allCode = qDAO.selectAllCode();
		if (allCode == null) {
			allCode = new ArrayList<String>();
		}

		// 记录共有多少条数据
		int sumAll = 0;
		// 记录共有多少条记录可以被插入
		int sumSuccess = 0;
		// 记录每行数据是否有错误，默认为true，true为没有错误
		boolean flag;
		// 记录数据最终是否进行回滚，true为不回滚，当flagSuccess为false时不再执行插入操作。
		boolean flagSuccess = true;
		// 取第0个单元表
		for (int page = 0; page < workbook.getNumberOfSheets(); page++) {
			HSSFSheet sheet = workbook.getSheetAt(page);
			// 循环标签，用于直接跳出此循环
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				flag = true;
				// 取一行操作
				HSSFRow row = sheet.getRow(i);
				// 如果编码为空，则默认此行没有数据
				if (row.getCell(0) == null || row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_BLANK
						|| row.getCell(0).toString().trim().equals("")) {
					continue;
				} else {
					sumAll++;
				}
				// 保存职位的变量,为下面通过职位和二级指标查找 职位指标id作准备;
				String post = "";
				// 为求最高分作准备
				int top[] = new int[6];
				// 用来保存每行的错误
				StringBuffer errorBuf = new StringBuffer();
				errorBuf.append("表格:" + sheet.getSheetName() + "; 第" + (i + 1) + "行;");
				for (int j = 0; j < 18; j++) {
					// 获取单元格
					Cell cell = row.getCell(j);
					// 判断单元格是否为空，为空则设置为""
					String cellString;
					if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						cellString = "";
					} else {
						cellString = cell.toString().trim();
					}
					// 去除.0
					int length = cellString.length();
					if (length >= 2) { // 这里大于等于2是防止有些列只有一个字符，到下面会报错
						// 通过截取最后两个字符，如果等于.0 就去除最后两个字符
						if (cellString.substring(length - 2, length).equals(".0"))
							cellString = cellString.substring(0, length - 2);
					}

					// 判断是第几列，然后对实体index进行相对应的赋值
					switch (j) {
					case 0:
						if (length > 25) {
							errorBuf.append("A列: 题目编号不能超过25个字符;");
							flag = false;
							break;
						}
						checkOut: for (int ci = 0; ci < allCode.size(); ci++) {
							if (cellString.equals(allCode.get(ci))) {
								errorBuf.append("A列: 题目号码已存在,请检查题库里或者excel里是否已经存在此题目号码;");
								flag = false;
								break checkOut;
							}
						}
						allCode.add(cellString);
						q.setCode(cellString);
					case 1:
						if ("".equals(cellString)) {
							errorBuf.append("B列: 题目为空;");
							// System.out.println("B列,题目为空");
							flag = false;
							break;
						} else if (length > 255) {
							errorBuf.append("B列: 题目字符数量不能大于255;");
						}
						q.setQuestion(cellString);
						break;
					case 2:
						post = cellString;
						break;
					case 3:
						int indexId = indexDAO.selectByPIS(post, cellString);
						// System.out.println(post + "," + cellString + "," +
						// indexDAO.selectByPIS(post, cellString));
						if (indexId != 0) {
							q.setIndexId(indexId);
						} else {
							errorBuf.append("C列D列: 无法查询到" + post + "职位下的" + cellString + "二级指标;");
							flag = false;
						}
						break;
					case 4:
						if (length > 100) {
							errorBuf.append("E列: 选项一的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionA(cellString);
						break;
					case 5:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("F列: 必须是整数类型的;");
							flag = false;
							break;
						}
						q.setScoreA(Integer.valueOf(cellString));
						top[0] = Integer.valueOf(cellString);
						break;
					case 6:
						if (length > 100) {
							errorBuf.append("G列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionB(cellString);
						break;
					case 7:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("H列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[1] = Integer.valueOf(cellString);
						q.setScoreB(Integer.valueOf(cellString));

						break;
					case 8:
						if (length > 100) {
							errorBuf.append("I列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionC(cellString);
						break;
					case 9:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("J列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[2] = Integer.valueOf(cellString);
						q.setScoreC(Integer.valueOf(cellString));

						break;
					case 10:
						if (length > 100) {
							errorBuf.append("K列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionD(cellString);
						break;
					case 11:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("L列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[3] = Integer.valueOf(cellString);
						q.setScoreD(Integer.valueOf(cellString));

						break;
					case 12:
						if (length > 100) {
							errorBuf.append("M列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionE(cellString);
						break;
					case 13:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("N列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[4] = Integer.valueOf(cellString);
						q.setScoreE(Integer.valueOf(cellString));

						break;
					case 14:
						if (length > 100) {
							errorBuf.append("O列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionF(cellString);
						break;
					case 15:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("P列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[5] = Integer.valueOf(cellString);
						q.setScoreF(Integer.valueOf(cellString));

						break;
					case 16:
						if (cellString.equals("客观题")) {
							q.setObjsub(0);
						} else if (cellString.equals("主观题")) {
							q.setObjsub(1);
						} else {
							errorBuf.append(" Q列: 题型不能为空");
							flag = false;
							break;
						}
						break;
					case 17:
						if (length > 100) {
							errorBuf.append("R列: 具体描述的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setDes(cellString);
						break;
					}
				}
				// 设置最高分属性
				q.setTop(Arrays.stream(top).max().getAsInt());
				// 如果发现有错，则停止进行数据库插入,只进行错误的检查
				if (!flag) {
					errors.add(errorBuf.toString());
					if (flagSuccess == true) {
						flagSuccess = false;
					}
				} else {
					// 可以进行插入的数据
					sumSuccess++;
				}
				if (flagSuccess) {
					qDAO.insert(q);
				}
			}

		}
		// 当循环结束时，如果excel里存在错误，则进行回滚
		if (flagSuccess == true) {
			errors.add("success! 添加成功！共" + sumAll + "条数据," + sumSuccess + "条有效数据");
		} else {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			errors.add("fail! 添加失败！共" + sumAll + "条数据," + sumSuccess + "条有效数据,请对无效的数据进行修改或者删除");
		}
		workbook.close();
		return errors;
	}

	@Transactional //可以进行小优化
	public boolean deleteQById(List<Integer> all) {
		// TODO Auto-generated method stub
		boolean flag = false;
		int num = all.size();
		Iterator<Integer> iter = all.iterator();
		while (iter.hasNext()) {
			int id = iter.next();
			qDAO.deleteByPrimaryKey(id);
			num--;
		}
		if (num == 0) {
			flag = true;
		}
		indexDAO.updateAllNumSubObj(indexDAO.selectAll());//更新所有的数量
		return flag;
	}
	public List<Question> findAllBy(Target target, Question ques) {
		// TODO Auto-generated method stub
		// 定义一个装载所有题目的集合
		List<Question> allQ = new ArrayList<Question>();
		//假如所有条件都是默认值
		if("".equals(target.getPost()) && "".equals(target.getIndexF()) && "".equals(target.getIndexS())
				&&ques.getObjsub() == 2 && ques.getFlag() == 2){
			allQ=qDAO.selectAllInnerJoinTarget();
			return allQ;
		}else{
			// 获取满足条件下的所有二级指标id
			List<Integer> all=null;
			if("".equals(target.getPost()) && "".equals(target.getIndexF()) && "".equals(target.getIndexS())){
				all=indexDAO.selectAllId();
			}else{
				all = indexDAO.selectBYPII(target.getPost(), target.getIndexF(), target.getIndexS());
			}
			if (null != all && all.size() > 0) {
				// 判断question表格的objsub和flag
				if (ques.getObjsub() == 2 && ques.getFlag() == 2) {
					/*for (int i = 0; i < all.size(); i++) {
						allQ.addAll(qDAO.selectAllByIndex(all.get(i)));
					} 废弃了1*/
					//allQ.addAll(qDAO.selectAllByIndexPlus(all));//废弃了2
					allQ.addAll(qDAO.selectAllByIndex(target));
				} else if (ques.getObjsub() == 2) {//是否必考题未知
					/*for (int i = 0; i < all.size(); i++) {
						ques.setIndexId(all.get(i));
						allQ.addAll(qDAO.selectAllByIndexA(ques));
					}*/
					//allQ.addAll(qDAO.selectAllByIndexAPlus(all, ques.getFlag()));
					allQ.addAll(qDAO.selectAllByIndexA(target,ques.getFlag()));
				} else if (ques.getFlag() == 2) {
					/*for (int i = 0; i < all.size(); i++) {
						ques.setIndexId(all.get(i));
						allQ.addAll(qDAO.selectAllByIndexB(ques));
					}*/
					//allQ.addAll(qDAO.selectAllByIndexBPlus(all, ques.getObjsub()));
					allQ.addAll(qDAO.selectAllByIndexB(target,ques.getObjsub()));
				} else {
					/*for (int i = 0; i < all.size(); i++) {
						ques.setIndexId(all.get(i));
						allQ.addAll(qDAO.selectAllByIndexC(ques));
					}*/
					//allQ.addAll(qDAO.selectAllByIndexCPlus(all,ques.getFlag(), ques.getObjsub()));
					allQ.addAll(qDAO.selectAllByIndexC(target,ques.getFlag(),ques.getObjsub()));
				}
			}
		}
		

		return allQ;
	}

	public boolean updateQById(Question ques) {
		// TODO Auto-generated method stub
		int temp[] = new int[6];
		if (ques.getScoreA() == null) {
			ques.setScoreA(0);
		}
		if (ques.getScoreB() == null) {
			ques.setScoreB(0);
		}
		if (ques.getScoreC() == null) {
			ques.setScoreC(0);
		}
		if (ques.getScoreD() == null) {
			ques.setScoreD(0);
		}
		if (ques.getScoreE() == null) {
			ques.setScoreE(0);
		}
		if (ques.getScoreF() == null) {
			ques.setScoreF(0);
		}
		temp[0] = ques.getScoreA();
		temp[1] = ques.getScoreB();
		temp[2] = ques.getScoreC();
		temp[3] = ques.getScoreD();
		temp[4] = ques.getScoreE();
		temp[5] = ques.getScoreF();
		int top = temp[0];
		for (int i = 1; i < 6; i++) {
			if (temp[i] > top) {
				top = temp[i];
			}
		}
		ques.setTop(top);
		if (qDAO.updateByPrimaryKey(ques) > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings({ "deprecation" })
	@Transactional()
	public List<String> addQuestionPlus(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		List<Question>allQues=new ArrayList<Question>();//记录所有的题目
		Question q;//记录每一道题目
		// 记录所有的错误
		List<String> errors = new ArrayList<String>();
		InputStream is = file.getInputStream();
		// 确定要操作的excelc
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(is);
		} catch (org.apache.poi.poifs.filesystem.OfficeXmlFileException e) {
			// 判断是否是真正的xls文件
			errors.add("error0_文件格式错误,请使用模版文件");
			return errors;
		}
		// 为下面判断分数是否是数字类型的作准备
		Pattern pat = Pattern.compile("[0-9][0-9]*");

		// 获取所有的测试者自定义的题目编号
		List<String> allCode = qDAO.selectAllCode();
		if (allCode == null) {
			allCode = new ArrayList<String>();
		}
		// 记录共有多少条数据
		int sumAll = 0;
		// 记录共有多少条记录可以被插入
		int sumSuccess = 0;
		// 记录每行数据是否有错误，默认为true，true为没有错误
		boolean flag;
		// 记录数据最终是否进行回滚，true为不回滚，当flagSuccess为false时不再执行插入操作。
		boolean flagSuccess = true;
		// 取第0个单元表
		for (int page = 0; page < workbook.getNumberOfSheets(); page++) {
			HSSFSheet sheet = workbook.getSheetAt(page);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				flag = true;
				// 取一行操作
				HSSFRow row = sheet.getRow(i);
				// 如果编码为空，则默认此行没有数据
				if (row.getCell(0) == null || row.getCell(0).getCellType() == HSSFCell.CELL_TYPE_BLANK
						|| row.getCell(0).toString().trim().equals("")) {
					continue;
				} else {
					sumAll++;
				}
				// 保存职位的变量,为下面通过职位和二级指标查找 职位指标id作准备;
				String post = "";
				// 为求最高分作准备
				int top[] = new int[6];
				// 用来保存每行的错误
				StringBuffer errorBuf = new StringBuffer();
				errorBuf.append("表格:" + sheet.getSheetName() + "; 第" + (i + 1) + "行;");
				q=new Question();
				for (int j = 0; j < 18; j++) {
					// 获取单元格
					Cell cell = row.getCell(j);
					// 判断单元格是否为空，为空则设置为""
					String cellString;
					if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
						cellString = "";
					} else {
						cellString = cell.toString().trim();
					}
					// 去除.0
					int length = cellString.length();
					if (length >= 2) { // 这里大于等于2是防止有些列只有一个字符，到下面会报错
						// 通过截取最后两个字符，如果等于.0 就去除最后两个字符
						if (cellString.substring(length - 2, length).equals(".0"))
							cellString = cellString.substring(0, length - 2);
					}
					// 判断是第几列，然后对实体index进行相对应的赋值
					switch (j) {
					case 0:
						if (length > 25) {
							errorBuf.append("A列: 题目编号不能超过25个字符;");
							flag = false;
							break;
						}
						checkOut: for (int ci = 0; ci < allCode.size(); ci++) {
							if (cellString.equals(allCode.get(ci))) {
								errorBuf.append("A列: 题目号码已存在,请检查题库里或者excel里是否已经存在此题目号码;");
								flag = false;
								break checkOut;
							}
						}
						allCode.add(cellString);
						q.setCode(cellString);
					case 1:
						if ("".equals(cellString)) {
							errorBuf.append("B列: 题目为空;");
							// System.out.println("B列,题目为空");
							flag = false;
							break;
						} else if (length > 255) {
							errorBuf.append("B列: 题目字符数量不能大于255;");
						}
						q.setQuestion(cellString);
						break;
					case 2:
						post = cellString;
						break;
					case 3:
						int indexId = indexDAO.selectByPIS(post, cellString);
						// System.out.println(post + "," + cellString + "," +
						// indexDAO.selectByPIS(post, cellString));
						if (indexId != 0) {
							q.setIndexId(indexId);
						} else {
							errorBuf.append("C列D列: 无法查询到" + post + "职位下的" + cellString + "二级指标;");
							flag = false; 
						}
						break;
					case 4:
						if (length > 100) {
							errorBuf.append("E列: 选项一的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionA(cellString);
						break;
					case 5:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("F列: 必须是整数类型的;");
							flag = false;
							break;
						}
						q.setScoreA(Integer.valueOf(cellString));
						top[0] = Integer.valueOf(cellString);
						break;
					case 6:
						if (length > 100) {
							errorBuf.append("G列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionB(cellString);
						break;
					case 7:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("H列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[1] = Integer.valueOf(cellString);
						q.setScoreB(Integer.valueOf(cellString));

						break;
					case 8:
						if (length > 100) {
							errorBuf.append("I列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionC(cellString);
						break;
					case 9:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("J列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[2] = Integer.valueOf(cellString);
						q.setScoreC(Integer.valueOf(cellString));

						break;
					case 10:
						if (length > 100) {
							errorBuf.append("K列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionD(cellString);
						break;
					case 11:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("L列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[3] = Integer.valueOf(cellString);
						q.setScoreD(Integer.valueOf(cellString));

						break;
					case 12:
						if (length > 100) {
							errorBuf.append("M列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionE(cellString);
						break;
					case 13:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("N列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[4] = Integer.valueOf(cellString);
						q.setScoreE(Integer.valueOf(cellString));

						break;
					case 14:
						if (length > 100) {
							errorBuf.append("O列: 选项二的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setOptionF(cellString);
						break;
					case 15:
						if ("".equals(cellString)) {
							cellString = "0";
						} else if (!pat.matcher(cellString).matches()) {
							errorBuf.append("P列: 必须是整数类型的;");
							flag = false;
							break;
						}
						top[5] = Integer.valueOf(cellString);
						q.setScoreF(Integer.valueOf(cellString));

						break;
					case 16:
						if (cellString.equals("客观题")) {
							q.setObjsub(0);
						} else if (cellString.equals("主观题")) {
							q.setObjsub(1);
						} else {
							errorBuf.append(" Q列: 题型不能为空");
							flag = false;
							break;
						}
						break;
					case 17:
						if (length > 100) {
							errorBuf.append("R列: 具体描述的字符数量不能大于100;");
							flag = false;
							break;
						}
						q.setDes(cellString);
						break;
					}
				}
				// 设置最高分属性
				q.setTop(Arrays.stream(top).max().getAsInt());
				// 如果发现有错，则停止进行数据库插入,只进行错误的检查
				if (!flag) {
					errors.add(errorBuf.toString());
					if (flagSuccess == true) {
						flagSuccess = false;
					}
				} else {
					// 可以进行插入的数据
					sumSuccess++;
				}
				if (flagSuccess) {
					allQues.add(q);
				}
			}
		}
		if(allQues.size()>0 && flagSuccess){
			System.out.println(qDAO.insertAll(allQues));//插入题库
			indexDAO.updateAllNumSubObj(indexDAO.selectAll());//更新所有的数量
			errors.add("success! 添加成功！共" + sumAll + "条数据," + sumSuccess + "条有效数据");
		}else{
			errors.add("fail! 添加失败！共" + sumAll + "条数据," + sumSuccess + "条有效数据,请对无效的数据进行修改或者删除");
		}
		
		workbook.close();
		return errors;
	}

}
