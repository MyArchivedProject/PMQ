package test;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import pmq.dao.QuestionMapper;
import pmq.dao.TargetMapper;
import pmq.pojo.Question;
import pmq.pojo.Target;
import pmq.service.IQuestionService;

public class test_question extends BaseTest{
	@Resource(name="question")
	private Question q;
	@Resource(name="questionMapper")
	private QuestionMapper qDAO;
	@Resource(name="targetMapper")
	private TargetMapper indexDAO;
	
	@Resource(name="questionService")
	private IQuestionService qService;
	
	List<Integer> all=new ArrayList<Integer>();
	
	//@Transactional
	@Test
	public void test1() { //
		List<Question>list=new ArrayList<Question>();
		Question q1=new Question();
		Question q2=new Question();
		Question q3=new Question();
		q1.setCode("111");
		q1.setIndexId(230);
		
		/*q.setDes("aa");
		q.getCode();
		q.getDes();
		q.setFlag(flag);
		q.setIndexF(indexF);
		q.setIndexId(indexId);*/
		list.add(q1);
		q2.setCode("112");
		q2.setIndexId(230);
		q3.setCode("113");
		q3.setIndexId(231);
		list.add(q2);
		list.add(q3);
		List<Target> tt=indexDAO.selectAll();
		for(int i=0;i<tt.size();i++){
			System.out.println(tt.get(i).getId());
		}
		System.out.println(indexDAO.updateAllNumSubObj(tt));
	}
	//@Test
	public void test() {
		List<Integer> list=new ArrayList<Integer>();
		list.add(178);
		list.add(179);
		list.add(180);
		qDAO.selectAllByIndexPlus(list);
		System.out.println(qDAO.selectAllByIndexPlus(list).size());
		//List<Question> quesMust=qDAO.selectMust();
		//int quesMustNum=quesMust.size();
		//System.out.println(quesMustNum);
		//System.out.println(qDAO.selectObjNumByIndexId(533));
		//System.out.println(qDAO.selectSubNumByIndexId(388));
		
		//System.out.println(qDAO.selectAllCode());
		
		//System.out.println(qService.findAllBy("", "", "专业广度"));
		//System.out.println(qDAO.findAllByIndex(0).size());
		//		all.add(23);
		//		all.add(25);
	   // System.out.println(qService.addQuestion("D:\\aaaa.xls"));
	}
	//@Test
	public void insert(){
		q.setQuestion("PMI关于项目管理的九大知识领域分别是项目集成管理、项目范围管理、项目时间管理、项目费用管理、项目质量管理、项目人力资源管理、项目沟通管理、项目风险管理、项目     管理。");			
		//indexDAO.selectByPIS("项目经理", "专业广度")
		q.setIndexId(1);
		q.setOptionA("采购");
		q.setScoreA(4);
		q.setOptionB("采购");
		q.setScoreB(0);
		q.setOptionC("采购");
		q.setScoreC(0);
		q.setOptionD("采购");
		q.setScoreD(0);
		q.setOptionE("采购");
		q.setScoreE(0);
		q.setOptionF("采购");
		q.setScoreF(0);
		q.setObjsub(0);
		q.setTop(4);
		System.out.println(qDAO.insert(q));
		
		//System.out.println(indexDAO.selectByPIS("项目经s理", "独立自主"));
	}
	//@Test
	@Transactional
	@Rollback(false)  //单元测试时，事物会自动进行回滚，注解式事物自动回滚关闭
	public void createIndex() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		// 确定要操作的excelc
		HSSFWorkbook workbook = new HSSFWorkbook(
				new POIFSFileSystem(new FileInputStream("D:/question.xls")));

		// 取第0个单元表
		HSSFSheet sheet = workbook.getSheetAt(0);
		//循环标签，用于直接跳出此循环
		continueOut:
		// sheet.getPhysicalNumberOfRows();求出所有行数
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			// 取一行操作
			HSSFRow row = sheet.getRow(i);			
			if(row.getCell(1)==null || row.getCell(1).getCellType()==HSSFCell.CELL_TYPE_BLANK){
				//提示此行有错误，结束程序
				break;
			}
			// row.getPhysicalNumberOfCells();求出本行的单元格数，也就是列数
			//通过职位和二级指标查找 职位指标 id;
			String post="";
			for (int j = 0; j < 18; j++) {
				//获取单元格
				Cell cell=row.getCell(j);
				//判断单元格是否为空，为空则设置为""
				String cellString;
				if(cell==null || cell.getCellType()==HSSFCell.CELL_TYPE_BLANK){
					cellString="";					
				}else{
					cellString = cell.toString().trim();
				}				
				
				int length = cellString.length();
				if (length >= 2) { // 这里大于等于2是防止有些列只有一个字符，到下面会报错
					// 通过截取最后两个字符，如果等于.0 就去除最后两个字符
					if (cellString.substring(length - 2, length).equals(".0"))
						cellString = cellString.substring(0, length - 2);
				}
				//判断是第几列，然后对实体index进行相对应的赋值
				switch (j) {
				case 0:
					q.setQuestion(cellString);
					break;
				case 1:					
					post=cellString;
					//System.out.println(post);
					break;
				case 2:
					int indexId=indexDAO.selectByPIS(post, cellString);
					System.out.println(post+","+cellString+","+indexDAO.selectByPIS(post, cellString));
					if(indexId!=0){
						q.setIndexId(indexId);
					}else{
						System.out.println("第"+(i+1)+"行不存在此职位下的二级指标");
						break continueOut;
					}
					break;
				case 3:
					q.setOptionA(cellString);
					break;
				case 4:
					if(cellString.equals("")){
						q.setScoreA(0);
					}else{
						q.setScoreA(Integer.valueOf(cellString));
					}					
					break;
				case 5:
					q.setOptionB(cellString);
					break;
				case 6:
					if(cellString.equals("")){
						q.setScoreB(0);
					}else{
						q.setScoreB(Integer.valueOf(cellString));
					}					
					break;
				case 7:
					q.setOptionC(cellString);
					break;
				case 8:
					if(cellString.equals("")){
						q.setScoreC(0);
					}else{
						q.setScoreC(Integer.valueOf(cellString));
					}					
					break;
				case 9:
					q.setOptionD(cellString);
					break;
				case 10:
					if(cellString.equals("")){
						q.setScoreD(0);
					}else{
						q.setScoreD(Integer.valueOf(cellString));
					}					
					break;
				case 11:
					q.setOptionE(cellString);
					break;
				case 12:
					if(cellString.equals("")){
						q.setScoreE(0);
					}else{
						q.setScoreE(Integer.valueOf(cellString));
					}					
					break;
				case 13:
					q.setOptionF(cellString);
					break;
				case 14:
					if(cellString.equals("")){
						q.setScoreF(0);
					}else{
						q.setScoreF(Integer.valueOf(cellString));
					}					
					break;
				case 15:
					Integer objsub;
					if(cellString.equals("客观题")){
						objsub=0;
					}else{
						objsub=1;
					}
					q.setObjsub(objsub);
					break;
				case 16:
					q.setDes(cellString);
					break;
				case 17:
					q.setTop(Integer.valueOf(cellString));
					break;
				}					
					
			}
			System.out.println(qDAO.insert(q));
			//还须判断事物是否回滚了
			//System.out.print(indexDAO.insert(index));
		}

	}
}
