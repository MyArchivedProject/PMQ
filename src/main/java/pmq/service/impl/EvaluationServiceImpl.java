package pmq.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import pmq.dao.EvaluationMapper;
import pmq.dao.TargetMapper;
import pmq.pojo.Evaluation;
import pmq.service.IEvaluationService;

@Service(value = "evaluationService")
public class EvaluationServiceImpl implements IEvaluationService {

	@Resource(name = "evaluationMapper")
	private EvaluationMapper evaDAO;
	@Resource(name = "evaluation")
	private Evaluation eva;
	@Resource(name = "targetMapper")
	private TargetMapper targetDAO;

	public boolean updateEva(Evaluation eva) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (evaDAO.updateByPrimaryKey(eva) > 0) {
			flag = true;
		}
		return flag;
	}

	/*@SuppressWarnings("deprecation")
	@Transactional
	public List<String> importEva(String url) {
		// TODO Auto-generated method stub
		// 用于储存错误信息或者cuccess
		List<String> errors = new ArrayList<String>();
		// 验证是否是*.xls的文件
		int len = url.length();
		if (!(len >= 4 && (url.substring(len - 4, len).equals(".xls")))) {
			errors.add("文件格式错,请使用以.xls为后缀的文件");
			return errors;
		}
		// 为下面判断分数是否是数字类型的作准备
		Pattern pat = Pattern.compile("[0-9][0-9]*");
		// 有一条数据更新失败则为false
		boolean flag = true;
		// 一共更新了多少条数据//暂时没用到
		int sum = 0;
		// 确定要操作的excelc
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(new POIFSFileSystem(new FileInputStream(url)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			errors.add("找不到指定文件");
			return errors;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			errors.add("系统错误1");
			return errors;
		}catch (org.apache.poi.poifs.filesystem.OfficeXmlFileException e) {
			errors.add("error0_文件格式错误,请使用模版文件");
			return errors;
		}
		// 取第0个单元表
		HSSFSheet sheet = workbook.getSheetAt(0);
		// System.out.println(sheet.getPhysicalNumberOfRows());
		// 循环标签，用于直接跳出此循环
		continueOut: for (int i = 1; i < 17; i++) {
			// 取一行操作
			HSSFRow row = sheet.getRow(i);
			// 保存职位指标
			int indexId = 0;
			// 保存职位，用来查询职位指标id
			String post = "";
			for (int j = 0; j < 14; j++) {
				Cell cell = row.getCell(j);
				// 判断单元格是否为空，为空则设置为""
				String cellString;
				if (cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
					cellString = "";
				} else {
					cellString = cell.toString().trim();
				}
				int length = cellString.length();
				if (length >= 2) { // 这里大于等于2是防止有些列只有一个字符，到下面会报错
					// 通过截取最后两个字符，如果等于.0 就去除最后两个字符
					if (cellString.substring(length - 2, length).equals(".0"))
						cellString = cellString.substring(0, length - 2);
				}
				// 判断是第几列，然后对实体index进行相对应的赋值
				switch (j) {
				case 0:
					post = cellString;
					eva.setPost(post);
					break;
				case 1:
					indexId = targetDAO.selectByPIS(post, cellString);
					if (indexId == 0) {
						errors.add(post + " 职位下没有 " + cellString + " 这个二级指标,停止更新");
						flag = false;
						break continueOut;
					}
					eva.setIndexId(indexId);
					break;
				case 2:
					eva.setEvaA(cellString);
					break;
				case 3:
					if (!pat.matcher(cellString).matches()) {
						cellString = "0";
					}
					eva.setMiniA(Integer.valueOf(cellString));
					break;
				case 4:
					eva.setEvaB(cellString);
					break;
				case 5:
					if (!pat.matcher(cellString).matches()) {
						cellString = "0";
					}
					eva.setMiniB(Integer.valueOf(cellString));
					break;
				case 6:
					eva.setEvaC(cellString);
					break;
				case 7:
					if (!pat.matcher(cellString).matches()) {
						cellString = "0";
					}
					eva.setMiniC(Integer.valueOf(cellString));
					break;
				case 8:
					eva.setEvaD(cellString);
					break;
				case 9:
					if (!pat.matcher(cellString).matches()) {
						cellString = "0";
					}
					eva.setMiniD(Integer.valueOf(cellString));
					break;
				case 10:
					eva.setEvaE(cellString);
					break;
				case 11:
					if (!pat.matcher(cellString).matches()) {
						cellString = "0";
					}
					eva.setMiniE(Integer.valueOf(cellString));
					break;

				case 12:
					eva.setEvaF(cellString);
					break;
				case 13:
					if (!pat.matcher(cellString).matches()) {
						cellString = "0";
					}
					eva.setMiniF(Integer.valueOf(cellString));
					break;
				}
			}
			if ( evaDAO.updateByIndexId(eva) > 0) {
				sum++;
			} else {
				errors.add("系统错误2");
				flag = false;
				break continueOut;
			}
		}
		
		if (flag == false) {
			errors.add("发生错误之前,共加载"+sum+"条数据");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		} else {
			errors.add("success");
		}
		// 关闭HSSFWorkbook
		try {
			workbook.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return errors;
	}*/
	public String addEva(Evaluation eva) {
		// TODO Auto-generated method stub
		String flag = "添加失败";
		if(evaDAO.selectEvaByPost(eva.getTitle())>0){
			flag = "已存在名称为:"+eva.getTitle()+" 的等级评价";
		}else{
			if (evaDAO.insert(eva) > 0) {
				flag = "success";
			}
		}	
		return flag;
	}

	public boolean deleteEva(int id) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if (evaDAO.deleteByPrimaryKey(id) > 0) {
			flag = true;
		}
		return flag;
	}

	public List<Evaluation> findAll(Evaluation eva) {
		// TODO Auto-generated method stub
		List<Evaluation> list=new ArrayList<Evaluation>();
		if ("".equals(eva.getPost().trim()) && "".equals(eva.getTitle().trim()) && 2==eva.getFlag()) {
			list=evaDAO.selectAll();
		}else if(2==eva.getFlag()){
			list=evaDAO.selectAllByEva(eva);
		}else{
			list=evaDAO.selectAllByEvaFlag(eva);
		}
		//遍历所有的等级评价
		for(int i=0;i<list.size();i++){
			/*//用来暂存等级评价名称和对应的所有二级指标名称
			StringBuffer buffer=new StringBuffer();
			//判断是否是自定义的等级评价
			if(null!=list.get(i).getIndexId()){
				//不是自定义的等级评价
				String temp=targetDAO.selectSById(list.get(i).getIndexId());
				buffer.append(temp);//放等级评价所包含的二级职位指标
			}else{
				String temp[]=list.get(i).getMultiIndex().split(";");
				//buffer.append(temp[0]);//放等级评价的名称
				//遍历，取出职位指标id，并获取所有的二级职位 指标
				for(int j=1;j<temp.length;j++){
					int indexId=Integer.parseInt(temp[j]);
					buffer.append(targetDAO.selectSById(indexId));
					if((j+1)<temp.length){
						buffer.append(";");
					}				
				}
			}
			System.out.println(buffer.toString());
			list.get(i).setMessage(buffer.toString());*/
			//后面要删除
			if(list.get(i).getMultiIndex()==null){
				list.get(i).setMultiIndex("83");
			}
		}
	
		return list;
	}
}
