package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import pmq.dao.TargetMapper;
import pmq.pojo.Count未使用;
import pmq.pojo.Post;
import pmq.pojo.Target;
import pmq.service.IIndexService;



public class test_index extends BaseTest {
	@Resource(name = "target")
	private Target index;
	@Resource(name = "targetMapper")
	private TargetMapper indexDAO;
	@Resource(name="indexService")
	private IIndexService indexService;
	@Test
	public void test1(){
		indexDAO.updateAllNumSubObj(indexDAO.selectAll());
	}
	//@Test
	public void test() throws FileNotFoundException, IOException{
		//Count c=indexDAO.selectAllNumObjSubA("项目经理");
	    List <Post>cc=indexDAO.selectAllNumObjSub("");
	    for(Post c : cc ){
	    	System.out.println(c.getPost()+"---"+c.getObjNum()+"---"+c.getSubNum()+"---"+c.getIndexSNum());
	    }
		/*List<Post>posts=indexService.getAllPost();
		for(int i=0;i<posts.size();i++){
			System.out.println(posts.get(i).getPost());
			System.out.println(posts.get(i).getObjNum());
			System.out.println(posts.get(i).getSubNum());
			System.out.println(posts.get(i).getIndexSNum());
		}*/
		//System.out.println(indexDAO.selectIndexSNumByPost("项目经理"));
		//System.out.println(indexDAO.selectBYPII("", "", "专业广度"));
			//System.out.println(indexService.deletePost("项目经理1"));
		//System.out.println(indexService.addPost("项目经理"));
		//System.out.println(indexService.deletePost("项目经理"));
		//System.out.println(indexDAO.selectSById(402));
		//System.out.println(indexDAO.selectObjNumById(388));
		
		
	}
	//@Test
	public void addindex() {
		index.setPost("项目经理");
		index.setIndexF("专业知识");
		index.setIndexS("专业广度&专业深度");
		//index.setNumObj(1);	
		//index.setNumSub(Integer.valueOf("1"));
		//index.setTotal(Integer.valueOf("1"));
		System.out.println(indexService.addIndex(index));
		
	}

	//@Test
	@Transactional
	@Rollback(false)  //单元测试时，事物会自动进行回滚，注解式事物自动回滚关闭
	public void createIndex() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		// 确定要操作的excelc
		HSSFWorkbook workbook = new HSSFWorkbook(
				new POIFSFileSystem(new FileInputStream("src/main/resources/xls/index.xls")));

		// 取第0个单元表
		HSSFSheet sheet = workbook.getSheetAt(0);
		// sheet.getPhysicalNumberOfRows();求出所有行数
		for (int i = 1; i < 16; i++) {

			// 取一行操作
			HSSFRow row = sheet.getRow(i);
			// row.getPhysicalNumberOfCells();求出本行的单元格数，也就是列数
			for (int j = 0; j < 6; j++) {
				String cellString = row.getCell(j).toString().trim();
				int length = cellString.length();
				if (length >= 2) { // 这里大于等于2是防止有些列只有一个字符，到下面会报错
					// 通过截取最后两个字符，如果等于.0 就去除最后两个字符
					if (cellString.substring(length - 2, length).equals(".0"))
						cellString = cellString.substring(0, length - 2);
				}
				//判断是第几列，然后对实体index进行相对应的赋值
				switch (j) {
				case 0:
					index.setPost(cellString);
					break;
				case 1:
					index.setIndexF(cellString);
					break;
				case 2:
					index.setIndexS(cellString);
					break;
				case 3:
					index.setNumObj(Integer.valueOf(cellString));
					break;
				case 4:
					index.setNumSub(Integer.valueOf(cellString));
					break;
				case 5:
					index.setTotal(Integer.valueOf(cellString));
					break;
				}	
			}
			System.out.println(indexDAO.insert(index));
			//还须判断事物是否回滚了
			//System.out.print(indexDAO.insert(index));
		}

	}
}
